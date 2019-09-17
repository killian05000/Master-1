# short example
var p1;
var p2;
var p3;
var p4;
var p5;
var p6;
var s1;
var s2;
var s3;
var s4;
var s5;
var s6;


minimize obj: 240 * p1 + 250  * p2 + 3.6 * s2 + 265 * p3 + 3.75 * s3 + 285 * p4 + 3.975 * s4 + 280 * p5 + 4.275 * s5 + 285 * p6 + 4.2 * s6;
s.t. c1: s2>=1500;
s.t. c2: s3>=1500;
s.t. c3: s4>=1500;
s.t. c4: s5>=1500;
s.t. c5: s6>=1500;
s.t. c6: p1>=2000;
s.t. c7: p2>=1750;
s.t. c8: p3>=2000;
s.t. c9: p4>=2250;
s.t. c10: p5>=2000;
s.t. c11: p6>=1750;
s.t. c12: p2+s2<=6000;
s.t. c13: p3+s3<=6000;
s.t. c14: p4+s4<=6000;
s.t. c15: p5+s5<=6000;
s.t. c16: p6+s6<=6000;
s.t. c17: p1+s1<=6000;
s.t. c18: s1=2750;
solve;
display p1, p2, p3, p4, p5, p6, s1, s2, s3, s4, s5, s6;
end;