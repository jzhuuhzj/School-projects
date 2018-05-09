//Jingxia Zhu(jiz119)

#include <stdio.h>
#include <stdlib.h>

int check_char(char character){
	if ((character >= 32)&&(character <= 126))
		return 1;
	else
		return 0;
}

int main(int argc, const char* argv[]){
	if (argc < 1){
		printf("Please input some file name\n");
		return 0;
	}
	
	FILE* file = fopen(argv[1], "rb");
	
	if(file == NULL){
		printf("File points to null. Error!\n");
		exit(1);
	}

	long string_start_pos = 0;
	long string_end_pos = 0;
	fseek(file, 0, SEEK_END);
	long size = ftell(file);
	fseek(file, 0, SEEK_SET);
	char* buffer = malloc(size + 1);
	
	fread(buffer, size, 1, file);
	buffer[size]='\0';	
	while (string_end_pos + 1 <= size){
		while(check_char(buffer[string_end_pos]) == 1){
			string_end_pos++;
		}
		if ((string_end_pos - string_start_pos) >= 4){
			buffer[string_end_pos] = '\0';
			printf("%s\n", &buffer[string_start_pos]);
		}
		string_end_pos++;
		string_start_pos = string_end_pos;
	}
	free(buffer);
	fclose(file);
	
	return 0;
}


