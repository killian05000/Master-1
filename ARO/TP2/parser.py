from item import Item

def parser(file_name):
    try:
        with open(file_name, 'r') as filehandler:
            bag_size = filehandler.readline()
            item_list = []
            for line in filehandler:
                weight, value = line.split(" ")
                item_list.append(Item(float(weight), float(value)))
            return bag_size, item_list
    except FileNotFoundError:
        print("An error occured while trying to read the file :", filename)
        exit(1)
