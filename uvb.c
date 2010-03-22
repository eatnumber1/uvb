#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <string.h>
#include <setjmp.h>
#include <signal.h>
#include <errno.h>

static jmp_buf jump_buf;

static void sigpipe_handler() {
	signal(SIGPIPE, sigpipe_handler);
	longjmp(jump_buf, 1);
}

typedef enum {
	false, true
} bool;

int main() {
	register int sock;
	bool closed = true;
	register const char *str = "HEAD /fight.php?name=eatnumber1 HTTP/1.1\r\nHost: uvb.csh.rit.edu\r\nAccept: */*\r\n\r\n";
	register const size_t len = strlen(str);
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = (in_port_t) htons(80);
	if( inet_aton("129.21.50.165", &addr.sin_addr) == 0 ) {
		fprintf(stderr, "Illegal address\n");
		exit(EXIT_FAILURE);
	}

	if( setjmp(jump_buf) == 0 ) sigpipe_handler();
	if( !closed ) close(sock);
	if( (sock = socket(AF_INET, SOCK_STREAM, 0)) == -1 ) {
		perror("socket");
		exit(EXIT_FAILURE);
	}
	closed = false;
	if( connect(sock, (struct sockaddr *) &addr, sizeof(struct sockaddr_in)) == -1 ) {
		if( errno == ETIMEDOUT || errno == ECONNREFUSED ) {
			sigpipe_handler();
		} else {
			perror("connect");
			exit(EXIT_FAILURE);
		}
	}
	char buf[1024];
	while(1) {
		if( write(sock, str, len) == -1 ) {
			sigpipe_handler();
		}
		register int count = read(sock, buf, 1024);
		if( count == -1 || count == 0 ) {
			sigpipe_handler();
		}
	}
}
