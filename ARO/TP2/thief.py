from typing import *
from bag import Bag
from item import Item


class Thief:
    def __init__(self, bag: Bag, item_list: List[Item]):
        self.bag = bag
        self.item_list = item_list
        self.valeurMax = 0
        self.count = 0
        self.nbPossibilities = pow(2, len(item_list))

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

    def greedy_fill2(self, capacity: int, item_list: List[Item]):
        self.bag.clear()
        for i in item_list:
            self.bag.add_and_cut(i)
            if self.bag.weight >= capacity :
                break
        return self.bag.value

    def branch_and_bound_fill(self):
        self.bag.clear()
        taken_items = []
        opt, taken_items = self.branch_and_bound(self.item_list, self.bag.size, taken_items)
        print("OPT", opt)
        self.bag.clear()
        for i in taken_items:
            self.bag.add(i)
        print(self.bag)

    def branch_and_bound(self, item_list: List[Item], capacity: int, taken_items: List[Item]):
        tmpSum = sum(i.value for i in taken_items)
        if(len(item_list) == 0):
            return tmpSum, taken_items

        item = item_list[0]
        item_list = item_list[1:]

        if(self.valeurMax < tmpSum):
            self.valeurMax = tmpSum

        if(tmpSum + self.greedy_fill2(capacity, item_list) ) <= self.valeurMax:
            self.count += 1
            print("Vmax : ", self.valeurMax, " | ", self.count, "branches coupes / ", self.nbPossibilities)
            return 0, taken_items


        # If we got a too heavy object (capacity - item.weight < 0), we idon't take it
        sum1=0
        if(capacity - item.weight >= 0):
            sum1, taken_items1 = self.branch_and_bound(item_list, capacity - item.weight, taken_items+[item])

        # but we are still gonna check if there is lighter items after it instead of stopping here
        sum2, taken_items2 = self.branch_and_bound(item_list, capacity, taken_items)

        if(sum1 > sum2):
            return sum1, taken_items1
        else:
            return sum2, taken_items2
