//Jingxia Zhu (jiz119)

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

typedef struct Node
{
    struct Node* next;
    int value;
} Node;

//function to create a node -- part 1
Node* create_node(int value){

	Node* node = malloc(sizeof(Node));
	
	node -> value = value;
	node -> next = NULL;

	return node; 
}

// function to print out all values of the linked list - part2
void print_list(Node* n){
	
	while (n != NULL){
		printf("%d\n", n -> value);
		n = n -> next;
	}
}

//function to free the every malloc - part3
void free_list(Node* n){
	Node* temp = NULL;
	while (n){
		temp = n -> next;
		//printf("%d\n", n -> value);
		free(n);
		n = temp;	
	}

}

void read_line(const char* message, char* buffer, int length)
{
	printf(message);
	fgets(buffer, length, stdin);
	if(strlen(buffer) != 0)
		buffer[strlen(buffer) - 1] = 0;
}


int main()
{
	
	//test for part1
	/**
	Node* new_node = create_node(500);
	printf("value = %d, next = %p\n", new_node->value, new_node->next);
	**/
	
	//test for part2
	/**
	Node* a = create_node(10);
	Node* b = create_node(20);
	Node* c = create_node(30);
	a->next = b;
	b->next = c;
	print_list(a);
	**/

	//test for part3
	//free_list(a);

	
	//the driver program - part4
	char buffer[100];
	int typed_int;

	Node* head = NULL;
	
	while(1){
		fgets(buffer, sizeof(buffer), stdin);
		sscanf(buffer, "%d", &typed_int);
		
		if (typed_int == -1){		
			break;
			
		} else{
			Node* node = create_node(typed_int);
			node -> next = head;
			head = node;
		}
				
	}

	print_list(head);
	free_list(head);

	return 0;
}

