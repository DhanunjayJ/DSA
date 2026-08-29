class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>();
        if (s.length() < 4 || s.length() > 12) {
            return ans; // Quick pruning
        }
        dfs(s, 0, new ArrayList<>(), ans);
        return ans;
    }

    private void dfs(String s, int start, List<String> path, List<String> ans) {
        // If we have 4 segments and we've used up the whole string, it's a valid IP!
        if (path.size() == 4 && start == s.length()) {
            ans.add(String.join(".", path));
            return;
        }
        
        // If we hit 4 segments too early, or ran out of string, stop.
        if (path.size() == 4 || start == s.length()) {
            return;
        }

        // Try 1, 2, or 3 digit lengths for the current segment
        for (int length = 1; length <= 3; length++) {
            if (start + length > s.length()) {
                break; // Out of bounds
            }
            
            String segment = s.substring(start, start + length);
            
            // Check rules: 
            // 1. No leading zeros (e.g., "01" is invalid, but "0" is valid)
            // 2. Value must be <= 255
            if ((segment.length() > 1 && segment.charAt(0) == '0') || Integer.parseInt(segment) > 255) {
                continue;
            }

            path.add(segment);
            dfs(s, start + length, path, ans);
            path.remove(path.size() - 1); // Backtrack
        }
    }
}