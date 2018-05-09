#Jingxia Zhu (jiz119)

.include "led_keypad.asm"

#Data section
.data
x_coord: .word	32
y_coord: .word	32
 

#Text section
.text
.globl main
main:
_infinite_loop_begin: 
li	a0, 17
li	v0, 32
syscall
jal	check_input
jal	draw_dot
jal 	display_update_and_clear
b _infinite_loop_begin
_infinite_loop_end:


draw_dot:
push	ra

lw	a0, x_coord
lw	a1, y_coord

li 	a2, COLOR_YELLOW

jal 	display_set_pixel 

pop	ra
jr 	ra


check_input:
push	ra

jal 	input_get_keys	

beq 	v0, KEY_L, x_coord_decrement 
_x_coord_decre_ret:

beq 	v0, KEY_R, x_coord_increment 
_x_coord_incre_ret:

beq 	v0, KEY_U, y_coord_decrement 
_y_coord_decre_ret:

beq 	v0, KEY_D, y_coord_increment 
_y_coord_incre_ret:

lw	t0, x_coord
lw	t1, y_coord

andi 	t2, t0, 63
andi 	t3, t1, 63

sw	t2,x_coord
sw	t3,y_coord 

pop	ra
jr 	ra


x_coord_decrement:
lw 	t0, x_coord
subi	t0, t0, 1	
sw	t0, x_coord
j	_x_coord_decre_ret


x_coord_increment:
lw 	t0, x_coord
addi	t0, t0, 1	
sw	t0,x_coord
j	_x_coord_incre_ret


y_coord_decrement:
lw	t1, y_coord
subi	t1, t1, 1	
sw	t1, y_coord
j 	_y_coord_decre_ret


y_coord_increment:
lw	t1, y_coord
addi	t1, t1, 1	
sw 	t1, y_coord
j 	_y_coord_incre_ret


