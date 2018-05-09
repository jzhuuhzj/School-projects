# Jingxia Zhu (jiz119)

#Data section
.data
small:	.byte	200
medium:	.half	400
large:  .word	0
.eqv NUM_ITEMS 5
values:	.word 0:NUM_ITEMS

#Text section
.text 
.globl main		
main:   
	lbu	s0, small
	
	lh	s1, medium

	mul	t0, s1, s0
	sw	t0, large
	
	li  	v0, 1
	move	a0, t0	
	syscall
	
	li	s0, 0
ask_loop_top:  # while(...)
	blt	s0, NUM_ITEMS, ask_loop_body
	b ask_loop_exit
ask_loop_body: # {
	li	v0, 5
	syscall # asks the user for a number
	#now, whatever the user typed in is in v0 instead of the 5 we put there.
	la	t0, values
	mul     t1, s0, 4
	lw      a1, (t0)
	add     t0, t0, t1                  		
	sw 	v0, (t0) 
	addi    s0, s0, 1
	b ask_loop_top
ask_loop_exit: # }
	
		
	
