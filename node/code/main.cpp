#include "architecture.h"
#include "thread.h"

#include "init_display.h"

#include <cstdio>
#include <vector>

using namespace std;

/* main */
int main()
{
    printf("\n************ We are in C++ 😎 ***********\n");
    printf("\n");

    init_display();

    puts("{\"result\": \"PASS\"}");

    return 0;
}
