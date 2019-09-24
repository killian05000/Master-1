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

    def branch_and_bound_fill(self):
        self.bag.clear()
        taken_items = []
        opt, taken_items = self.branch_and_bound(self.item_list, self.bag.size, taken_items)
        for i in taken_items:
            self.bag.add(i)
        print(self.bag)

    def branch_and_bound(self, item_list: List[Item], capacity: int, taken_items: List[Item]) -> float:
        if(len(item_list) == 0):
            #print("je sors dans la condition feuille")
            return sum(i.value for i in taken_items), taken_items

        item = item_list[0]
        item_list = item_list[1:]

        if(capacity - item.weight < 0):
            #print("je sors dans la condition bag obèse")
            return sum(i.value for i in taken_items), taken_items

        sum1, taken_items1 = self.branch_and_bound(item_list, capacity - item.weight, taken_items+[item])
        sum2, taken_items2 = self.branch_and_bound(item_list, capacity, taken_items)

        #print("sum1 : ", sum1, " | sum2 : ", sum2)

        if(sum1 > sum2):
            #print("je sors avec SUM1")
            return sum1, taken_items1
        else:
            #print("je sors avec SUM2")
            return sum2, taken_items2
