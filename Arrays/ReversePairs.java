class Solution {
    int countPairs = 0;
    
    public int reversePairs(int[] nums) {
        mergeSort(nums,0,nums.length-1);
        return countPairs;
    }

    public void mergeSort(int []nums,int start,int end){
        if(start>=end) return;
        int mid = (end+start)/2;
        mergeSort(nums,start,mid);
        mergeSort(nums,mid+1,end);
        count(nums,start,mid,end);
        merge(nums,start,mid,end);
    }
    public void count(int []nums,int start,int mid,int end){
        int j = mid+1;
        int i = start;
        while(i<=mid && j<=end){
            if((long) 2*nums[j]>nums[i]){
                j++;
            } else{
                i++;
            }
            count += (j-(mid+1));
        }   
    }
    public void merge(int [] nums,int start,int mid,int end){
        int len = end-start+1;
        int [] temp = new int[len];
        int p1 = start;
        int p2 = mid+1;
        int p3 = 0;
        while(p1<=mid && p2<=end){
            if(nums[p1]<=nums[p2]){
                temp[p3++] = nums[p1++];
            }else{
                temp[p3++] = nums[p2++;]
            }
        }
        while(p1<=mid) {temp[p3++] = nums[p1]++;}
        while(p2<=mid) {temp[p3++] = nums[p2]++;}
    }
}

// easier logic to count same as count inversion

class Solution {
    int count;
    public int reversePairs(int[] nums) {
        count = 0;
        mergeSort(nums,0,nums.length-1);
        return count;
    }

    public void mergeSort(int []arr,int left,int right){
        if(left>=right) return;
        int mid = (left+right)/2;
        mergeSort(arr,left,mid);
        mergeSort(arr,mid+1,right);
        countReversePairs(arr,left,mid,right);
        merge(arr,left,mid,right);
    }

    public void countReversePairs(int [] arr,int left,int mid,int right){
        int i = left;
        int j = mid+1;
        while(i<=mid && j<=right){
            if((long)arr[i]<=(long)2*arr[j]){
                i++;
            }else{
                count += (mid-i+1);
                j++;
            }
        }
    }

     public void merge(int [] arr,int left,int mid,int right){
        
        int len = right-left+1;
        int [] temp = new int[len];
        int p1 = left;
        int p2 = mid+1;
        int p3 = 0;
        while(p1<=mid && p2<=right){
            if(arr[p1]<=arr[p2]){
                temp[p3++] = arr[p1++];
            }else{
                // count+=(mid-p1+1);
                temp[p3++] = arr[p2++];
            }
        }
        while(p1<=mid)temp[p3++] = arr[p1++];
        while(p2<=right)temp[p3++] = arr[p2++];
        for(int i=0;i<len;i++){
            arr[left+i] = temp[i];
        }
    }
}