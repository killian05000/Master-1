from item import Item

def parser(file_name):
    try:
        line_number = 1
        with open(file_name, 'r') as filehandler:
            bag_size = filehandler.readline()
            item_list = []
            for line in filehandler:
                line_number += 1
                weight, value = line.split(" ")
                item_list.append(Item(int(weight), int(value), line_number))
            return bag_size, item_list
    except FileNotFoundError:
        print("An error occured while trying to read the file :", filename)
        exit(1)
