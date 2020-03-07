#ifndef NODE_HPP
#define NODE_HPP

class Node {
  public:
    bool walkable = false;
    int gridX = 0;
    int gridY = 0;

    int gCost=0; // how far away the node is from the start Node
    int hCost=0; // how far away the node is from the end Node

    int parent_gridX = 0;
    int parent_gridY = 0;

    Node();
    Node(int gridX, int gridY, bool walkable);
    int fCost();
};

#endif
