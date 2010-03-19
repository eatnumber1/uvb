#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <string.h>
#include <setjmp.h>
#include <signal.h>

static jmp_buf jump_buf;

static void sigpipe_handler() {
	signal(SIGPIPE, sigpipe_handler);
	printf("SIGPIPE\n");
	longjmp(jump_buf, 1);
}

int main() {
	register int sock;
	register const char *str = "GET /fight.php?name=eatnumber1 HTTP/1.1\nHost: uvb.csh.rit.edu\nAccept: */*\n\n";
	register const size_t len = strlen(str);
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = (in_port_t) htons(80);
	if( inet_aton("129.21.50.165", &addr.sin_addr) == 0 ) {
		fprintf(stderr, "Illegal address\n");
		exit(EXIT_FAILURE);
	}

	if( setjmp(jump_buf) == 0 ) {
		sigpipe_handler();
	} else {
		close(sock);
	}
	if( (sock = socket(AF_INET, SOCK_STREAM, 0)) == -1 ) {
		perror("socket");
		exit(EXIT_FAILURE);
	}
	if( connect(sock, (struct sockaddr *) &addr, sizeof(struct sockaddr_in)) == -1 ) {
		perror("connect");
		sigpipe_handler();
	}
	while(1) {
		write(sock, str, len);
	}
}
