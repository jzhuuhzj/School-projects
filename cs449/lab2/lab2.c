//Jingxia Zhu(jiz119)

#include <stdio.h>
#include <string.h>
#include <ctype.h>

// prints a message, reads a line of input, and chops off the newline.
// call it like
//    read_line("Type something in: ", buffer, sizeof(buffer));
void read_line(const char* message, char* buffer, int length)
{
	
	printf(message);
	fgets(buffer, length, stdin);
	if(strlen(buffer) != 0)
		buffer[strlen(buffer) - 1] = 0;
}

// sees if two strings are the same, ignoring case.
// call it like
//    if(streq_nocase(one, two))
int streq_nocase(const char* a, const char* b)
{
	for(; *a && *b; a++, b++)
		if(tolower(*a) != tolower(*b))
			return 0;

	return *a == 0 && *b == 0;
}

float weight_in_the_planet(int weight, const char* planet){
	float yourWeight;

	if(streq_nocase(planet, "mercury")){
		yourWeight = 0.38 * weight;
	}else if(streq_nocase(planet, "venus")){
		yourWeight = 0.91 * weight;
	}else if(streq_nocase(planet, "mars")){
		yourWeight = 0.38 * weight;
	}else if(streq_nocase(planet, "jupiter")){
		yourWeight = 2.54 * weight;
	}else if(streq_nocase(planet, "saturn")){
		yourWeight = 1.08 * weight;
	}else if(streq_nocase(planet, "uranus")){
		yourWeight = 0.91 * weight;
	}else if(streq_nocase(planet, "neptune")){
		yourWeight = 1.19 * weight;
	}else{
		yourWeight = -1;
	}

	return yourWeight;

}

int main()
{
	char buffer_weight[100];
	char buffer_planet[100];

	int weight;
	char planet[20];

	read_line("Uh, how much do you weigh? ", buffer_weight, sizeof(buffer_weight));
	sscanf(buffer_weight, "%d", &weight);
	//printf("%d\n", weight);

	while(1){
		read_line("What planet do you wanna go to ('exit' to exit)? ", buffer_planet, sizeof(buffer_planet));
		sscanf(buffer_planet, "%19s", planet);
		//printf("%s\n", planet);
		if(streq_nocase(planet, "exit")){
			break;
		}else if(streq_nocase(planet, "earth")){
			printf("uh, you're already there, buddy\n");
		}else{
			if(weight_in_the_planet(weight, planet) < 0){
				printf("That's not a planet.\n");
			}
			else{	
				printf("You'd weigh %.2f there.\n", weight_in_the_planet(weight, planet));
			}
			
		}
	}

	return 0;

}