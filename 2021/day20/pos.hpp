#ifndef DAY20_POS_HPP_
#define DAY20_POS_HPP_

#include <ostream>

struct Pos {
  int x;
  int y;

  Pos() : x(0), y(0) {}
  Pos(int x, int y) : x(x), y(y) {}

  friend std::ostream &operator<<(std::ostream &os, const Pos &pos) {
    os << pos.x << 'x' << pos.y;
    return os;
  }

  bool operator==(const Pos &rhs) const {
    return x == rhs.x && y == rhs.y;
  }

  bool operator!=(const Pos &rhs) const {
    return !(rhs == *this);
  }
};

template <>
struct std::hash<Pos> {
  size_t operator()(const Pos &pos) const {
    return std::hash<int>()(pos.x) ^ std::hash<int>()(pos.y);
  }
};

#endif  // DAY20_POS_HPP_
