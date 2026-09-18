class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        //since the substring should contain all the values.
        //we store the first and last indexes of the alphabet occuring.

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, -1);
        Arrays.fill(last, -1);

        int n = s.length();

        //fill the occurances
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) {
                first[c] = i;
            }
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        //Adding valid Intervals. 
        for (int i = 0; i < 26; i++) {

            if (first[i] != -1) {

                int start = first[i];
                int end = last[i];

                boolean isValid = true;

                //if the alphabets occuring inside the 
                //valid range of an alphabet is having 
                //start value that is less than the current start
                // then it is not valid substring. 
                //if it occurs after this start then we update the end.
                for (int j = start; j <= end; j++) {

                    int c = s.charAt(j) - 'a';

                    if (first[c] < start) {
                        isValid = false;
                        break;
                    }
                    //update the end till the max of all occurances
                    end = Math.max(last[c], end);
                }
                //if the stirng is valid we add it to the array
                if (isValid) {
                    intervals.add(new int[] { start, end });
                }
            }
        }

        //we sort the intervals by end inteval because 
        //the faster the interval end
        // the more substrings we can include. 
        //and also this guantess to have the min length. 
        //final array while picking the intervals non overlapping.

        Collections.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int prevEnd = -1;

        List<String> ans = new ArrayList<>();
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            if (start > prevEnd) {
                ans.add(s.substring(start, end + 1));
                prevEnd = end;
            }
        }
        return ans;
    }
}