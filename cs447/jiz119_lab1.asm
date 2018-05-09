# Jingxia Zhu (jiz119)

print_int:
	li	v0, 1
	syscall
	jr	ra
	
newline:
	li	v0, 11
	li	a0,'\n'
	syscall
	jr	ra

.globl main
main:
	li	a0, 1234    
	li	v0, 1
	syscall         #Java: System.out.print(1234);
	
	jal	newline
	
	li	a0, 5678
	jal	print_int
	


