#include "Node.hpp"
#include <iostream>

Node::Node(int gridX, int gridY, bool walkable): gridX(gridX), gridY(gridY), walkable(walkable), hCost(0), gCost(0)
{}

int Node::fCost()
{
    //std::cout << "[Node at " << gridX << "," << gridY << "] -> hcost = " << hCost << " gcost = " << gCost << std::endl;
    return hCost + gCost;
}
