class Solution {
    public int longestStrChain(String[] words) {
        /*

        we could sort the words based on thier length
        because the prefix is always smaller than the next word
        
        ten, we do the lis, on each string comparing each others. and check if both valid.

        if they are then we go do increse teh length.

        o(n2)

        */

        Arrays.sort(words,(a,b) ->Integer.compare(a.length(),b.length()));
        int n = words.length;
        int [] dp = new int[n];
        Arrays.fill(dp,1);
        int max = 1;
        Arrays.fill(dp,1);
        for(int i=1;i<n;i++){
            for(int j=i-1;j>=0;j--){
                if(valid(words[j],words[i]) && dp[j]+1>dp[i]){
                    dp[i] = dp[j]+1;
                    max = Math.max(dp[i],max);
                }
            }
        }
        return max;
    }
    public boolean valid(String prefix,String word){
        //if both lengths don't differ by one then return false then and there.
        if(prefix.length()!=word.length()-1) return false;
        int i = 0;
        int j = 0;
        boolean isFirstMisMatch = false;
        while(i<prefix.length() && j<word.length()){
            if(prefix.charAt(i)!=word.charAt(j)){
                if(!isFirstMisMatch){
                    isFirstMisMatch = true;
                    j++;
                    continue;
                }else{
                    //second mismatch
                    return false;
                }
            }
            i++;
            j++;
        }
        return true;
    }
}