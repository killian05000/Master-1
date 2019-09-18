# short example
var x11;
var x12;
var x13;
var x21;
var x22;
var x23;
var x31;
var x32;
var x33;

minimize obj: 21 * x11 + 50 * x12 + 40 * x13 + 35 * x21 + 30 * x22 + 20 * x23 + 55 *x31 + 20 * x32 + 25 * x33;
s.t. c1: x11 + x12 + x13  <= 275;
s.t. c2: x21 + x22 + x23 <= 400;
s.t. c3: x31 + x32 + x33 <= 300;
s.t. c4: x11 + x21 + x31 >= 200;
s.t. c5: x12 + x22 + x32 >= 550;
s.t. c6: x13 + x23 + x33 >= 225;
s.t. c7: x11 >= 0 ;
s.t. c8:x12>= 0;
s.t. c9:x13>= 0;
s.t. c10:x21>= 0;
s.t. c11:x22>= 0;
s.t. c12:x23>= 0;
s.t. c13:x31>= 0;
s.t. c14:x32>= 0;
s.t. c15:x33>= 0;
# s.t. c16: x4 >= 0;
#s.t. c17: x5 >= 0;
#s.t. c18: x1 + x2 + x3 + x4 + x5 <=100000;
solve;
display x11, x12, x13, x21, x22, x23, x31,x32, x33;
end;