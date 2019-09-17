import sys
import logging.FileHandler

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
        for line in fileHandler:
            weight, value = line.split(" ")
            item_list.append(Item(weight, value))
        return bag_size, item_list


print(parser(filename))
