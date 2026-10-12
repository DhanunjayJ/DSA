class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int MOD = 1_000_000_007;
        /*
The Bit Conservation Law
When we replace (x, y) with (x AND y,, x OR y), let's inspect what happens to any single bit position b:If both have bit b as 0: AND \to 0, OR \to 0 (Total 1s: 0 \to 0)
If both have bit b as 1: AND \to 1, OR \to 1 (Total 1s: 2 \to 2)If one has 1 and the other has 0: AND \to 0, OR \to 1 (Total 1s: 1 \to 1)

Two key conclusions arise:Total set bits per position never change. The bit counts across the entire array are strictly conserved.Bits migrate together. Whenever two numbers differ at bit b, the 1 gets shifted into the OR result, leaving a 0 in the AND result. We can repeat this operation arbitrarily many times across any pairs, meaning we can collect set bits into whichever numbers we want.

The Greedy Principle (Maximizing Squares)

Suppose we have a fixed total sum S = A + B. If we shift a value diff > 0 from B to A (assuming A \ge B):(A + diff)^2 + (B - diff)^2 = A^2 + B^2 + 2diff(A - B) + 2diff^2 > A^2 + B^2

Making numbers as skewed/unequal as possible maximizes the sum of their squares.Because we only pick the top k numbers to square, our best strategy is to make the 1st number as massive as possible by dumping every available set bit into it. Once the 1st number has one of every available bit, we make the 2nd number as large as possible with the remaining bits, repeating down to the k-th number.

Because the problem asks to maximize the sum of squares of k numbers:You want the top k numbers to be as large and packed with bits as possible.You achieve that by greedily gathering as many 1-bits into the first number, then the remaining bits into the second, and so on.
        */
        int [] bitCount = new int[31];
        for(int num : nums){
            for(int b=0;b<30;b++){
                if(((num>>b)&1)==1){
                    bitCount[b]++;
                }
            }
        }
        long totalSum = 0;
        for(int i=0;i<k;i++){
            long val = 0;
            for(int b=0;b<30;b++){
                if(bitCount[b]>0){
                    val |= (1L<<b);
                    bitCount[b]--;
                }
            }
            totalSum = (totalSum + (val%MOD)*(val%MOD))%MOD;
        }
        return (int)(totalSum%MOD);
    }
}