# short example
var x1;
var x2;
var x3;
var x4;


minimize obj: 0.5 * x1 + 0.6 * x2 + 0.64 * x3 + 0.3 * x4;
s.t. c1: x1 + x2 + x3 + x4 = 4000;
s.t. c2: x1 >= 0;
s.t. c3: x2 >= 0;
s.t. c4: x3 >= 0;
s.t. c5: x4 >= 0;
s.t. c6: 0.3 * x1 + 0.05 * x2 + 0.2 * x3 + 0.1 * x4 >= 800;
s.t. c7: 0.1 * x1 + 0.3 * x2 + 0.15 * x3 + 0.1 * x4 >= 600;
s.t. c8: 0.2 * x1 + 0.15 * x2 + 0.2 * x3 + 0.3 * x4 >= 1000;
solve;
display x1, x2, x3, x4;
end;