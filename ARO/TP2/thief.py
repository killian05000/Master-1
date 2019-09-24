from typing import *
from bag import Bag
from item import Item


class Thief:
    def __init__(self, bag: Bag, item_list: List[Item]):
        self.bag = bag
        self.item_list = item_list

    def naive_fill(self):
        self.bag.clear()
        for i in self.item_list:
            if (self.bag.weight + i.weight) > self.bag.size:
                break
            self.bag.add(i)
        print(self.bag)

    def greedy_fill(self):
        self.bag.clear()
        for i in self.item_list:
            self.bag.add_and_cut(i)
            if self.bag.weight >= self.bag.size :
                break
        print(self.bag)
