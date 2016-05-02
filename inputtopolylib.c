#include <stdio.h>
#include <polylib/polylib.h>
int main() {
Matrix *a, *b, *t;
Polyhedron *A, *B, *C, *D;
printf("Polyhedral Library Test\n\n");
a = Matrix_Read(); /* 1 */
b = Matrix_Read(); /* 2 */
A = Constraints2Polyhedron(a, 200);
B = Constraints2Polyhedron(b, 200);
/* 3 */
/* 4 */
Matrix_Free(a); /* 5 */
Matrix_Free(b); /* 6 */
a = Polyhedron2Constraints(A);
b = Polyhedron2Constraints(B);
/* 7 */
/* 8 */
printf("\na =");
Matrix_Print(stdout,P_VALUE_FMT,a);
printf("\nb =");
Matrix_Print(stdout,P_VALUE_FMT,b);
Matrix_Free(a); /* 11 */
Matrix_Free(b); /* 12 */
/* 9 */
/* 10 */
C = DomainIntersection(A, B, 200); /* 13 */
printf("\nC = A and B =");
Polyhedron_Print(stdout,P_VALUE_FMT,C); /* 14 */
t = Matrix_Read(); /* 15 */
D = Polyhedron_Preimage(C, t, 200); /* 16 */
Matrix_Free(t); /* 17 */
printf("\nD = transformed C =");
/* 18 */
Polyhedron_Print(stdout,P_VALUE_FMT,D); /* 19 */
Domain_Free(D); /* 20 */
if (PolyhedronIncludes(A,C))
/* 21 */
printf("\nWe expected A to cover C since C = A intersect B\n");
if (!PolyhedronIncludes(C,B)) /* 22 */
printf("and C does not cover B...\n");
Domain_Free(A);
Domain_Free(B);
Domain_Free(C);
return 0;
}
