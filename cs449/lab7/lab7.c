//Jingxia Zhu(jiz119)
#define _GNU_SOURCE
#include <errno.h>
#include <stdio.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char** argv)
{
	if(argc < 2)
	{
		printf("uh, you gotta gimme an executable to run...\n");
		return 0;
	}

	if(fork() == 0)
	{
		execvp(argv[1], &argv[1]);

		perror("Error running program: ");
		exit(1);
	}
	else
	{
		signal(SIGINT, SIG_IGN);
		int status;
		int childpid = waitpid(-1, &status, 0); //0: the parent will wait util child is terminated
		printf("----------\n");

		if(childpid == -1){
			perror("Error waitpid: ");
		} else if(WIFEXITED(status) && WEXITSTATUS(status) == 0){ 
			printf("Program exited successfully!\n");
		} else if(WIFEXITED(status) && WEXITSTATUS(status) != 0){ 
			printf("Program exited with code %d\n", WEXITSTATUS(status));
		} else if(WIFSIGNALED(status)){
			printf("Program was terminated by signal: %s\n", strsignal(status));
		} else{
			printf("Program was terminated some other way!\n");
		}
	}

	return 0;
}
