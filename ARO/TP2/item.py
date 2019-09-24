from __future__ import annotations

class Item:
    def __init__(self, weight: int, value: int, identifier: int):
        self.weight = weight
        self.value = value
        self.ratio = float(self.value) / float(self.weight)
        self.identifier = identifier

    def __eq__(self, o: Item) -> boolean:
        return (
                self.__class__ == o.__class__ and
                self.weight == o.weight and
                self.value == o.value and
                self.identifier == o.identifier
         )

    def __str__(self):
        return f"(ID#{self.identifier} weight : {self.weight} | value : {self.value} | ratio : {self.ratio})"

    def __repr__(self):
        return self.__str__()
