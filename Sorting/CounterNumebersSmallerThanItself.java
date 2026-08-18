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

//Asceding order

class Pair{
    int val;
    int idx;
    Pair(int val,int idx){
        this.val = val;
        this.idx = idx;
    }
}

class Solution {
    int [] count;
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Pair [] arr = new Pair[n];
        count = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = new Pair(nums[i],i);
        }
        mergeSort(arr,0,n-1);
        List<Integer> ans = new ArrayList<>();
        for(int c:count)ans.add(c);
        return ans;
    }
    public void mergeSort(Pair[] arr,int l,int r){
        if(l>=r) return;
        int m = (l+r)/2;
        mergeSort(arr,l,m);
        mergeSort(arr,m+1,r);
        merge(arr,l,m,r);
    }
    public void merge(Pair[] arr,int l,int m,int r){
        int len = r-l+1;
        Pair [] temp = new Pair[len];
        int p1 = l;
        int p2 = m+1;
        int p3 = 0;
        int rightCount = 0;
        while(p1<=m && p2<=r){
            if(arr[p2].val<arr[p1].val){
                temp[p3++] = arr[p2++];
                //accumate when the right element is less than the current element
                rightCount++;
            }else{
                count[arr[p1].idx] += rightCount;
                temp[p3++] = arr[p1++];
            }
        }
        //// Remaining elements in the left half are also greater than all `rightCount` elements
        while(p1<=m){
            count[arr[p1].idx] += rightCount;
            temp[p3++]=arr[p1++];
        }
        while(p2<=r)temp[p3++]=arr[p2++];

        for(int i=0;i<len;i++){
            arr[i+l] = temp[i];
        }
    }
}