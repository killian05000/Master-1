#ifndef GRIDE_HPP
#define GRIDE_HPP

#include "Node.hpp"
#include <vector>

using namespace std;

class Grid {
  public:
    int columnNumber = -1;
    int lignNumber = -1;
    vector<Node> grid;

    Grid();
    Node& get(int i, int j);
    void setParent(int i, int j, int i_parent, int j_parent);
    vector<Node> getNeighbours(Node node);
    void displayGrid();
};

#endif
