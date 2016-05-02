#include <stdio.h>
#include <polylib/polylib.h>
int main() {
Matrix *a1;
Polyhedron *D1;
a1 = Matrix_Read();
D1 = Constraints2Polyhedron(a1, 200);
printf("\n");
fflush(stdout);
Polyhedron_Print(stdout,P_VALUE_FMT,D1);
}
