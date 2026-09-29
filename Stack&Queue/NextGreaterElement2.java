// implemetation 1

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int [] ans = new int[n];
        for(int i=0;i<n;i++){
            while(!stack.isEmpty() && nums[stack.peek()]<nums[i]){
                ans[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        for(int i=0;i<n;i++){
            while(!stack.isEmpty() && nums[i]>nums[stack.peek()]){
                ans[stack.pop()] = nums[i];
            }
        }
        while(!stack.isEmpty()){
            ans[stack.pop()] = -1;
        }
        return ans;
    }
}


// optimized and refactored.

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1); // Initialize with -1 so we don't need a final loop
        Deque<Integer> stack = new ArrayDeque<>();

        // We "fake" a doubled array by looping up to 2 * n
        for (int i = 0; i < 2 * n; i++) {
            int currentNum = nums[i % n]; // Modulo wraps the index back to the start
            
            while (!stack.isEmpty() && nums[stack.peek()] < currentNum) {
                ans[stack.pop()] = currentNum;
            }
            
            // Only push indices during the first pass
            if (i < n) {
                stack.push(i);
            }
        }
        return ans;
    }
}
