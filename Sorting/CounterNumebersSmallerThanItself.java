class Pair {
    int index;
    int value;
    Pair(int index,int value){
        this.index = index;
        this.value = value;
    }
}
class Solution {
    int [] count;
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Pair [] arr = new Pair[n];
        for(int i=0;i<n;i++){
            arr[i] = new Pair(i,nums[i]);
        }
        count = new int[n];
        mergeSort(arr,0,n-1);
        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++){ans.add(count[i]);}
        return ans;
    }
    public void mergeSort(Pair[] arr,int left,int right){
        if(left>=right)return;
        int mid = (left+right)/2;
        mergeSort(arr,left,mid);
        mergeSort(arr,mid+1,right);
        merge(arr,left,mid,right);
    }
    public void merge(Pair [] arr,int left,int mid,int right){
        int len = right-left+1;
        Pair [] temp = new Pair[len];
        int p1 = left;
        int p2 = mid+1;
        int p3 = 0;
        while(p1<=mid && p2<=right){
            if(arr[p1].value>arr[p2].value){
                count[arr[p1].index] += right-p2+1;
                temp[p3++] = arr[p1++];
            }else{
                temp[p3++] = arr[p2++];
            }
        }
        while(p1<=mid){temp[p3++] = arr[p1++];}
        while(p2<=right){temp[p3++] = arr[p2++];}
        for(int i=0;i<len;i++){
            arr[left+i] = temp[i];
        }
    }

}