from __future__ import annotations
from item import Item

class Bag:
    def __init__(self, size: int):
        self.size = int(size)
        self.item_list = []
        self.weight = 0
        self.value = 0

    def add(self, item: Item):
        if(self.weight + item.weight <= float(self.size)):
            self.item_list.append(item)
            self.weight += item.weight
            self.value += item.value
        else:
            print("--Bag full : ", self.weight)

    def add_and_cut(self, item: Item):
        if(self.weight + item.weight <= float(self.size)):
            self.item_list.append(item)
            self.weight += item.weight
            self.value += item.value
        else:
            remaining_capacity = self.size - self.weight
            ratio = remaining_capacity/item.weight
            i = Item(item.weight * ratio, item.value * ratio, item.identifier)
            self.item_list.append(i)
            self.weight += i.weight
            self.value += i.value
            print("--Object ID#", item.identifier, "cut, bag full --")

    def clear(self):
        self.item_list = []
        self.weight = 0
        self.value = 0

    def __str__(self):
        displayed_list = ""
        for item in self.item_list:
            displayed_list += str(item) + "\n"
        displayed_list = displayed_list[:-1] #to remove the last \n

        return f"Size : {self.size} / Weight : {self.weight} / Value : {self.value} / Object number : {len(self.item_list)} \
        \nItem list : \n{displayed_list}"
