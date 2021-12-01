#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <cassert>
#include <sstream>

bool IsNumeric(char c) {
  return c >= '0' && c <= '9';
}

void Split(const std::string &string, std::vector<std::string> *parts, char delimiter = ' ') {
  std::stringstream stream(string);
  std::string segment;
  while (getline(stream, segment, delimiter)) {
    parts->emplace_back(segment);
  }
}

class Policy {
 public:
  Policy(char character, int min, int max) : character_(character), a_(min), b_(max) {
    assert(min <= max);
  }

  static Policy Parse(const std::string &string) {
    int i = 0;

    int min = 0;
    while (IsNumeric(string[i])) {
      min = min * 10 + (string[i] - '0');
      i++;
    }

    // Skip separator
    i++;

    int max = 0;
    while (IsNumeric(string[i])) {
      max = max * 10 + (string[i] - '0');
      i++;
    }

    // Skip space
    i++;

    char character = string[i];

    return {character, min, max};
  }

  [[nodiscard]] bool IsValid(const std::string &password) const {
    return (password[a_ - 1] == character_ && password[b_ - 1] != character_)
        || (password[a_ - 1] != character_ && password[b_ - 1] == character_);
  }

 private:
  friend std::ostream &operator<<(std::ostream &os, const Policy &policy) {
    os << "Char: " << policy.character_ << ", a: " << policy.a_ << ", b: " << policy.b_;
    return os;
  }

  char character_ = '\0';
  int a_ = 0;
  int b_ = 0;
};

int main() {
  std::ifstream file("input.txt");
  assert(file && "Could not open input file");

  int valid = 0;

  std::string line;
  while (getline(file, line)) {
    std::vector<std::string> parts;
    Split(line, &parts, ':');

    auto policy = Policy::Parse(parts[0]);

    auto password = parts[1];
    password.erase(0, 1);

    std::cout << policy << "\t" << password << "\t" << std::boolalpha << policy.IsValid(password) << std::endl;

    if (policy.IsValid(password)) {
      valid++;
    }
  }

  std::cout << valid << " valid passwords" << std::endl;

  return 0;
}
