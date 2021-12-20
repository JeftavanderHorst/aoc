#include <fstream>
#include <iostream>
#include <limits>
#include <queue>
#include <string>
#include <unordered_set>
#include <vector>

#include "pos.hpp"

constexpr int TILE_WIDTH = 100;
constexpr int TILE_HEIGHT = 100;
constexpr int REPETITIONS = 5;
constexpr int GRID_WIDTH = TILE_WIDTH * REPETITIONS;
constexpr int GRID_HEIGHT = TILE_HEIGHT * REPETITIONS;

struct Node {
  int cost;
  int dist;
  Pos parent;
};

void PrintGrid(const Node grid[GRID_WIDTH][GRID_WIDTH]) {
  for (int y = 0; y < GRID_HEIGHT; y++) {
    for (int x = 0; x < GRID_WIDTH; x++) {
      std::cout << grid[y][x].cost;
    }

    std::cout << std::endl;
  }
}

std::vector<Pos> Dijkstra(Node grid[GRID_HEIGHT][GRID_WIDTH]) {
  // Nodes to be evaluated
  auto compare = [&grid](const Pos& a, const Pos& b) { return grid[a.y][a.x].dist > grid[b.y][b.x].dist; };
  std::priority_queue<Pos, std::vector<Pos>, decltype(compare)> open(compare);
  std::unordered_set<Pos> open2;  // because apparently priority_queue doesn't have find() and i'm lazy

  // Nodes already evaluated
  std::unordered_set<Pos> closed;

  open.emplace(0, 0);
  Pos end(GRID_WIDTH - 1, GRID_HEIGHT - 1);

  Pos neighbors[4];

  while (true) {
    Pos current = open.top();
    open.pop();
    closed.insert(current);

    //    std::cout << "Considering " << current << std::endl;

    if (current == end) {
      //      std::cout << "Path has been found" << std::endl;
      break;
    }

    neighbors[0] = Pos(current.x - 1, current.y);
    neighbors[1] = Pos(current.x + 1, current.y);
    neighbors[2] = Pos(current.x, current.y - 1);
    neighbors[3] = Pos(current.x, current.y + 1);

    for (auto& neighbor : neighbors) {
      //      std::cout << " Has neighbor " << neighbor << std::endl;

      if (neighbor.x < 0 || neighbor.x >= GRID_WIDTH || neighbor.y < 0 || neighbor.y >= GRID_HEIGHT) {
        //        std::cout << "  Out of bounds" << std::endl;
        continue;
      }

      if (closed.find(neighbor) != closed.end()) {
        //        std::cout << "  In closed" << std::endl;
        continue;
      }

      if (grid[neighbor.y][neighbor.x].dist <= grid[current.y][current.x].dist + grid[neighbor.y][neighbor.x].cost) {
        //        std::cout << "  Not closer" << std::endl;
        continue;
      }

      //      std::cout << "  Is closer" << std::endl;

      grid[neighbor.y][neighbor.x].dist = grid[current.y][current.x].dist + grid[neighbor.y][neighbor.x].cost;
      grid[neighbor.y][neighbor.x].parent = current;

      if (open2.find(neighbor) == open2.end()) {
        open.push(neighbor);
        open2.insert(neighbor);
      }
    }
  }

  std::vector<Pos> result;

  Pos t = end;
  int cost = 0;
  while (t != Pos(0, 0)) {
    result.push_back(t);
    cost += grid[t.y][t.x].cost;
    t = grid[t.y][t.x].parent;
  }

  std::cout << "Cost: " << cost << std::endl;

  return result;
}

Node grid[GRID_HEIGHT][GRID_WIDTH];

int main() {
  std::ifstream file("input.txt");

  std::string line;
  int y = 0;
  while (getline(file, line)) {
    for (int x = 0; x < TILE_WIDTH; x++) {
      int tile_cost = line[x] - '0';

      for (int rep_y = 0; rep_y < REPETITIONS; rep_y++) {
        for (int rep_x = 0; rep_x < REPETITIONS; rep_x++) {
          int cost = tile_cost + rep_x + rep_y;
          if (cost > 9) {
            cost -= 9;
          }

          grid[rep_y * TILE_HEIGHT + y][rep_x * TILE_WIDTH + x] = {
              cost,
              std::numeric_limits<int>::max(),
              Pos(std::numeric_limits<int>::max(), std::numeric_limits<int>::max()),
          };
        }
      }
    }

    y++;
  }

  std::cout << "Parsed" << std::endl;
  auto path = Dijkstra(grid);
  std::cout << "Dijkstrad" << std::endl;

  for (const Pos& pos : path) {
    //    std::cout << pos.x << 'x' << pos.y << ": " << (grid[pos.y][pos.x]).cost << std::endl;
  }
}
