#Jingxia Zhu(jiz119)

# 6,    5,    5,    16
# 26,   21,   16,   0
# 0x3f, 0x1f, 0x1f, 0xffff

.data
	opcode: .asciiz "\nopcode = "
	rs: .asciiz "\nrs = "
	rt: .asciiz "\nrt = "
	immediate: .asciiz "\nimmediate = "

.text

encode_instruction:
	push	ra

	# FILL ME IN!
	# shift left
	sll	t0, a3, 0
	sll	t1, a2, 16
	sll	t2, a1, 21
	sll	t3, a0, 26
	# masking 
	andi	t0, t0,	0xffff
	# ORing everything together
	or	t0, t0, t1
	or	t0, t0, t2
	or	a0, t0, t3
	# print the result in hex 
	li	v0, 34
	syscall
	# print the new line character
	li	v0, 11
	li	a0, '\n'
	syscall	

	pop	ra
	jr	ra

decode_instruction:
	push	ra

	# FILL ME IN!
	push	s0
	# print opcode string
	move	s0, a0	
	la	a0, opcode
	li	v0, 4
	syscall
	# extract the value of the opcode and print
	move	a0, s0
	srl	t0, a0, 26
	andi	a0, t0, 0x3f
	li	v0, 1
	syscall
	
	# print the rs string
	la	a0, rs
	li	v0, 4
	syscall
	# extract the value of rs and print
	move	a0, s0
	srl	t0, a0, 21
	andi	a0, t0, 0x1f
	li	v0, 1
	syscall
	
	# print the rt string
	la	a0, rt
	li	v0, 4
	syscall
	# extract the value of rt and print
	move	a0, s0
	srl	t0, a0, 16
	andi	a0, t0, 0x1f
	li	v0, 1
	syscall
	
	# print the immediate string
	la	a0, immediate
	li	v0, 4
	syscall
	# extract the value of the immediate and print
	move	a0, s0
	sll	t0, a0, 16
	sra	a0, t0, 16
	li	v0, 1
	syscall
	
	pop	s0
	pop	ra
	jr	ra

.globl main
main:
	# addi t0, s1, 123
	li	a0, 8
	li	a1, 17
	li	a2, 8
	li	a3, 123
	jal	encode_instruction

	# beq t0, zero, -8
	li	a0, 4
	li	a1, 8
	li	a2, 0
	li	a3, -8
	jal	encode_instruction

	li	a0, 0x2228007B
	jal	decode_instruction

	li	a0, '\n'
	li	v0, 11
	syscall

	li	a0, 0x1100fff8
	jal	decode_instruction

	# exit the program cleanly
	li	v0, 10
	syscall
