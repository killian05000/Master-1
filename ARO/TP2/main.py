import sys
import logging
import time
from thief import Thief
from item import Item
from bag import Bag
from parser import *

filename = 'bag0.txt'
if (len(sys.argv) > 1):
    filename = sys.argv[1]


bag_size, item_list = parser(filename)
item_list.sort(key=lambda x: x.ratio, reverse=True)
thief = Thief(Bag(bag_size), item_list)

'''
for i in item_list:
    print(i)

exit(1)

item_list = item_list[1:]


for i in item_list:
    print(i)

exit(1)
'''

################## Naive sorting ##################
print("-------------------------- Naive sorting --------------------------")
thief.naive_fill()
print("-------------------------- Naive sorting --------------------------\n")
################## Naive sorting ##################


##################  Greedy sorting ##################
print("-------------------------- Greedy sorting --------------------------")
thief.greedy_fill()
print("-------------------------- Greedy sorting --------------------------\n")
##################  Greedy sorting ##################

##################  Branch and bound sorting ##################
print("-------------------------- Branch and bound sorting --------------------------")
start_time = time.time()
thief.branch_and_bound_fill()
print("Execution time = ", int((time.time() - start_time)*1000)/1000, " s.")
print("-------------------------- Branch and bound sorting --------------------------\n")
##################  Branch and bound sorting ##################
