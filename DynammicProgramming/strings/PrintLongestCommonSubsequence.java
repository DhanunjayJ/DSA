public class PrintLongestCommonSubsequence {
    public static void main(String[] args) {
        System.out.println(longestCommonString("abcab", "cbab")); // Output: "bab" or "cab"
        System.out.println(longestCommonString("apple", "waffle")); // Output: "ale"
    }

    public static String longestCommonString(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();

        int[][] dp = new int[n + 1][m + 1];

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Backtrack to build the output string
        StringBuilder sb = new StringBuilder();
        int i = n, j = m;

        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                sb.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // Move UP
            } else {
                j--; // Move LEFT
            }
        }

        return sb.reverse().toString();
    }
}