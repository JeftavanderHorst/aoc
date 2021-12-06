#include <fstream>
#include <iostream>
#include <sstream>
#include <string>

void PrintFishes(uint16_t day, uint64_t *fishes) {
  std::cout << "After " << day << " days: ";

  uint64_t number_of_fishes = 0;
  for (uint8_t i = 0; i < 10; i++) {
    std::cout << std::to_string(fishes[i]) << ", ";
    number_of_fishes += fishes[i];
  }

  std::cout << " (" << number_of_fishes << " fishes)" << std::endl;
}

int main() {
  std::ifstream ifs("input.txt");
  std::string content((std::istreambuf_iterator<char>(ifs)), (std::istreambuf_iterator<char>()));
  std::stringstream input(content);

  // This pointer is incremented by one each tick, so that it always points to the current day
  // I.e. fishes[0] is always the day where fishes reproduce
  auto fishes = new uint64_t[300] {0};

  std::string substr;
  while (std::getline(input, substr, ',')) {
    fishes[std::stoi(substr)]++;
  }

//  PrintFishes(0, fishes);

  for (uint16_t day = 1; day <= 256 ; day++) {
    fishes[7] += fishes[0];
    fishes[9] += fishes[0];

    // Move pointer
    fishes++;

//    PrintFishes(day, fishes);
  }

  uint64_t number_of_fishes = 0;
  for (uint8_t i = 0; i < 10; i++) {
    number_of_fishes += fishes[i];
  }
  std::cout << "There are " << number_of_fishes << " fishes" << std::endl;

  return 0;
}
