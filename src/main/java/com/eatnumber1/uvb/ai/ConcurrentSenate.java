package com.eatnumber1.uvb.ai;

import com.eatnumber1.uvb.board.GameMap;
import com.eatnumber1.uvb.commands.AbstractCommandVisitor;
import com.eatnumber1.uvb.commands.Command;
import com.eatnumber1.uvb.commands.MoveCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * @author Russell Harmon
 * @since Nov 2, 2010
 */
public class ConcurrentSenate implements Senate {
	private static Log log = LogFactory.getLog(ConcurrentSenate.class);

	private Set<Senator> senators;
	private ExecutorService threadPool = Executors.newCachedThreadPool(new ThreadFactory() {
		@Override
		public Thread newThread( Runnable runnable ) {
			return new Thread(runnable, "Senator Thread");
		}
	});

	public ConcurrentSenate( Set<Senator> senators ) {
		this.senators = senators;
	}

	public ConcurrentSenate() {
		senators = new HashSet<Senator>();
	}

	private void await( Set<Future<?>> futures ) {
		for( Future<?> f : futures ) {
			try {
				f.get();
			} catch( InterruptedException e ) {
				throw new RuntimeException(e);
			} catch( ExecutionException e ) {
				throw new RuntimeException(e);
			}
		}
	}

	private Set<Proposal> doProposals( final GameMap map ) {
		final Set<Proposal> proposals = new HashSet<Proposal>();
		Set<Future<?>> futures = new HashSet<Future<?>>(senators.size());
		for( final Senator senator : senators ) {
			futures.add(threadPool.submit(new Runnable() {
				@Override
				public void run() {
					proposals.addAll(senator.propose(map));
				}
			}));
		}
		await(futures);
		return proposals;
	}

	@NotNull
	@Override
	public Command decide( final GameMap map ) {
		log.debug("Senate is in session.");
		final Set<Proposal> proposals = doProposals(map);
		Set<Future<?>> futures = new HashSet<Future<?>>(senators.size());
		List<Ballot> ballots = new ArrayList<Ballot>(proposals.size());
		for( Proposal proposal : proposals ) {
			final Ballot ballot = new SimpleBallot(proposal);
			for( final Senator senator : senators ) {
				futures.add(threadPool.submit(new Runnable() {
					@Override
					public void run() {
						senator.vote(map, ballot);
					}
				}));
			}
			ballots.add(ballot);
		}
		await(futures);
		futures.clear();
		Collections.sort(ballots, new Comparator<Ballot>() {
			@Override
			public int compare( Ballot ballot, final Ballot ballot1 ) {
				Integer cmp = ballot.compareTo(ballot1);
				if( cmp != 0 ) return cmp;
				cmp = ballot.getProposal().getCommand().visit(new AbstractCommandVisitor<Integer>() {
					@Override
					@Nullable
					public Integer visitMoveCommand( final MoveCommand command ) {
						return ballot1.getProposal().getCommand().visit(new AbstractCommandVisitor<Integer>() {
							@Override
							@Nullable
							public Integer visitMoveCommand( MoveCommand command1 ) {
								return command.getDirection().compareTo(command1.getDirection());
							}
						});
					}
				});
				if( cmp == null ) return 0;
				return cmp;
			}
		});
		log.trace("Voting complete. Results are: " + ballots);
		final Ballot acceptedBallot = ballots.get(ballots.size() - 1);
		log.debug("Ballot " + acceptedBallot + " wins.");
		for( final Senator senator : senators ) {
			futures.add(threadPool.submit(new Runnable() {
				@Override
				public void run() {
					senator.results(acceptedBallot.getProposal());
				}
			}));
		}
		await(futures);
		return acceptedBallot.getProposal().getCommand();
	}

	@Override
	public void addSenator( Senator senator ) {
		senators.add(senator);
	}
}
