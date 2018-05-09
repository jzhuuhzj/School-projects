//Jingxia Zhu(jiz119)

#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>
#include <string.h>
#include <ctype.h>

unsigned long (*compressBound)(unsigned long length);
int (*compress)(void *dest, unsigned long* destLen, const void* source, unsigned long sourceLen);
int (*uncompress)(void *dest, unsigned long* destLen, const void* source, unsigned long sourceLen);

void error_exit(const char *error_message)
{
   printf("Error: %s\n", error_message);
   exit(1);
}

int main(int argc, const char* argv[]){
	FILE *fp;
	unsigned long uncompressed_size;
  	unsigned long compressed_size;
  	char* uncompressed_buffer;
  	char* compressed_buffer;
  	int compress_result;
  	int uncompress_result;

  	if(argc < 3){
		error_exit("too few program arguments");
	}

	void* lib = dlopen("libz.so", RTLD_NOW);
	if(lib == NULL)
	{
    	error_exit("couldn’t open libz.so");
	}

	compressBound = dlsym(lib, "compressBound");
	if(compressBound == NULL)
	{
    	error_exit("compress bound is null!");
	}

	compress = dlsym(lib, "compress");
	if(compress == NULL)
	{
    	error_exit("couldn't load the compress symbol from zlib");
	}

	uncompress = dlsym(lib, "uncompress");
	if(uncompress == NULL)
	{
    	error_exit("couldn't load the uncompress symbol from zlib");
	}

	fp = fopen(argv[2], "rb");
	if(fp == NULL){
		error_exit("couldn’t open input file");
	}

	if(strcmp(argv[1],"-c") == 0){
		fseek(fp, 0, SEEK_END); //to the end of the file
		uncompressed_size = ftell(fp); //find the size of the file
		fseek(fp, 0, SEEK_SET); //back to the begining
		uncompressed_buffer = malloc(uncompressed_size + 1);
		fread(uncompressed_buffer, uncompressed_size, 1, fp);
		compressed_size = compressBound(uncompressed_size);
		compressed_buffer = malloc(compressed_size);
		compress_result = compress(compressed_buffer, &compressed_size, uncompressed_buffer, uncompressed_size);
		if(compress_result < 0){
			printf("Error: compress failed");
			return -1;
		}
		fwrite(&uncompressed_size, sizeof(unsigned long), 1, stdout);
  		fwrite(&compressed_size, sizeof(unsigned long), 1, stdout);
  		fwrite(compressed_buffer, compressed_size, 1, stdout);
	} else if(strcmp(argv[1],"-d") == 0){
		fread(&uncompressed_size, sizeof(unsigned long), 1, fp);
		fread(&compressed_size, sizeof(unsigned long), 1, fp);
		compressed_buffer = malloc(compressed_size);
		fread(compressed_buffer, compressed_size, 1, fp);
		uncompressed_buffer = malloc(uncompressed_size);
		uncompress_result = uncompress(uncompressed_buffer, &uncompressed_size, compressed_buffer, compressed_size);
		if(uncompress_result < 0){
			printf("Error: uncompress failed");
			return -1;
		}
  		fwrite(uncompressed_buffer, uncompressed_size, 1, stdout);
	} else {
		error_exit("invalid argv[1]");
	}
	free(uncompressed_buffer);
	free(compressed_buffer);
	fclose(fp);
	return 0;
}


