import sys
import logging
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
