//Jingxia Zhu (jiz119)

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

// prints a message, reads a line of input, and chops off the newline.
void read_line(const char* message, char* buffer, int length)
{	
	printf(message);
	fgets(buffer, length, stdin);
	if(strlen(buffer) != 0)
		buffer[strlen(buffer) - 1] = 0;
}

// sees if two strings are the same, ignoring case.
int streq_nocase(const char* a, const char* b)
{
	for(; *a && *b; a++, b++)
		if(tolower(*a) != tolower(*b))
			return 0;
	return *a == 0 && *b == 0;
}

//function to generate a random number between 0 and 2
int getRandomNumber(){
	srand((unsigned int)time(NULL));
	int min = 0;
	int max = 2;
	int random;
	random = rand()%(max - min + 1) + min;

	return random;
}

//function that takes user choice and computer choice
//return 2 when it's a tie
//return 1 when the user wins; return -1 when the computer wins
//return 3 when the user inputs choices other than rock, paper, or scissors
int battle_with_comp(const char* userChoice, const char* compChoice){	
	if ((streq_nocase(userChoice, compChoice))){
		printf("The computer chooses %s. It's a tie! ", compChoice);
        return 2;	
    }
	if ((streq_nocase(userChoice, "rock"))){
		if ((streq_nocase(compChoice, "paper"))){ 
			printf("The computer chooses %s. You lose this round! ", compChoice);
			return -1;
		} else if ((streq_nocase(compChoice, "scissors"))){
			printf("The computer chooses %s. You win this round! ", compChoice);
			return 1;
		}
	} else if ((streq_nocase(userChoice, "paper"))){
		if ((streq_nocase(compChoice, "scissors"))){
			printf("The computer chooses %s. You lose this round! ", compChoice);
			return -1;
		} else if ((streq_nocase(compChoice, "rock"))){
			printf("The computer chooses %s. You win this round! ", compChoice);
			return 1;
		}
	} else if ((streq_nocase(userChoice, "scissors"))){
		if ((streq_nocase(compChoice, "rock"))){
			printf("The computer chooses %s. You lose this round! ", compChoice);
			return -1;
		} else if ((streq_nocase(compChoice, "paper"))){
			printf("The computer chooses %s. You win this round! ", compChoice);
			return 1;
		}
	}else{
    	return 3;
    }

	return 0;
}

//function to check if the user wants to play again
//return 1 when user inputs "yes" or "y"
//return -1 when the user inputs "no" or "n"
int play_again(int userPoint, int compPoint){
	char replayOption[100];
	char buffer[100];
	if (userPoint == 3){
		read_line("You win the tournament! Play again? ", buffer, sizeof(buffer));
		sscanf(buffer, "%99s", replayOption);
	} else if (compPoint == 3){
		read_line("You lose the tournament! Play again? ", buffer, sizeof(buffer));
		sscanf(buffer, "%99s", replayOption);
	}	
	if ((streq_nocase(replayOption, "yes")) || (streq_nocase(replayOption, "y")))
		return 1;
	if ((streq_nocase(replayOption, "no")) || (streq_nocase(replayOption, "n")))
		return -1;

	return 0;
}
	

int main()
{
	char buffer[100];
	char userChoice[100];
	int round = 1;
	int userPoint = 0;
	int compPoint = 0;
	int battleResult;
	
	//three choices taht the computer can choose
	const char* choices[3] = {"rock", "paper", "scissors"};
	const char* compChoice;	
	
	//loop when neighther the user nor the computer gets 3 points
	while ((userPoint < 3) && (compPoint < 3)){
		printf("Round %d! ", round);
		read_line("What's your choice? ", buffer, sizeof(buffer));
		sscanf(buffer, "%99s", userChoice);

		//to randomly choose from rock, paper, and scissors for compChoice
		compChoice = choices[getRandomNumber()];
		//get the result of one round of rps game
		battleResult = battle_with_comp(userChoice, compChoice);
		
		//loop when the user types in choices other than rock, paper, or scissors
		while(battleResult == 3){
			printf("Huh? ");
			//ask the user to type in again for a valid input 
			read_line("What's your choice? ", buffer, sizeof(buffer));
			sscanf(buffer, "%99s", userChoice);
			//get the result of one round of rps game
			battleResult = battle_with_comp(userChoice, compChoice);

		}	
		
		//the user gets one point if he/she wins while the computer doesn't
		if (battleResult == 1){
			userPoint++;
			printf("You: %d computer: %d\n\n", userPoint, compPoint);	
		
		//the computer gets one point while the user doesn't
		} else if (battleResult == -1){
			compPoint++;
			printf("You: %d computer: %d\n\n", userPoint, compPoint);
		
		//neither the user nor the computer gets any point if this round is a tie
		} else if (battleResult == 2){
			printf("You: %d computer: %d\n\n", userPoint, compPoint);
			
		} 
		//increment the round
		round++;
		
		//check if the user would like to replay the game
		int checkReplay = play_again(userPoint, compPoint);
		if (checkReplay == 1){
			round = 1;
			userPoint = 0;
			compPoint = 0;
			printf("\n");
		} else if(checkReplay == -1){
			break;
		}


	}
	return 0;
}
