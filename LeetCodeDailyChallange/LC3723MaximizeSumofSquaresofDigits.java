class Solution {
    public String maxSumOfSquares(int num, int sum) {
        //if the sum > 9*num meaning that if the 
        //we place all 99 in num times we can't ge tthe sum then it
        //is not possible
        if(sum>num*9 || sum<0) return "";
        int count9 = sum/9;
        int rem = sum%9;
        int zeros = num-count9-(rem>0 ? 1 : 0);
        StringBuilder st = new StringBuilder(num);
        for(int i=0;i<count9;i++) st.append("9");
        if(rem>0) st.append(rem);
        for(int i=0;i<zeros;i++) st.append("0");
        return st.toString();
    }
}