#!/bin/bash
echo "MiniCloog using FMLib and PolyLib"  
# read no of variables
echo "Please enter number of variables in the constraint matrix:"
read x
echo $x

rm -f "loopnest.out"

for (( i = 1; i < $x+1; i++ ))
do
#gcc -c -g -O2 -I Polylib/include -DLINEAR_VALUE_IS_INT prog1.c -o prog1.o;
#gcc prog1.o ../polylib-5.22.5/.libs/libpolylib64.a -o prog1;
../fm-0.5.0/src/fm-solver input_fmlib.in output_fmlib.out
#javac fmlib.java mymain.java loop.java
java mymain $x $i
#if [$i != x]; then
	./prog1 < output_polylib.in > input_fmlib.in
#fi
done
echo "MINI CLOOG's OUTPUT:"
cat loopnest.out
