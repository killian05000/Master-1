#include <vector>
#include <iostream>
#include "Node.hpp"
#include "Grid.hpp"
#include "Astar.hpp"

using namespace std;

int main()
{
  Grid grille;

  //cout << "La grille fait " << grille.lignNumber << " lignes et " << grille.columnNumber << " colonnes" << endl;

  grille.displayGrid();

  Astar algo(grille);
  int startPos[2] = {0, 0};
  int targetPos[2] = {1, 6};

  if(grille.get(startPos[0], startPos[1]).walkable == true && grille.get(targetPos[0], targetPos[1]).walkable == true)
    cout << "Start node and target node walkable, proceed." << endl;
  else
  {
    cout << "Start node and/or target node not walkable, error" << endl;
    exit(1);
  }


  vector<Node> path = algo.findPath(startPos, targetPos);
  cout << "Pour hunter etant en [" << startPos[0] << " : " << startPos[1] << "] et target en [" << targetPos[0] << " : " << targetPos[1] << "] chemin de taille " << path.size() << " trouve : " <<  endl;
  for (int i=0; i<path.size(); i++)
  {
    cout << "(" << path.at(i).gridX << " : " << path.at(i).gridY << ") - ";
  }
  cout << endl;
}
