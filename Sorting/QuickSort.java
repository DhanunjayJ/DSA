class Solution {
    public int[] sortArray(int[] nums) {
        quickSort(nums,0,nums.length-1);
        return nums;
    }
    public void quickSort(int [] nums,int l,int r){
        if(l>=r) return;
        int p = partition(nums,l,r);
        quickSort(nums,l,p-1);
        quickSort(nums,p+1,r);
    }
    public int partition(int [] nums,int l,int r){
        // -- Math.random return 0 to 1 values double multiple with length
        // gives values on one of the index when converted to int. 
        int randomIdx = l + (int) (Math.random() * (r-l+1));
        int t = nums[randomIdx];
        nums[randomIdx] = nums[l];
        nums[l] = t;
        // --- randomizing the pivot ---

        int pivot = nums[l];
        int p1 = l+1;
        int p2 = r;
        while(p1<=p2){
            if(nums[p1]<=pivot){
                p1++;
            }else if(nums[p2]>pivot){
                p2--;
            }else{
                int temp = nums[p2];
                nums[p2] = nums[p1];
                nums[p1] = temp;
                p1++;
                p2--;
            }
        }
        int temp = nums[p2];
        nums[p2] = nums[l];
        nums[l] = temp;
        return p2;
    }
}

// 3 way paritiontioning

class Solution {
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void quickSort(int[] nums, int l, int r) {
        if (l >= r) return;

        // Randomize the pivot to avoid O(n^2) on sorted arrays
        int random = l + (int) (Math.random() * (r - l + 1));
        swap(nums, random, l);

        int pivot = nums[l];

        // 3-Way Partitioning (Dutch National Flag Pointers):
        // [l ... i-1]   -> elements < pivot
        // [i ... k-1]   -> elements == pivot
        // [k ... j]     -> unexamined elements
        // [j+1 ... r]   -> elements > pivot
        int i = l; 
        int j = r; 
        int k = l + 1; 

        while (k <= j) {
            if (nums[k] < pivot) {
                // If a value is smaller, put it in the '< pivot' zone.
                // We swap with 'i' (which holds either an equal value or k itself).
                swap(nums, i, k);
                i++;
                k++;
            } else if (nums[k] > pivot) {
                // When swapping with 'j', the incoming value from the right 
                // is completely unknown (could be <, ==, or >). 
                // Therefore, we DO NOT increment k yet so we can inspect it next.
                swap(nums, k, j);
                j--;
            } else {
                // If equal to pivot, just expand the middle zone.
                k++;
            }
        }

        // Recursively sort sub-arrays (skipping the middle '==' block)
        quickSort(nums, l, i - 1);
        quickSort(nums, j + 1, r);
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}