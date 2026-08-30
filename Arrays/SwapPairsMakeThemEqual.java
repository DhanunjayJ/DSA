class Solution {
    boolean findSwapValues(int[] a, int[] b) {
        // code here
        
        /*
        let Ta is the total Sum of a
        and Tb is the total Sum of b
        
        the let x ne the value we need to swap from a , and y from b
        
        so the final conditin is
        
        y+TA-x = TB-y+x;
        
        y-x = (TB-TA)/2;
        
        we need to find y-x that is equal tot he diff/2.
        
        also if diff/2 is the target
        
        we need to find y = (diff/2)+x;
        */
        
        long TA = 0;
        long TB = 0;
        
        for(int num : a){
            TA += num;
        }
        
        for(int num : b){
            TB += num;
        }
        
        long diff = Math.abs(TB-TA);
        
        if(diff%2==1) return false;
        
        long target = diff/2;
        // System.out.println(target);
        
        // we can't find them with two pointers.
        //so we need to try all paris. since the range is etween 1000
        
        // for(int i=0;i<a.length;i++){
        //     for(int j=0;j<b.length;j++){
        //         if(Math.abs(a[i]-b[j])==target){
        //             return true;
        //         }
        //     }
        // }
        
        ///we can optimize this take two pointes on the both but 
        //one from the start and ne from the end.
        
        
        Arrays.sort(a);
        Arrays.sort(b);
        
        int i = 0;
        int j = b.length-1;
        
        while(i<a.length && j>=0){
            int val = Math.abs(a[i]-b[j]);
            // System.out.println(val);
            if(val==target){
                return true;
            }else if(val<target){
                i++;
            }else{
                j--;
            }
        }
        
        return false;
    }
}
