from __future__ import annotations

class Item:
    def __init__(self, weight: int, value: int):
        self.weight = weight
        self.value = value
        self.ratio = float(self.value) / float(self.weight)

    def __eq__(self, o: Item) -> boolean:
        return (
                self.__class__ == o.__class__ and
                self.weight == o.weight and
                self.value == self.value
         )

    def __str__(self):
        return f"weight : {self.weight} | value : {self.value} | ratio : {self.ratio}"
