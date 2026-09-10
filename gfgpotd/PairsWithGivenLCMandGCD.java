class Solution {
    public int pairCount(int x, int y) {
        // code here
        /*
        
        given two numer x = gcd and y = lcm.
        
        we are looking for two unknown numbers a and b.
        
        Since x is the Greatest Common Divisor of a and b, 
        numbers must be multiples of x.
        
        Therefore, we can write a = x*A, b = x*B.
        
        A and B are some unknown intergers.
        
        Because x is the greatest common divisor, 
        A and B cannot share any common factors other than 1.
        
        If they shared a factor, 
        x wouldn't have been the greatest divisor.
        
        GCD(a,b) * LCM(a,b) = a*b
        
        (substitute a=x*A, b=x*B)
        
        x*y = x*A*x*B
        y = x*A*B
        y/x = A*B
        
        let k = y/x
        
        Finding the original numbers a and b is the exact same thing as 
        finding two factors A and B of k such that they are 
        coprime (GCD(A, B) = 1
        
       Once you find a valid pair (A, B):
       
       Your two numbers are simply a = x*A and b = x*B.
       Because order matters ((a, b) is different from (b, a)), 
       every valid pair (A, B) gives you two valid answers: (x*A, x*B)
       and (x*B, x*A), unless A = B.
       
    */
        
        if(y%x!=0) return 0;
        
        int k = y/x;
        int count = 0;
        
        for(int i=1;i*i<=k;i++){
            
            if(k%i==0){
               
                int A = i;
                int B = k/i;
                
                if(gcd(A,B)==1){
                    if(A==B){
                        count++;
                    }else{
                        count+=2;
                    }
                }
                
            }
        }
        
        return count;
    }
    
    public int gcd(int a,int b){
        return a==0 ? b : gcd(b%a,a);
    }
}