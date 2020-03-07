#include "Grid.hpp"
#include <iostream>

using namespace std;

Grid::Grid()
{
  lignNumber = 5;
  columnNumber = 7;

  int matrix[lignNumber][columnNumber] = {
    { 0, 0, 0, 1, 0, 0, 0 },
    { 1, 0, 1, 0, 0, 1, 0 },
    { 0, 0, 1, 0, 1, 1, 0 },
    { 1, 0, 1, 0, 0, 0, 0 },
    { 0, 0, 0, 0, 1, 1, 1}};


  for (int i=0; i < lignNumber; i++) {
    for (int j=0; j < columnNumber; j++) {
      if(matrix[i][j] == 0)
        grid.push_back(Node(i,j,true));
      else
        grid.push_back(Node(i,j,false));
    }
  }
}

/*
vector<Node> Grid::getNeighbours(Node node)
{
  vector<Node> neighbours;

  for (int x=-1; x<1; x++)
  {
    for (int y=-1; y<1; y++)
    {
      if(x == 0 && y == 0)
        continue;

      int checkX = node.gridX +x;
      int checkY = node.gridY +y;

      if(checkX >=0 && checkX < columnNumber && checkY >=0 && checkY < lignNumber)
        neighbours.push_back(get(checkX, checkY));
    }
  }

  return neighbours;
} */

vector<Node> Grid::getNeighbours(Node node)
{
  //cout << endl << "------------------------" << endl;
  //cout << "checking neighbours of node at " << node.gridX << "," << node.gridY << endl;
  vector<Node> neighbours;

  for (int x=-1; x<=1; x++)
  {
    for (int y=-1; y<=1; y++)
    {
      //cout << "x = " << x << " y = " << y << endl;
      if((x == 0 && y == 0) || (x == -1 && y == -1) || (x == -1 && y == 1) || (x == 1 && y == -1) || (x == 1 && y == 1) )
      {
        //cout << "neighbour " << x << " : " << y << " ingored" << endl;
        continue;
      }

      int checkX = node.gridX +x;
      int checkY = node.gridY +y;

      if((checkX >=0 && checkX < lignNumber) && (checkY >=0 && checkY < columnNumber))
      {
        //cout << "neigbour " << checkX << " : " << checkY << " added" << endl;
        neighbours.push_back(get(checkX, checkY));
      }
      else
      {
        //cout << "neighbour " << checkX << " : " << checkY << " unaccepted" << endl;
      }
    }
  }

  //cout << endl << neighbours.size() << " neighbours found for Node at " << node.gridX << "," << node.gridY << endl;
  //cout << "------------------------" << endl << endl;

  return neighbours;
}


Node& Grid::get(int i, int j)
{
  return grid[i*columnNumber+j];
}

void Grid::setParent(int i, int j, int i_parent, int j_parent)
{
  grid[i*columnNumber+j].parent_gridX = i_parent;
  grid[i*columnNumber+j].parent_gridY = j_parent;
}

void Grid::displayGrid()
{
  for(int i=0; i< lignNumber; i++)
  {
    for (int j=0; j < columnNumber; j++)
    {
      cout << get(i,j).walkable << " ";
    }
     cout << endl;
  }
}
