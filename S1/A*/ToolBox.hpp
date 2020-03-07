#ifndef TOOLBOX_HPP
#define TOOLBOX_HPP

#include "Node.hpp"
#include <vector>

using namespace std;

bool contains(vector<Node> vec, Node node)
{
    for (int i = 0; i < vec.size(); i++ )
        if (vec.at(i).gridX == node.gridX && vec.at(i).gridY == node.gridY)
            return true;

    return false;
}

#endif
