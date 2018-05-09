//Jingxia Zhu(jiz119)
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mymalloc.h"

// Don't change or remove these constants.
#define MINIMUM_ALLOCATION  16
#define SIZE_MULTIPLE       8

typedef struct Node
{
	int size;
	int flag; //1 for used and 0 for free
	struct Node* prev;
	struct Node* next;		
}Node;

Node* head;
Node* tail;

unsigned int round_up_size(unsigned int data_size)
{
	if(data_size == 0)
		return 0;
	else if(data_size < MINIMUM_ALLOCATION)
		return MINIMUM_ALLOCATION;
	else
		return (data_size + (SIZE_MULTIPLE - 1)) & ~(SIZE_MULTIPLE - 1);
}

Node* find_best_fit(int size) {
	Node* curr;
    curr = head;
	Node* best= NULL;
	while (curr != NULL){
		if((curr->flag == 0) && (curr->size >= size) && (best == NULL || curr->size < best->size)){
			best = curr;
			if(best->size == size){
				break;
			}
		}
		curr = curr->next;
	}
	return best;
}

int find_free_neighbors(Node* node) {
	Node* p_node = node->prev;
	Node* n_node = node->next;	
	//node_to_free is a node in middle of 2 unflag nodes.
	if (p_node->flag == 0 && n_node->flag == 0) {  
		return 3;
	} 
	//only the right node is free.
	else if (p_node->flag == 1 && n_node->flag == 0){ 
		return 2; 
	} 
	//only the left node is free
	else if(p_node->flag == 0 && n_node->flag == 1) {
		return 1;
	} 
	//both nodes adjacent to node_to_free is in use
	else if(p_node->flag == 1 && n_node->flag == 1) {
		return 0;
	}
	return -1;
}

void coalese_neighboring_blocks(Node* node_1, Node* node_2) {
	node_1->size = node_1->size + node_2->size + sizeof(Node);
	node_1->next = node_2->next;
	node_2->next->prev = node_1;
}

void* my_malloc(unsigned int size)
{
	if(size == 0)
		return NULL;

	size = round_up_size(size);
	// ------- Don't remove anything above this line. -------
	// Here's where you'll put your code!
	// USE FUNCTIONS
	Node* current_node;
	Node* new_node;
	Node* temp_node;
	int rem_size;
	
	if (head == NULL) {
		head = (Node*)sbrk(size + sizeof(Node));
		head->flag = 1;
		head->prev = NULL;
		head->next = NULL;
		head->size = size;
		tail = head;
		return (void*)((char*)head + sizeof(Node));
	} else {
		current_node = find_best_fit(size);
		if(current_node == NULL) {
			temp_node = tail;
			tail = (Node*)(sbrk(sizeof(Node) + size));
			temp_node->next = tail;
			tail->prev = temp_node;
			tail->size = size;
			tail->next = NULL;
			tail->flag = 1;
			return (void*)((char*)tail + sizeof(Node));	
		} else {
			//check for size, if good for splitting	
			if(current_node->size >= (size + sizeof(Node) + MINIMUM_ALLOCATION)){  
				rem_size = current_node->size;
				current_node->size = size;
				new_node = (Node*) (((char*)current_node) + sizeof(Node) + size);
				new_node->prev = current_node;
				new_node->next = current_node->next;
				new_node->flag = 0;
				new_node->size = (rem_size - sizeof(Node) - size);
				if (current_node->next != NULL)
					new_node->next->prev = new_node;
				current_node->next = new_node;
			} 
			current_node->flag = 1;
			return (void*)((char*)current_node + sizeof(Node));
		}
	}
	return NULL;
}

void my_free(void* ptr)
{
	if(ptr == NULL)
		return;
	// and here's where you free stuff.
	
	Node* node_to_be_free = (Node*)(ptr - sizeof(Node));
	node_to_be_free->flag = 0;
	int shrink_size = node_to_be_free->size + sizeof(Node);
	if(node_to_be_free == head && head == tail) {
		head = NULL;
		tail = NULL;
		sbrk(0 - shrink_size);
	} else if (node_to_be_free == head) {
		if(head->next->flag == 0){
			coalese_neighboring_blocks(head, head->next);
		} 
	} else if(node_to_be_free == tail) {
		if(tail->prev->flag == 0) {
			shrink_size += (tail->prev->size + sizeof(Node));
			if(tail->prev == head) {
				head = NULL;
				tail = NULL;
			} else {
				tail = tail->prev->prev;
			}
			sbrk(0 - shrink_size);
		} else {
			tail = tail->prev;
			sbrk(0 - shrink_size);
		}
	} else {
		int to_coalese = find_free_neighbors(node_to_be_free);

		switch(to_coalese){
			case 3:
				node_to_be_free->prev->size += shrink_size;
				coalese_neighboring_blocks(node_to_be_free->prev, node_to_be_free->next);
				break;
			case 2:
				coalese_neighboring_blocks(node_to_be_free, node_to_be_free->next);
				break;
			case 1:
				coalese_neighboring_blocks(node_to_be_free->prev, node_to_be_free);
				break;
			case 0:
				node_to_be_free->flag = 0;
				break;
			case -1:
				break;
		}
	}
}







