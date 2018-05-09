//Jingxia Zhu(jiz119)

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef int (*PREDICATE)(const void*);

// ------------------------------------------------------

int filter(void* output, const void* input, int length, int item_size, PREDICATE pred)
{
	// FILL ME IN!
	int count = 0;
	for (int i = 0; i < length; i++){	
		if(pred(input)){	
			memcpy(output, input, item_size);	
			output = (char*)output + item_size;	
			count++;
		}
		input = (char*)input + item_size;		
	}
	return count;

}

int less_than_50(const void* p)
{
	// FILL ME IN!
	float pointer = *(const float*)p;
	return (pointer<50.0);
}

// ------------------------------------------------------
// you shouldn't have to change the stuff below here.
// you can for testing, but please put it back the way it was before you submit.

#define NUM_VALUES 10

float float_values[NUM_VALUES] =
{
	31.94, 61.50, 36.10,  1.00,  6.35,
	20.76, 69.30, 19.60, 79.74, 51.29,
};

int main()
{
	float filtered[NUM_VALUES];
	int filtered_len = filter(filtered, float_values, NUM_VALUES, sizeof(float), &less_than_50);

	printf("there are %d numbers less than 50:\n", filtered_len);

	for(int i = 0; i < filtered_len; i++)
		printf("\t%.2f\n", filtered[i]);

	return 0;
}