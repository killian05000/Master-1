#include "Astar.hpp"
#include "Node.hpp"
#include "ToolBox.hpp"
#include <vector>
#include <iostream>
#include <cmath>
#include <cassert>

using namespace std;

Astar::Astar(Grid grid): grid(grid) {}

vector<Node> Astar::findPath(int startPos[], int targetPos[])
{
    cout << "[entering algo, looking for a path]" << endl;
    Node startNode = grid.get(startPos[0], startPos[1]);
    Node targetNode = grid.get(targetPos[0], targetPos[1]);

    vector<Node> openSet = vector<Node>();
    vector<Node> closedSet = vector<Node>();

    openSet.push_back(startNode);
    //openSet.push_back(targetNode);


    while(openSet.size() > 0)
    {
        //cout << endl << "Open set size = " << openSet.size() << endl;
        //cout << "Openset : " << endl;
        // for(int i=0; i<openSet.size(); i++)
        // {
        //     cout << openSet.at(i).gridX << openSet.at(i).gridY << " : g = " << openSet.at(i).gCost << " h = " << openSet.at(i).hCost << endl;
        // }

        Node currentNode = openSet.front();
        //cout << endl << "first Node of Openset : " << openSet.front().gridX << " / " << openSet.front().gridY << endl;


        int index=0;
        //cout << "comparing which node has the best f cost of the openSet" << endl;
        for (int i = 1; i < openSet.size(); i++)
        {
            //cout << "fcost : " << openSet.at(i).fCost() << " ; " << currentNode.fCost() << " // hcost openSet(i)/currentNode : " << openSet.at(i).hCost << " ; " << currentNode.hCost << endl;
            if ((openSet.at(i).fCost() < currentNode.fCost()) || (openSet.at(i).fCost() == currentNode.fCost() && openSet.at(i).hCost < currentNode.hCost))
            {
                currentNode = openSet.at(i);
                //cout << "Node at " << currentNode.gridX << "," << currentNode.gridY << " choisis" << endl;
                index = i;
            }
        }
        //cout << "current Node (after comparing fCosts) = " << currentNode.gridX << " / " << currentNode.gridY << endl;
        openSet.erase(openSet.begin()+index);
        closedSet.push_back(currentNode);

        if (currentNode.gridX == targetNode.gridX && currentNode.gridY == targetNode.gridY)
        {
            //cout << "current Node = " << currentNode.gridX << "," << currentNode.gridY << " son parent est " << currentNode.parent_gridX << "," << currentNode.parent_gridY << endl;
            cout << endl << "[path found, exiting algo]" << endl;
            // cout << "start Node = " << startNode.gridX << "," << startNode.parent_gridY << endl;
            // while(currentNode.parent_gridX != startNode.gridX || currentNode.parent_gridY != startNode.gridY)
            // {
            //   cout << "-> " << currentNode.gridX << "," << currentNode.gridY << " parent = " << currentNode.parent_gridX << "," << currentNode.parent_gridY << endl;
            //   currentNode = grid.get(currentNode.parent_gridX, currentNode.parent_gridY);
            //   cout << "donc le parent est bien = " << currentNode.gridX << "," << currentNode.gridY  << endl;
            // }
            return retracePath(startNode, currentNode);
        }


        vector<Node> neighbours = grid.getNeighbours(currentNode);
        for (int i = 0; i < neighbours.size(); i++ )
        {
            //cout << "---->Testing neighbour at " << neighbours.at(i).gridX << "," << neighbours.at(i).gridY << endl;
            if(!neighbours.at(i).walkable || contains(closedSet, neighbours.at(i)) )
            {
                //cout << "Neighbour not walkable or in closedSet" << endl;
                continue;
            }

            int newMovementCostToNeighbour = currentNode.gCost + getDistance(currentNode, neighbours.at(i));

            if (newMovementCostToNeighbour < neighbours.at(i).gCost || !contains(openSet, neighbours.at(i)))
            {
                neighbours.at(i).gCost = newMovementCostToNeighbour;
                neighbours.at(i).hCost = getDistance(neighbours.at(i), targetNode);
                //cout << "Neighbour at " << neighbours.at(i).gridX << "," << neighbours.at(i).gridY << " gcost = " << neighbours.at(i).gCost << " hcost = " << neighbours.at(i).hCost << endl;
                //cout << "parent: " << (neighbours.at(i).parent == nullptr) << endl;
                // cout << " --------------> " << neighbours.at(i).parent_hCost << " = " << currentNode.hCost << " / " << neighbours.at(i).parent->gCost << " = " << currentNode.gCost << endl;

                neighbours.at(i).parent_gridX = currentNode.gridX;
                neighbours.at(i).parent_gridY = currentNode.gridY;

                //cout << "--------------------------------" << endl;
                //cout << "Ajout du node " << currentNode.gridX << "," << currentNode.gridY << " comme parent du node " << neighbours.at(i).gridX << "," << neighbours.at(i).gridY << endl;
                //cout << "--------------------------------" << endl;

                if(!contains(openSet, neighbours.at(i)))
                {
                    //cout << "adding neighbour " << neighbours.at(i).gridX << "," << neighbours.at(i).gridY << " to the openSet" << endl;
                    openSet.push_back(neighbours.at(i));
                    grid.setParent(neighbours.at(i).gridX, neighbours.at(i).gridY, neighbours.at(i).parent_gridX, neighbours.at(i).parent_gridY);
                }
                else
                {
                    //cout << "Neighbour already present in the openset" << endl;
                }

            }

        }
    }
    vector<Node> emptyVec;
    //cout << "Aucun chemin trouvé open set vide, renvoi d'un vecteur de node vide" << endl;
    return emptyVec;

}

