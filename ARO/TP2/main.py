import sys
import logging

from item import Item

for arg in sys.argv:
    filename = arg



a = Item(1, 10)
b = Item(2, 10)
print(b)




def parser(file_name):
    with open(file_name, 'r') as filehandler:
        bag_size = filehandler.readline()
        item_list = []
        for line in filehandler:
            weight, value = line.split(" ")
            item_list.append(Item(weight, value))
        return bag_size, item_list


bag_size, item_list = parser(filename)
print(len(item_list))

for i in range(len(item_list)):
    print(item_list[i])
