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

    private void quickSort(int[] nums, int l, int r) {
        if (l >= r) return;

        // 1. Randomize pivot to protect against sorted inputs
        int randomIdx = l + (int) (Math.random() * (r - l + 1));
        swap(nums, randomIdx, l);

        int pivot = nums[l];

        // 2. Three-way partitioning pointers
        int i = l;      // Tracks boundary for < pivot
        int j = r;      // Tracks boundary for > pivot
        int k = l + 1;  // Scans through the array

        while (k <= j) {
            if (nums[k] < pivot) {
                swap(nums, i, k);
                i++;
                k++;
            } else if (nums[k] > pivot) {
                swap(nums, k, j);
                j--; // Don't increment k yet, we need to examine the swapped element
            } else {
                k++; // Equal to pivot, just move forward
            }
        }

        // 3. Recursively sort elements less than and greater than pivot
        // (Notice we skip sorting the middle section because duplicates are already done!)
        quickSort(nums, l, i - 1);
        quickSort(nums, j + 1, r);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}