vector<Node> Astar::retracePath(Node startNode, Node endNode)
{
    vector<Node> path;
    Node currentNode = endNode;
    // cout << "AAAAAAAAAAAAAAAAAAAAAAAAAAH -> " << grid.get(4,1).parent_gridX << "," << grid.get(4,1).parent_gridY << endl;
    // cout << "current Node : " << currentNode.gridX << "," << currentNode.gridY <<" start node : " << startNode.gridX << "," << startNode.gridY << endl;
    // cout << "Parent du current Node : " << currentNode.parent_gridX << "," << currentNode.parent_gridY <<" parent du start node : " << startNode.parent_gridX << "," << startNode.parent_gridY << endl;

    while(currentNode.gridX != startNode.gridX || currentNode.gridY != startNode.gridY)
    {
        //cout << "RETRACING PATH -----------> node at " << currentNode.gridX << "," << currentNode.gridY << " added" << endl;
        //cout << "RETRACING PATH -----------> Le parent du node at " << currentNode.gridX << "," << currentNode.gridY << " est : " << currentNode.parent_gridX << "," << currentNode.parent_gridY << endl;
        path.push_back(currentNode);
        currentNode = grid.get(currentNode.parent_gridX, currentNode.parent_gridY);
        //cout << "RETRACING PATH -----------> new node = " << currentNode.gridX << "," << currentNode.gridY << endl;
        // currentNode.gridX = currentNode.parent_gridX;
        // currentNode.gridY = currentNode.parent_gridY;
    }
    path.push_back(startNode);
    return path;
}

int Astar::getDistance(Node nodeA, Node nodeB)
{
    //cout << "getDistance between node " << nodeA.gridX << "," << nodeA.gridY << " and " << nodeB.gridX << "," << nodeB.gridY << endl;
    int xCost = std::abs(nodeA.gridX - nodeB.gridX);
    int yCost = std::abs(nodeA.gridY - nodeB.gridY);
    //cout << "getDistance = " << xCost + yCost << endl;
    return xCost + yCost;
}
