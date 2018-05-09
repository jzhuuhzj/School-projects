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
enemies_move_track:   .word 0
enemies_alive:        .byte 0:20   # 0 for alive; 1 for not  
last_frame:  	      .word 0
enemies_left:  	      .word 20

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
 
press_b: .asciiz "press b"
to: .asciiz " to "
start: .asciiz "start"

game: 	.asciiz "game"	
over:  .ascii "over"

you: .ascii "you"
win: .ascii "win!"

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

	jal	check_collision
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
	jal	display_update_and_clear
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
################################################################################# check ppoints
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


check_collision:
enter	s0, s1, s2, s3

	lw 	t0, enemy_x
	lw 	t1, enemy_y

	li 	s0, 0 			
	li 	s1, 0			
	li 	s2, 0	  # bullets_on_screen counter		
	li 	s3, 0	  # count for each enemies 		
		_check_collision_loop:
			lb 	t3, bullet_x(s2)
			lb 	t4, bullet_y(s2)
			
			beq 	s2, MAX_BULLETS_ON_SCREEN, _check_next_enemy_alive
			
			lb 	t2, enemies_alive(s3)	# not alive, check for next
			beq 	t2, 1, _check_next_enemy_alive

			lb 	t5, bullets_on_screen(s2)  # not on screen, check for next
	 		beq 	t5, 0, _check_next_active_bullet  
		#checked for alive enemy and onscreen bullet
		#then, check if bullet hit the enemy
			blt 	t3, t0, _check_next_active_bullet # bullet_x < enemy least x coord
			blt 	t4, t1, _check_next_active_bullet # bullet_y < enemy least y coord
			
			addi 	t5, t0, 5  
			addi 	t6, t1, 5
			
			bgt 	t3, t5 _check_next_active_bullet # bullet_x > enemy largest x coord
			bgt 	t4, t6 _check_next_active_bullet # bullet_y > enemy leargest y coord
				
			li 	t5, 0
			sb 	t5, bullets_on_screen(s2)   # mark 0 for not on screen any more
			li 	t5, 1
			sb 	t5, enemies_alive(s3)	# mark 1 for not alive any more
			j 	_check_next_active_bullet

		_check_next_active_bullet:
			inc 	s2
			j 	_check_collision_loop

		_check_next_enemy_alive:
			inc 	s3
			li 	s2, 0
			blt 	s1, 3, _check_next_row
			li 	s1, 0
			blt 	s0, 4, _check_next_column
			j 	_exit_check_collision

			_check_next_row:
			inc 	s1
			addi 	t1, t1, 7 
			j 	_check_collision_loop

			_check_next_column:
			inc 	s0
			addi 	t0, t0, 10 
			lw	t1, enemy_y 
			j 	_check_collision_loop
																																					
	_exit_check_collision:
leave 	s0, s1, s2, s3
###########################################################################screen display

start_screen: 
enter
	
	li	a0, 10
	li 	a1, 10
	la 	a2, press_b
	jal 	display_draw_text
	
	li	a0, 18
	li 	a1, 20
	la 	a2, to
	jal 	display_draw_text
	
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
	li 	a0 10
	li 	a1 10
	lw 	a2, game
	jal 	display_draw_text
	li 	a0 18
	li 	a1 20
	lw 	a2, over
	jal 	display_draw_text
	
	jal 	display_update_and_clear
	li 	v0, sys_exit
	syscall
leave

you_win_screen:
enter
	li 	a0 10
	li 	a1 10
	lw 	a2, you
	jal 	display_draw_text
	li 	a0 18
	li 	a1 20
	lw 	a2, win
	jal 	display_draw_text
	
	jal 	display_update_and_clear
	li 	v0, sys_exit
	syscall
leave

########################################################################### draw and display

draw_player:
enter
	lw	a0, x_coord_player
	lw	a1, y_coord_player
	la	a2, player_image # pointer to the image
	jal	display_blit_5x5
leave

draw_enemies: 
enter	s0, s1, s2
	lw 	t1, enemy_x
	lw 	t2, enemy_y

	li 	s0, 0 		
	li 	s1, 0 
	li 	s2, 0  #keep track of enemies left alive
	
	_draw_enemies_loop_1: 
		move 	a0, t1
		move 	a1, t2
		lb 	t3, enemies_alive(s2)
		beq 	t3, 1, _next  #not alive
		la 	a2, enemy_image #pointer to enemy image	
		jal 	display_blit_5x5
	  _next:
		addi 	t2, t2, 7 		
		inc 	s0
		inc 	s2   
		blt 	s0, 4, _draw_enemies_loop_1
	  _draw_enemies_loop_2:
		addi 	t1, t1, 10 		
	 	inc 	s1						
	 	beq 	s1, 5, _exit_draw_enemies
	 	lw 	t2, enemy_y		
	 	li 	s0, 0				
		j 	_draw_enemies_loop_1			
	 _exit_draw_enemies: 
	 	beq	s2, 0, you_win_screen	
leave	s0, s1, s2

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
	lw	s0, lives_remaining
	_display_player_lives_loop:
		lw	a0, x_coord_life
		lw	a1, y_coord_life
		
		beq	s0, 0, game_over_screen 
		
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


################################################################################# move and update

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
enter	s0
 	lw 	t2, frame_counter
 	lw 	t3, last_frame
 	sub 	s0, t2, t3
 	bge 	s0, 10, _move_enemies_loop
 	j 	_exit_enemies_move
 	_move_enemies_loop:
  		lw 	t4, frame_counter
  		sw 	t4, last_frame
  		lw 	t1, enemy_x
  		lw 	t2, enemy_y
  		lw 	s0, enemies_move_track
  		
  		beq 	s0, 0, _move_enemies_right
  		beq 	s0, 1, _move_enemies_down
 		beq 	s0, 2, _move_enemies_left
 		beq 	s0, 3, _move_enemies_down
 		
 		_move_enemies_right:
   			beq 	t1, 17, _inc_track
  			inc	t1
   			sw 	t1, enemy_x
   			j 	_exit_enemies_move
  
   		_move_enemies_down:
   			beq 	t2, 10, _last_move_check
   			inc	t2
   			sw 	t2, enemy_y
   			inc	s0
   			bge 	s0, 4, _reset_track
   			sw 	s0, enemies_move_track
   			j 	_exit_enemies_move
   		
   		_move_enemies_left:
   			beq 	t1, 2, _inc_track
   			dec	t1
   			sw 	t1, enemy_x
			j 	_exit_enemies_move
		
		_reset_track:	# set back to the first step --> left
  			li 	s0, 0
  			sw 	s0, enemies_move_track   
 			j 	_exit_enemies_move

 		_inc_track:
  			inc	s0
  			sw 	s0, enemies_move_track
  			j 	_move_enemies_loop

 		_last_move_check:
  			beq 	s0, 3, _last_move_right   
   			j 	_last_move_left
 			beq 	s0, 1, _last_move_left
 		
  		_last_move_right:
    			li 	s0, 0	#right
   			inc	t1
   			sw 	t1, enemy_x
   			sw 	s0, enemies_move_track
   			j 	_exit_enemies_move
  		
		_last_move_left:
    			li 	s0, 2	#left
    			dec	t1
    			sw 	t1, enemy_x
    			sw 	s0, enemies_move_track
   			j 	_exit_enemies_move
 	_exit_enemies_move:
leave  s0



