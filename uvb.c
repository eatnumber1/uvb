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
#include <setjmp.h>
#include <signal.h>
#include <errno.h>
#include <poll.h>
#include <unistd.h>
#include <fcntl.h>
#include <netdb.h>

#define READBUF_SIZE 4096
#define NUM_FDS 1

static jmp_buf jump_buf;
static int sock;

static void restarter() {
	signal(SIGPIPE, restarter);
	longjmp(jump_buf, 1);
	close(sock);
}

static struct addrinfo *dnslookup() {
	struct addrinfo hints;
	memset(&hints, 0, sizeof(struct addrinfo));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;

	struct addrinfo *result;
	int code;
	if( (code = getaddrinfo("uvb.csh.rit.edu", "http", &hints, &result)) != 0 ) {
		fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(code));
		exit(EXIT_FAILURE);
	}
	return result;
}

#ifdef NDEBUG
static void spinner() {
	static int position = 0;
	putc('\r', stdout);
	switch( position ) {
		case 0:
			putc('|', stdout);
			break;
		case 1:
			putc('/', stdout);
			break;
		case 2:
			putc('-', stdout);
			break;
		case 3:
			putc('\\', stdout);
			break;
		default:
			fprintf(stderr, "Illegal position value %d\n", position);
			exit(EXIT_FAILURE);
	}
	fflush(stdout);
	position = (position + 1) % 4;
}
#endif

int main() {
	register const char *str = "HEAD /fight.php?name=eatnumber1 HTTP/1.1\r\nHost: uvb.csh.rit.edu\r\nAccept: */*\r\n\r\n";
	register const size_t len = strlen(str);
	struct pollfd poll_fd;

	struct addrinfo *addr = dnslookup();

	if( setjmp(jump_buf) == 0 ) restarter();
	if( (sock = socket(addr->ai_family, addr->ai_socktype | SOCK_NONBLOCK, addr->ai_protocol)) == -1 ) {
		perror("socket");
		exit(EXIT_FAILURE);
	}

	memset(&poll_fd, 0, sizeof(struct pollfd));
	poll_fd.fd = sock;
	poll_fd.events = POLLIN | POLLOUT;

	if( connect(sock, addr->ai_addr, addr->ai_addrlen) == -1 ) {
		if( errno == ETIMEDOUT || errno == ECONNREFUSED ) {
			perror("connect");
			restarter();
		} else if( errno != EINPROGRESS ) {
			perror("connect");
			exit(EXIT_FAILURE);
		}
	}

	while( true ) {
		int ready = poll(&poll_fd, 1, -1);
		if( ready == -1 ) {
			perror("poll");
			restarter();
		} else
#ifdef _GNU_SOURCE
		if( poll_fd.revents & POLLRDHUP ) {
			fprintf(stderr, "poll: Remote closed connection.\n");
			restarter();
		} else
#endif
		if( poll_fd.revents & POLLHUP ) {
			fprintf(stderr, "poll: Remote closed connection.\n");
			restarter();
		} else if( poll_fd.revents & POLLERR ) {
			fprintf(stderr, "poll: Error\n");
			restarter();
		} else if( poll_fd.revents & POLLNVAL ) {
			fprintf(stderr, "poll: Invalid request\n");
			restarter();
		}
		poll_fd.events = POLLIN;
		
		if( write(sock, str, len) == -1 ) {
			perror("write");
			restarter();
		}

		if( poll_fd.revents & POLLIN ) {
			char buf[READBUF_SIZE];
			while( true ) {
				ssize_t count = read(sock, buf, READBUF_SIZE);
				if( count == 0 ) {
					restarter();
				} else if( count == -1 ) {
					if( errno == EAGAIN || errno == EWOULDBLOCK ) {
						break;
					} else {
						perror("write");
						restarter();
					}
				}
#ifndef NDEBUG
				buf[READBUF_SIZE - 1] = '\0';
				printf("%s", buf);
				fflush(stdout);
#endif
			}
#ifdef NDEBUG
			spinner();
#endif
		}
	}
	freeaddrinfo(addr);
}
