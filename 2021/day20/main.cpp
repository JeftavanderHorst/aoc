#include <algorithm>
#include <bitset>
#include <fstream>
#include <iostream>
#include <string>
#include <unordered_map>

#include "pos.hpp"

struct Grid {
  std::unordered_map<Pos, bool> tiles;
  bool default_value;
  int size;
};

void LoadInput(Grid &grid, std::bitset<512> &rule) {
  std::ifstream file("input.txt");

  if (!file) {
    std::cerr << "Failed to open input.txt" << std::endl;
    exit(1);
  }

  // Parse rule
  std::string line;
  file >> line;
  for (int i = 0; i < line.size(); i++) {
    rule[i] = line[i] == '#';
  }

  // Parse grid
  int y = 0;
  while (getline(file, line)) {
    file >> line;

    for (int x = 0; x < line.size(); x++) {
      grid.tiles[Pos(x, y)] = line[x] == '#';
    }

    y++;
  }

  grid.size = y;
}

int NeighborhoodToIndex(const Grid &grid, int x, int y) {
  int result = 0;

  for (int py = y - 1; py <= y + 1; py++) {
    for (int px = x - 1; px <= x + 1; px++) {
      result = result << 1;

      const bool tile = grid.tiles.contains(Pos(px, py))
                            ? grid.tiles.at(Pos(px, py))
                            : grid.default_value;
      if (tile) {
        result += 1;
      }
    }
  }

  return result;
}

void ApplyRule(Grid &grid, std::bitset<512> &rule) {
  auto result = Grid {.tiles = std::unordered_map<Pos, bool>(),
                      .default_value = rule[(grid.default_value ? 511 : 0)],
                      .size = grid.size + 2};

  for (int y = 0; y <= grid.size + 2; y++) {
    for (int x = 0; x <= grid.size + 2; x++) {
      const auto index = NeighborhoodToIndex(grid, x - 1, y - 1);
      result.tiles[Pos(x, y)] = rule[index];
    }
  }

  grid = result;
}

int main() {
  auto grid = Grid {};
  auto rule = std::bitset<512>();

  LoadInput(grid, rule);

  for (int i = 1; i <= 50; i++) {
    ApplyRule(grid, rule);
  }

  const auto lit = std::count_if(grid.tiles.begin(), grid.tiles.end(), [](const auto &p) { return p.second; });
  std::cout << lit << " pixels lit" << std::endl;
}
