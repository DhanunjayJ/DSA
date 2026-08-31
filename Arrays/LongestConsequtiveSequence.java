class Solution {
    public int longestConsecutive(int[] nums) {
        /*
        since the values are from 10-9 to 10 +9 we need to store them in hashmap.

        to know which number is present or not we need to check num-1 and num+1.

        and there could be duplcates to, 

         so to handle duplicataes we only allow distinct vlaleus so that one they are merged. they
        won't ne comming agian.

        so, we try to maintain the length of the sequcne in the start and end points.
        of the consequtive sequence. 

        and then when ever a sp and ep are there we just increment the length and store the lengths in
        the both sp and ep. 

        if both sp and ep both are present then we could just update the length and also we need to
        add that element to the hasmap so that we don't have to handle same value sagain. 
        */
        HashMap<Integer,Integer> hm = new HashMap<>();
        int maxLen = 0;
        for(int num : nums){
            if(!hm.containsKey(num)){
                int sp = num;
                int ep = num;
                if(hm.containsKey(sp-1)) sp = sp-hm.get(sp-1);
                if(hm.containsKey(ep+1)) ep = ep+hm.get(ep+1);
                int len = ep-sp+1;
                if(len>maxLen) maxLen = len;
                hm.put(sp,len);
                hm.put(ep,len);
                if(sp!=num && ep!=num){
                    hm.put(num,1);
                }
            }
        }
        return maxLen;
    }
}


// Using DSU 

class Solution {
    //since the vlaues are too larget to represent int a arrray we use the hasmap.
    HashMap<Integer,Integer> parent = new HashMap<>();
    HashMap<Integer,Integer> size = new HashMap<>();

    int maxSize = 0;

    public void add(int num){
        if(!parent.containsKey(num)){
            parent.put(num,num);
            size.put(num,1);
            maxSize = Math.max(maxSize,1);
        }
    }
    
    public int find(int x){
        if(parent.get(x)==x) return x;
        //recursively find the parent.
        int root = find(parent.get(x));
        parent.put(x,root);
        return root;
    }

    public void union(int x,int y){
        int px = find(x);
        int py = find(y);
        if(px!=py){
            parent.put(px,py);
            int newSize = size.get(px)+size.get(py);
            size.put(py,newSize);
            maxSize = Math.max(newSize,maxSize);
        }
    }


    public int longestConsecutive(int[] nums) {
        if(nums.length==0) return 0;
        //intialize the dsu. with all values.
        for(int num : nums) add(num);
        for(int num : nums){
            //checking num-1 is optinal which will be handled symetrically when processing num-1.
            if(parent.containsKey(num+1)){
                union(num,num+1);
            }
        }
        return maxSize;
    }
}