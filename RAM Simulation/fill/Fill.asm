// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

@SCREEN //A+pixelYoureAt = pixelMemory

@KBD //keyboard 1 or 0

@R0 //storing if it is blacken or not
M=0

(LOOP)
    @8192
    D=A
    @R1 //amount of pixels 
    M=D
    @KBD
    D=M
    @BLACKEN
    D;JNE
    @8192
    D=A
    @R1 //amount of pixels 
    M=D
    @WHITEN
    A;JMP

(BLACKEN)
    @R1
    M=M-1
    D=M
    @LOOP
    D;JLT
    @R1
    D=M
    @SCREEN
    A=A+D
    M=-1 //blackens all the bits
    @BLACKEN
    A;JMP

(WHITEN)
    @R1
    M=M-1
    D=M
    @LOOP
    D;JLT
    @R1
    D=M
    @SCREEN
    A=A+D
    M=0 //blackens all the bits
    @WHITEN
    A;JMP
