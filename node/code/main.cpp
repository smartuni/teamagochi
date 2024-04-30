/**
 * @brief Main file for the C++ code.
 * @author Tom <git@annsann.eu>
 */

#include <cstdio>
#include <vector>

#include "architecture.h"
#include "init_display.h"
#include "thread.h"

using namespace std;

/* main */
int main() {
  printf("\n************ We are in C++ 😎 ***********\n");
  printf("\n");

  init_display();

  puts("{\"result\": \"PASS\"}");

  return 0;
}
