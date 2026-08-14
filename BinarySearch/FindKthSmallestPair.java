class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        /*

        Here we are supposed to find the kth smallest distance 
        out of all the paris of distances. 
        instead of searching for i we search the distance itself.
        as the value as kth one and check how many distances are <= mid
        distance we got from the binary search. 
        we coun tth edistance pairs using the two pointers. how ? 

        since the values in the sorted array are in creasing order.
        then keeping two pointers on the the at the starting of i
        and j we do j++ untill the the distance between the two points is <= pair distance. 

        if greater then twe reduce the length by increaisn he i++;
        */

        Arrays.sort(nums);
        int n = nums.length;
        
        int low = 0;
        //max pair distance is the differeance.
        int high = nums[n-1]-nums[0];

        int ans = 0;
        while(low<=high){
            int mid = low+(high-low)/2;
            //if the count of paris that has differance >= k
            // then make it lesser as it comes towards the smallest side/
            if(countPairDist(nums,mid)>=k){
                ans = mid;
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return ans;
    }

    public int countPairDist(int [] nums,int maxDist){
        int count = 0;
        int left = 0;
        int right = 1;
        for(;right<nums.length;right++){
            while(nums[right]-nums[left]>maxDist){
                left++;
            }
            count += (right-left);
        }
        return count;
    }
}