class Solution {
    public int getDigitSum(int n){
        int sum = 0;
        while(n>0){
            int digit = n%10;
            sum += digit;
            n/=10;
        }
        return sum;
    }
    public int findMax(int n) {
        // code here

        if(n<10) return n;
        
        int maxSum = getDigitSum(n);
        int ans = n;
        
        int curr = n;
        int b = 1;
        
        while(curr>0){
            //we construct the largest possible value from the current value.
            //to construct it we make the last value - 1 as we are making the last digit to 9.
            //and we substract one and mutiply by the base 10 multiple to get exact same number
            // but with the last digit ree to zero and then we add all 9 values. 
            // 1234 is the curr we remove 4 add 122 -> 1220 + 9 -> 1229. 
            int nextPMax = (curr-1) * b + (b-1);
            
            int sum = getDigitSum(nextPMax);
            if(sum>maxSum){
                maxSum = sum;
                ans = nextPMax;
            }

            //we reduce the curr ele by one digit
            curr /=10;
            //multiply the b with 10 to construct the number easily and also get 9's need to get the next
            //possible value. 
            b*=10;
            
        }
        return ans;
        
    }
}

/*
Let's dry run the logic with n = 123456 to see how b and curr systematically peel back the digits and test powerful candidates packed with 9s.

Initial Setup
n = 123456
ans = 123456
maxSum = 1 + 2 + 3 + 4 + 5 + 6 = \mathbf{21}
curr = 123456
b = 1
Step-by-Step Iterations
Iteration 1:
curr = 123456, b = 1
Candidate: (123456 - 1) * 1 + (1 - 1) = 123455
Digit Sum: 1+2+3+4+5+5 = 20
Compare: Is 20 > 21? No. ans remains 123456.
Update for next loop: curr = 12345, b = 10
Iteration 2:
curr = 12345, b = 10
Candidate: (12345 - 1) * 10 + (10 - 1) = 12344 * 10 + 9 = 123449
Digit Sum: 1+2+3+4+4+9 = 23
Compare: Is 23 > 21? Yes!
Update: maxSum = 23, ans = 123449
Update for next loop: curr = 1234, b = 100
Iteration 3:
curr = 1234, b = 100
Candidate: (1234 - 1) * 100 + (100 - 1) = 1233 * 100 + 99 = 123399
Digit Sum: 1+2+3+3+9+9 = 27
Compare: Is 27 > 23? Yes!
Update: maxSum = 27, ans = 123399
Update for next loop: curr = 123, b = 1000
Iteration 4:
curr = 123, b = 1000
Candidate: (122) * 1000 + 999 = 122999
Digit Sum: 1+2+2+9+9+9 = 30
Compare: Is 30 > 27? Yes!
Update: maxSum = 30, ans = 122999
Update for next loop: curr = 12, b = 10000
Iteration 5:
curr = 12, b = 10000
Candidate: (11) * 10000 + 9999 = 119999
Digit Sum: 1+1+9+9+9+9 = 38
Compare: Is 38 > 30? Yes!
Update: maxSum = 38, ans = 119999
Update for next loop: curr = 1, b = 100000
Iteration 6:
curr = 1, b = 100000
Candidate: (0) * 100000 + 99999 = 99999
Digit Sum: 9+9+9+9+9 = 45
Compare: Is 45 > 38? Yes!
Update: maxSum = 45, ans = 99999
Update for next loop: curr = 0, b = 1000000
End of Loop
curr is now 0, so the loop stops.
The function returns 99999, which has the maximum possible digit sum (45) for any number between 1 and 123456.
Notice how b grew exponentially (1, 10, 100, 1000, 10000, 100000) to precisely punch a block of 9s into the units, tens, hundreds, thousands, and ten-thousands places, testing each major tier in just a few steps!

*/