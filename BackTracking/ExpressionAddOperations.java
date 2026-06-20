
class Solution {
    public List<String> addOperators(String num, int target) {
        List<String> ans = new ArrayList<>();
        if (num == null || num.length() == 0) return ans;
        // Use long for 'sum' and 'prev' to avoid integer overflow
        helper(ans, num, target, 0, 0, 0, "");
        return ans;
    }


    public void helper(List<String> ans, String num, int target, int index, long currentSum, long lastValue, String path) {
        // Base Case: Reached the end of the string
        if (index == num.length()) {
            if (currentSum == target) {
                ans.add(path);
            }
            return;
        }


        for (int i = index; i < num.length(); i++) {
            // Handle Leading Zeros: "05" is not allowed, but "0" is.
            if (i != index && num.charAt(index) == '0') break;


            // Extract the number (can be multiple digits)
            long val = Long.parseLong(num.substring(index, i + 1));


            // If we are at the very start, just pick the number
            if (index == 0) {
                helper(ans, num, target, i + 1, val, val, "" + val);
            } else {
                // Try Addition
                helper(ans, num, target, i + 1, currentSum + val, val, path + "+" + val);


                // Try Subtraction
                helper(ans, num, target, i + 1, currentSum - val, -val, path + "-" + val);


                // Try Multiplication (The tricky part!)
                // currentSum - lastValue + (lastValue * val)
                helper(ans, num, target, i + 1, currentSum - lastValue + (lastValue * val), lastValue * val, path + "*" + val);
            }
        }
    }
}


//time complextiy is N * 4^N

