import sys
import logging
from item import Item
from bag import Bag
from parser import *

filename = 'bag0.txt'
if (len(sys.argv) > 1):
    filename = sys.argv[1]


bag_size, item_list = parser(filename)
b = Bag(bag_size)

for i in range(len(item_list)):
    print(item_list[i])

item_list.sort(key=lambda x: x.ratio, reverse=True)

print("BBBBBBBBBBBBBBBBBB")

for i in range(len(item_list)):
    print(item_list[i])
