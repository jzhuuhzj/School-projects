# jiz119
# Jingxia Zhu

.include "convenience.asm"
.include "display.asm"

.eqv GAME_TICK_MS      			16
.eqv MAX_BULLETS      			50
.eqv MAX_LIVES         			3
.eqv MAX_ENEMIES         		20
.eqv MAX_BULLETS_ON_SCREEN      10 #how many bullets can be onscreen at a time

.data
# don't get rid of these, they're used by wait_for_next_frame.
last_frame_time:  .word 0
frame_counter:    .word 0
x_coord_player:	  .word 30
y_coord_player:	  .word 52
x_coord_life:     .word 64
y_coord_life:     .word 58
bullet_x:         .byte 0:MAX_BULLETS
bullet_y:         .byte 0:MAX_BULLETS
enemy_x:	  .word	2
enemy_y:	  .word	2
bullets_on_screen:.byte 0:MAX_BULLETS
next_bullet:      .word 0
shots_player:	  .word 0
lives_remaining:  .word 3
enemies_move_counter: .word 0
enemies_move_check:   .word 0
enemies_alive: .byte 0:20
last_frame:  .word 0

player_image: 	  .byte
	5   0   5   0   5
 	0   5   5   5   0
 	5   7   0   7   5
 	5   5   5   5   5
 	1   0   2   0   1
enemy_image: 	  .byte
 1   0   1   0   1		
 2   2   2   2   2	
 2   7   0   7   2	
 0   2   6   2   0	
 0   0   2   0   0
 
press_b: .asciiz "Press b"
to: .asciiz " to "
start: .asciiz "start"

game: 	          .word 0	
pat_G	pat_A	pat_M	pat_E 
over:             .word 0
pat_O	pat_V	pat_E	pat_R pat_bang 

.text

# --------------------------------------------------------------------------------------------------

.globl main
main:
	# set up anything you need to here,
	# and wait for the user to press a key to start.
	jal     start_screen
	li 	a0,500
	li 	v0,32
	syscall
_main_loop:
	# check for input,
	# update everything,
	# then draw everything.
	
	jal	check_player_input
	jal	check_bullet_input
	jal	move_bullets
	jal	move_enemies
	jal	display_player_lives
	jal	display_player_shots
	jal	draw_player
	jal	draw_enemies
	jal	draw_bullets
	jal	display_update_and_clear
	jal	wait_for_next_frame
		
	b	_main_loop
_game_over:
	jal	game_over_screen
	exit

# --------------------------------------------------------------------------------------------------
# call once per main loop to keep the game running at 60FPS.
# if your code is too slow (longer than 16ms per frame), the framerate will drop.
# otherwise, this will account for different lengths of processing per frame.

wait_for_next_frame:
enter	s0
	lw	s0, last_frame_time
_wait_next_frame_loop:
	# while (sys_time() - last_frame_time) < GAME_TICK_MS {}
	li	v0, 30
	syscall # why does this return a value in a0 instead of v0????????????
	sub	t1, a0, s0
	bltu	t1, GAME_TICK_MS, _wait_next_frame_loop
	# save the time
	sw	a0, last_frame_time
	# frame_counter++
	lw	t0, frame_counter
	inc	t0
	sw	t0, frame_counter
leave	s0

# --------------------------------------------------------------------------------------------------

# .....and
# here's where all the rest of your code goes :D

check_player_input:
enter	
	jal 	input_get_keys
	lw	t1, x_coord_player
	lw	t2, y_coord_player
	_check_left:
		and	t0, v0, KEY_L
		beq	t0, 0, _check_right
		beq	t1, 2, _store_coordinates
		dec	t1
	_check_right:
		and	t0, v0, KEY_R
		beq	t0, 0, _check_up
		beq	t1, 57, _store_coordinates
		inc	t1
	_check_up:
		and	t0, v0, KEY_U
		beq	t0, 0, _check_down
		beq	t2, 46, _store_coordinates
		dec	t2
	_check_down:
		and	t0, v0, KEY_D
		beq	t0, 0, _store_coordinates
		beq	t2, 52, _store_coordinates
		inc	t2
	_store_coordinates:
		sw	t1, x_coord_player
		sw	t2, y_coord_player
leave

check_bullet_input: ##check if the user pressed "b" and if any two presses is happning at the same frame
enter	s0
	lw	s0, shots_player # counter for times shooting
	_check_bullet_input_loop:
		lw	t5, next_bullet
		lw	t6, frame_counter
		bgt 	t5, t6, _check_bullet_input_exit
		
		jal	input_get_keys
		and	t0, v0, KEY_B
		beq	t0, 0, _check_bullet_input_exit # if b key is not pressed, exit
		inc	s0  #increase 1 every time "b" is pressed
		sw	s0, shots_player
	
		lw	t6, frame_counter
		addi	t6, t6, 10
		sw	t6, next_bullet
		jal	find_free_slot  
		beq	v0, MAX_BULLETS, _check_bullet_input_exit  # if free slots exist in the array of onscreen bullets
	_on_screen_condition:
		lw t3, y_coord_player
		sb t3, bullet_y(v0)

		lw t4, x_coord_player
		add t4, t4, 2
		sb t4, bullet_x(v0)

		li t2, 1
		sb t2, bullets_on_screen(v0)
	_check_bullet_input_exit:	
leave	s0
##########################################################screen display

