#ifndef ASTAR_HPP
#define ASTAR_HPP

#include "Grid.hpp"

class Astar {
    
    public:
        Grid grid;

        Astar(Grid grid);
        vector<Node> findPath(int startPos[2], int targetPos[2]);
        vector<Node> retracePath(Node startNode, Node endNode);
        int getDistance(Node nodeA, Node nodeB);
};

#endif