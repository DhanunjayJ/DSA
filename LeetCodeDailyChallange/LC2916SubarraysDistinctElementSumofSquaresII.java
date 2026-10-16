class Solution {
    long [] sum1;
    long [] sum2;
    long [] lazy;
    int MOD = 1_000_000_007;

    public void apply(int node,int l,int r,long v){
        long len = (r-l+1);
        // updating the sum2 since we need the old sum1 to calcaute the new sum2.
        sum2[node] =  (sum2[node] + 2L*v%MOD*sum1[node]+len*(v*v)%MOD)%MOD; 
        sum1[node] = (sum1[node]+len*v)%MOD;
        lazy[node] = (lazy[node]+v)%MOD;
    }

    public void push(int node,int l,int r){
        //if lazy node is not null then add that to the left and right nodes. 
        if(lazy[node]!=0){
            int mid = l+(r-l)/2;
            apply(node*2,l,mid,lazy[node]);
            apply(node*2+1,mid+1,r,lazy[node]);
            lazy[node] = 0;
        }
    }

    public void update(int node,int l,int r,int ql,int qr,long v){
        if(ql<=l && r<=qr){
            apply(node,l,r,v);
            return;
        }
        //before going into the the childern we check if any
        // values in the parent are need to pushed. 
        //if yes we push it and then go tht child. 
        push(node,l,r);

        int mid = l+(r-l)/2;

        if(ql<=mid){
            update(node*2,l,mid,ql,qr,v);
        }
        //since this is a range update we need to go to the overlapping 
        //part and update that part too. 
        if(qr>mid){
            update(node*2+1,mid+1,r,ql,qr,v);
        }

        sum1[node] = (sum1[node*2]+sum1[node*2+1])%MOD;
        sum2[node] = (sum2[node*2]+sum2[node*2+1])%MOD;
    }

    public int sumCounts(int[] nums) {

        int n = nums.length;

        sum1 = new long[4*n];
        sum2 = new long[4*n];
        lazy = new long[4*n];

        int maxVal = Integer.MIN_VALUE;

        for(int i=0;i<nums.length;i++){
            maxVal = Math.max(maxVal,nums[i]);
        }

        int [] lastSeen = new int[maxVal+1];
        Arrays.fill(lastSeen,-1);
        
        int ans = 0;

        for(int r=0;r<n;r++){
            int x = nums[r];
            int prev = lastSeen[x];
            //update all the values from the lastseen+1 to the r by one.
            update(1,0,n-1,prev+1,r,1);
            lastSeen[x] = r;
            //root holds the sum of all the squares add sum to the ans
            ans = (int)(ans+sum2[1])%MOD;
        }

        return ans;
    }
}