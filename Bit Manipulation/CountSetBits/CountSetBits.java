class CountSetBits {
    
    public static void main (String args[]){
        System.out.println(countSetBits(17));
    }
    
    // Function to find the largest power of 2 less than or equal to n
    public static int largestPowerOf2(int n) {
        int x = 0;
        while ((1 << x) <= n) {
            x++;
        }
        return x - 1;
    }

    public static int countSetBits(int n) {
        if (n <= 0) return 0;

        int x = largestPowerOf2(n);
        
        // 1. Bits in the full blocks of 2^x
        int bitsInFullBlocks = x * (1 << (x - 1));
        
        // 2. The extra MSB bits from 2^x to n
        int msbBits = n - (1 << x) + 1;
        
        // 3. Recursive call for the remaining bits
        int rest = n - (1 << x);
        
        return bitsInFullBlocks + msbBits + countSetBits(rest);
    }
}