start_screen: 
enter
	li	a0, 10
	li 	a1, 10
	la 	a2, press_b
	jal 	display_draw_text
	
	jal	display_update_and_clear
	
	li	a0, 18
	li 	a1, 20
	la 	a2, to
	jal 	display_draw_text
	
	jal	display_update_and_clear
	
	li	a0, 18
	li 	a1, 30
	la 	a2, start
	jal 	display_draw_text
	
	jal	display_update_and_clear
	
	jal 	input_get_keys
	bne	v0, KEY_B, start_screen
	
leave

game_over_screen:
enter
	li 	a0 30
	li 	a1 30
	lw 	a2, game
	jal 	display_draw_text
	li 	a0 30
	li 	a1 40
	lw 	a2, over
	jal 	display_draw_text
	
	b	game_over_screen
leave

########################################################## draw and display

draw_player:
enter
	lw	a0, x_coord_player
	lw	a1, y_coord_player
	la	a2, player_image # pointer to the image
	jal	display_blit_5x5
leave

draw_enemies:
enter	s0, s1
lw 	t1, enemy_x
li	t1, 2
lw 	t2, enemy_y
li 	t2, 2 		
li 	s1, 0			
	_draw_enemies_loop_1: 		
		move 	a0, t1
		move 	a1, t2
		lbu	s3, enemies_alive(s2)
		la 	a2, enemy_image	
		jal 	display_blit_5x5
		addi 	t1, t1, 10
		inc 	s0
		blt	s0, 5, _draw_enemies_loop_1
	 _draw_enemies_loop_2:
	 	li	t1, 2	
	 	li 	s0, 0	
	 	addi 	t2, t2, 7		
	 	inc 	s1					
		blt	s1, 4, _draw_enemies_loop_1			
leave	s0, s1


draw_bullets:
enter   s0
	li	s0, 0
	_draw_bullets_loop:
		lb 	t1, bullets_on_screen(s0)
		beq 	t1, 0, _find_next
		lb	a0, bullet_x(s0)
		lb	a1, bullet_y(s0)
		li 	a2, COLOR_RED
		jal 	display_set_pixel
	_find_next:
		inc 	s0
		bne 	s0, MAX_BULLETS, _draw_bullets_loop
	 _exit_draw_bullets:
leave   s0

display_player_shots:
enter
	li	a0, 1   #top-left x
	li	a1, 58  #top-left y
	lw	t0, shots_player	
	li	t1, MAX_BULLETS		
	sub 	a2, t1, t0
	sub	t2, t1, a2
	sw	t2, shots_player				
	bgt	t2, 50, game_over_screen							
	jal 	display_draw_int 
leave

display_player_lives:
enter	s0
	li	s0, MAX_LIVES
	_display_player_lives_loop:
		lw	a0, x_coord_life
		lw	a1, y_coord_life
		la	a2, player_image # pointer to the image
		mul	t0, s0, 6
		sub	a0, a0, t0
		jal	display_blit_5x5
		dec	s0
		bgt	s0, 0, _display_player_lives_loop
leave	s0

erase_life:
enter
	lw	a0, x_coord_life #top-left x
	lw	a1, y_coord_life #top-left y
	li	a2, 5 #width
	li	a3, 5 #height
	li	v1, COLOR_BLACK #color
	jal	display_fill_rect
leave

erase_shots:
enter
	li	a0, 1 #top-left x
	li	a1, 58 #top-left y
	li	a2, 11 #width
	li	a3, 11 #height
	li	v1, COLOR_BLACK #color
	jal	display_fill_rect
leave

#####################################################################

find_free_slot:
enter	s0
	la	t0, bullets_on_screen
	li	s0, 0    #index
	_loop_find_free_slot:
		lb	t1, bullets_on_screen(s0)
		move	v0, s0
		beq	t1, 0, _exit_here
		inc	s0
		blt	s0, MAX_BULLETS, _loop_find_free_slot	
	_exit_here:
leave	s0


##################################################################### move and update

move_bullets:
enter	s0
	li	s0, 0
	_loop_move_bullets:
		lb	t0, bullet_y(s0)
		lb	t1, bullets_on_screen(s0)
		bne	t1, 1, _check_next_bullet
		sub	t0, t0, 1
		blt	t0, 0, _set_on_screen_flag
		sb	t0, bullet_y(s0)
	_check_next_bullet:	
		inc	s0
		bgt	s0, MAX_BULLETS, _exit_move_bullets
		j	_loop_move_bullets
	_set_on_screen_flag:
		li	t2, 0
		sb	t2, bullets_on_screen(s0)
		j	_check_next_bullet
	_exit_move_bullets:
leave	s0

move_enemies:
enter
	lw 	t2, frame_counter			
	li 	t0, 10					
	rem 	t3, t2, t0				
	bne 	t3, 0, _move_enemies_exit
	lw 	t1, enemies_move_check
	
	_move_enemies_loop:
	beq 	t1, 0, _move_enemies_right
	beq 	t1, 1, _move_enemies_left
	lw 	t0, enemy_x
	lw 	t0, enemy_y
	_move_enemies_right:
		lw 	t0, enemy_x					
		addi 	t0, t0, 1					
		bge 	t0, 17, _move_enemies_down			
		sw 	t0, enemy_x	
		j 	_move_enemies_exit	
	_move_enemies_down:
		xor 	t1, t1, 1
		sw 	t1, enemies_move_check
		lw 	t0, enemy_y
		inc	t0
		sw 	t0, enemy_y
		bge 	t0, 10, _move_enemies_exit
		j 	_move_enemies_exit
	_move_enemies_left:
		lw 	t0, enemy_x
		dec	t0
		sw 	t0, enemy_x
		blt 	t0, 2, _move_enemies_down
		j 	_move_enemies_exit
	_move_enemies_exit:	
leave
