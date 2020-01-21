# Makefile 
#

all: code2mot tokenize calc_cosine

code2mot: code2mot.c
	gcc -o code2mot code2mot.c

tokenize: tokenize.c
	gcc -o tokenize tokenize.c

calc_cosine: calc_cosine.c
	gcc -o calc_cosine calc_cosine.c -lm

clean:
	rm code2mot tokenize calc_cosine
