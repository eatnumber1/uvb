#define _POSIX_C_SOURCE 1

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <string.h>
#include <signal.h>
#include <errno.h>
#include <poll.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>
#include <sys/resource.h>
#include <sys/time.h>

#ifdef NDEBUG
//#include <spinner.h>
#endif

#define READBUF_SIZE 4096

typedef enum {
	UVB_FD_CLOSED,
	UVB_FD_DISCONNECTED,
	UVB_FD_CONNECTED,
	UVB_FD_CONNECTING
} uvbfd_status;

typedef struct {
	struct pollfd *poll_fd;
	uvbfd_status status;
} uvbfd;

static rlim_t max_sockets;

static void do_rlimits() {
	struct rlimit lim;
	if( getrlimit(RLIMIT_NOFILE, &lim) == -1 ) {
		perror("getrlimit");
		exit(EXIT_FAILURE);
	}
	if( lim.rlim_cur != lim.rlim_max ) {
		lim.rlim_cur = lim.rlim_max;
		if( setrlimit(RLIMIT_NOFILE, &lim) == -1 ) {
			perror("getrlimit");
			exit(EXIT_FAILURE);
		}
	}
	max_sockets = lim.rlim_cur - 4;
}

static struct addrinfo *dnslookup() {
	struct addrinfo hints;
	memset(&hints, 0, sizeof(struct addrinfo));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;

	struct addrinfo *result;
	int code;
	if( (code = getaddrinfo("www.instantfart.com", "8080", &hints, &result)) != 0 ) {
	//if( (code = getaddrinfo("192.31.186.33", "8080", &hints, &result)) != 0 ) {
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(code));
		exit(EXIT_FAILURE);
	}
	return result;
}

static void do_close( uvbfd *uvb_fd ) {
	close(uvb_fd->poll_fd->fd);
	uvb_fd->status = UVB_FD_CLOSED;
}

static void do_connect( uvbfd *uvb_fd, struct addrinfo *addr ) {
	uvb_fd->status = UVB_FD_CONNECTING;
do_connect_in:
	if( connect(uvb_fd->poll_fd->fd, addr->ai_addr, addr->ai_addrlen) == -1 ) {
		if( errno != EINPROGRESS ) {
			perror("connect");
			if( errno == ETIMEDOUT || errno == ECONNREFUSED ) goto do_connect_in;
			if( errno == EINTR ) goto do_connect_in;
			exit(EXIT_FAILURE);
		}
	}
	uvb_fd->status = UVB_FD_CONNECTED;
}

static int do_poll( uvbfd *uvb_fds, struct pollfd *poll_fds ) {
	int ready;
do_poll_in:
	ready = poll(poll_fds, max_sockets, -1);
	if( ready == -1 ) {
		perror("poll");
		if( errno == EINTR ) goto do_poll_in;
		exit(EXIT_FAILURE);
	} else {
		for( int i = 0; i < max_sockets; i++ ) {
#ifdef _GNU_SOURCE
			if( poll_fds[i].revents & POLLRDHUP ) {
				do_close(&uvb_fds[i]);
				return -1;
			} else
#endif
			if( poll_fds[i].revents & POLLHUP ) {
				do_close(&uvb_fds[i]);
				return -1;
			} else if( poll_fds[i].revents & POLLERR ) {
				fprintf(stderr, "poll: Error\n");
				exit(EXIT_FAILURE);
			} else if( poll_fds[i].revents & POLLNVAL ) {
				fprintf(stderr, "poll: Invalid request\n");
				exit(EXIT_FAILURE);
			}
		}
	}
	return ready;
}

int main() {
	do_rlimits();

	register const char *str = "HEAD /+ HTTP/1.1\r\nHost: uvb.csh.rit.edu\r\nAccept: */*\r\n\r\n";
	register const size_t len = strlen(str);
	uvbfd uvb_fds[max_sockets];
	struct pollfd poll_fds[max_sockets];

	memset(uvb_fds, 0, sizeof(uvbfd) * max_sockets);
	memset(poll_fds, 0, sizeof(struct pollfd) * max_sockets);

	struct addrinfo *addr = dnslookup();

	for( int i = 0; i < max_sockets; i++ ) {
		uvb_fds[i].poll_fd = &poll_fds[i];
		uvb_fds[i].poll_fd->events = POLLIN | POLLOUT;
		uvb_fds[i].status = UVB_FD_CLOSED;
	}

	while( true ) {
		for( int i = 0; i < max_sockets; i++ ) {
			register struct pollfd *poll_fd = uvb_fds[i].poll_fd;
			register uvbfd *uvb_fd = &uvb_fds[i];

			if( uvb_fd->status == UVB_FD_CONNECTING ) continue;
			if( uvb_fd->status == UVB_FD_CLOSED || uvb_fd->status == UVB_FD_DISCONNECTED ) goto end;

			if( poll_fd->revents & POLLOUT ) {
				if( write(poll_fd->fd, str, len) == -1 ) {
					perror("write");
					do_close(uvb_fd);
					goto end;
				}

				poll_fd->events = POLLIN;
			}

			if( poll_fd->revents & POLLIN ) {
				char buf[READBUF_SIZE];
				while( true ) {
					ssize_t count = read(poll_fd->fd, buf, READBUF_SIZE);
					if( count == 0 ) {
						do_close(uvb_fd);
						goto end;
					} else if( count == -1 ) {
						if( errno == EAGAIN || errno == EWOULDBLOCK ) {
							break;
						} else {
							perror("write");
							do_close(uvb_fd);
							goto end;
						}
					}
#ifndef NDEBUG
					buf[READBUF_SIZE - 1] = '\0';
					printf("%s", buf);
					fflush(stdout);
#endif
				}
#ifdef NDEBUG
				//spinner();
				putc('.', stdout);
				fflush(stdout);
#endif
				poll_fd->events |= POLLOUT;
			}
end:
			if( uvb_fd->status == UVB_FD_CLOSED ) {
				if( (poll_fd->fd = socket(addr->ai_family, addr->ai_socktype | SOCK_NONBLOCK, addr->ai_protocol)) == -1 ) {
					perror("socket");
					exit(EXIT_FAILURE);
				}
				uvb_fd->status = UVB_FD_DISCONNECTED;
			}
			if( uvb_fd->status == UVB_FD_DISCONNECTED ) {
				do_connect(uvb_fd, addr);
				poll_fd->events |= POLLOUT;
			}
		}
		do_poll(uvb_fds, poll_fds);
	}
	freeaddrinfo(addr);
}
