# short example
var x1;
var x2;
var x3;
var x4;
var x5;
var x6;

minimize obj:x1 + x2 + x3 + x4 + x5 + x6;
s.t. c1: x1 + x6 >= 9;
s.t. c2: x2 + x1 >= 21;
s.t. c3: x3 + x2 >= 25;
s.t. c4: x4 + x3 >= 16;
s.t. c5: x4 + x5 >= 30;
s.t. c12: x5 + x6 >= 12;


s.t. c6: x1 >= 0 ;
s.t. c7: x2 >= 0 ;
s.t. c8:x3>= 0;
s.t. c9:x4>= 0;
s.t. c10:x5>= 0;
s.t. c11:x6>= 0;


solve;
display x1, x2, x3, x4, x5, x6;
end;