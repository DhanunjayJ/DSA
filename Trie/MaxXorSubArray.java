class Solution {
    
    class Node {
        Node one = null;
        Node zero = null;
    }
    
    public Node root;
    
    public void insert(int num){
        
        Node curr = root;
        
        for(int i=31;i>=0;i--){
            int bit = (num&(1<<i))!=0 ? 1 : 0;
            if(bit==1){
                if(curr.one==null){
                    curr.one = new Node();
                }
                curr = curr.one;
            }else{
                if(curr.zero==null){
                    curr.zero = new Node();
                }
                curr = curr.zero;
            }
        }
        
    }
    
    public int maxSubarrayXOR(int[] arr) {
        // code here
        root = new Node();
        
        int pxor = 0;
        
        int maxXor = 0;
        
        insert(0);
        
        for(int i=0;i<arr.length;i++){
            
            pxor ^= arr[i];
            
            //find maxXor
            int num = 0;
             Node curr = root;
            for(int j=31;j>=0;j--){
               
                int bit = (pxor&(1<<j))!=0 ? 1 : 0;
                int wantBit = 1-bit;
                if(wantBit==0){
                    if(curr.zero!=null){
                        curr = curr.zero;
                    }else{
                        num |= (1<<j);
                        curr = curr.one;
                    }
                }else{
                    if(curr.one!=null){
                        num |= (1<<j);
                        curr = curr.one;
                    }else{
                        curr = curr.zero;
                    }
                }
            }
            
            maxXor = Math.max(maxXor,pxor^num);
            
            insert(pxor);
            
        }
        return maxXor;
    }
}