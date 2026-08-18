class Solution {
    public int aggressiveCows(int[] arr, int k) {
        // code here
        Arrays.sort(arr);
        int n = arr.length;
        int low = 1;
        int high = arr[n-1]-arr[0];
        int minD = 0;
        while(low<=high){
            int mid = low+(high-low)/2;
            if(canPlace(arr,k,mid)){
                minD = mid;
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        return high;
    }
    public boolean canPlace(int [] arr,int k,int minDist){
        int cows = 1;
        int lastP = 0;
        for(int i=1;i<arr.length;i++){
            if(arr[i]-arr[lastP]>=minDist){
                cows++;
                lastP = i;
            }
        }
        //only when we have ore cows we can minimize the 
        //hen we can go towards == k. gives the min dist
        //that is maximized.

        /*
        
        Inside canPlace: Why cows >= k?If you can place more than $k$ cows (say, 5 cows when k = 3), then placing 3 cows is trivial—just remove 2 of them! The distance between remaining cows will only increase or stay the same.Therefore, placing $\ge k$ cows is a valid placement.
        
        */
        return cows>=k;
    }
}