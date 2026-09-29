import java.util.*;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map to store: key = number, value = its next greater element
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Traverse nums2 to find the Next Greater Element for each number
        for (int num : nums2) {
            // While the current number is greater than the element at the top of the stack,
            // it means 'num' is the next greater element for the stack's top.
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            // Push the current number to be processed later
            stack.push(num);
        }

        // 2. Build the result for nums1 using the map
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            // If the number exists in the map, use the value; otherwise, -1
            result[i] = map.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}