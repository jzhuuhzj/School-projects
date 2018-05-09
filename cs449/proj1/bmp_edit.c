//Jingxia Zhu (jiz119)

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <stdint.h>

#pragma pack(1)

//struct for BMPHeader
typedef struct BMPHeader
{
    char format_identifier[2];
    int file_size;
    short reserved_value_1;
    short reserved_value_2;
    int offset;
} BMPHeader;

//struct for DIBHeader
typedef struct DIBHeader
{
    int header_size;
    int image_width;
    int image_height;
    short num_color_planes;
    short num_bits_per_pixel;
    int compression_scheme;
    int image_size;
    int horizontal_resolution;
    int vertical_resolution;
    int num_colors_in_palette;
    int num_important_colors;
} DIBHeader;

//struct for Pixel
typedef struct Pixel
{
    unsigned char blue_intensity;
    unsigned char green_intensity;
    unsigned char red_intensity;
} Pixel;

//check if the file is a proper 24-bit RGB BMP file
void check_if_proper_file(BMPHeader bmp_header, DIBHeader dib_header){
	//check if format identifier is "BM"
	if ((bmp_header.format_identifier[0] != 'B') || (bmp_header.format_identifier[1] != 'M')){
		printf("The format identifier is not the characters \'BM\'.");
		exit(1);
	}
	//check if the size of the DIB header is 40
	if(dib_header.header_size != 40){
		printf("The size of the DIB header is not 40. Error!\n");
		exit(1);
	}
	//check if bits per pixel is 24
	if(dib_header.num_bits_per_pixel != 24){
		printf("The bits per piexl is not 24. Error!\n");
		exit(1);
	}
}

int main(int agrc, const char* argv[]){
	//read a BMP file
	FILE* filePointer = fopen(argv[2], "rb+");
	//check if the file is null
	if(filePointer == NULL){
		fprintf(stderr, "\n File points to null. Error!\n");
		exit(1);
	}
	//make a BMPHeader struct variable
	BMPHeader bmp_header;
	//read the BMP file into the struct
	fread(&bmp_header, sizeof(bmp_header), 1, filePointer);
	
	//make a DIBHeader struct variable
	DIBHeader dib_header;
	//read the BMP file into the struct
	fread(&dib_header, sizeof(dib_header), 1, filePointer);

	//call function to check if the BMP file is in proper format
	check_if_proper_file(bmp_header, dib_header);

	//move the file pointer to the "offset from beginning of file"
	fseek(filePointer, bmp_header.offset, SEEK_SET);

	//calculate the row padding
	int extra_empty_padding = 4 - 3*dib_header.image_width%4;
	if (extra_empty_padding == 4){
		extra_empty_padding = 0;
	}

	//make a pixel struct variable
	Pixel pixel;
	//loop thtough every row of the image
	for(int i = 0; i < dib_header.image_height; i++){
		//loop through every pixel in each row
		for(int j = 0; j < dib_header.image_width; j++){
			//read a pixel into a Pixel struct variable
			fread(&pixel, sizeof(pixel), 1, filePointer);
			//do "invert" operation
			if (strcmp(argv[1], "--invert") == 0){
				pixel.blue_intensity = ~pixel.blue_intensity;
				pixel.green_intensity = ~pixel.green_intensity;
				pixel.red_intensity = ~pixel.red_intensity;
			}
			//do "grayscale" operation
			else if(strcmp(argv[1], "--grayscale") == 0){
				//normalize color values
				float normalized_blue = (pixel.blue_intensity)/255.0;
				float normalized_green = (pixel.green_intensity)/255.0;
				float normalized_red = (pixel.red_intensity)/255.0;		
				
				//calculating the "brightness" 
				float gray_value = (0.2126 * normalized_red) + (0.7152 * normalized_green) 
					+ (0.0722 * normalized_blue);
				
				//compensating for gamma
				if (gray_value <= 0.0031308){
					gray_value = 12.92*gray_value*255.0;		
				}else if(gray_value > 0.0031308){
					gray_value = (1.055*pow(gray_value,1/2.4)-0.055)*255.0;					
				}
				//put values back to Pixel structure
				pixel.blue_intensity = (unsigned char)(gray_value);
				pixel.green_intensity = (unsigned char)(gray_value);
				pixel.red_intensity = (unsigned char)(gray_value);
				
			}
			//fseek() backwards
			fseek(filePointer, -3, SEEK_CUR); 
			//fwrite() the transformed color out to the file 
			fwrite(&pixel, 3, 1, filePointer);
		}
		//handle the row padding
		fseek (filePointer, extra_empty_padding, SEEK_CUR);		
	}	
	//close file and make sure changes are written to disk
	fclose(filePointer);
	
	return 0;
}













