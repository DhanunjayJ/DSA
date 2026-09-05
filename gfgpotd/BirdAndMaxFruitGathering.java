//https://www.geeksforgeeks.org/problems/bird-and-maximum-fruit-gathering--170645/1

class Solution {
    public int maxFruits(ArrayList<Integer> arr, int m) {
        // code here
        int n = arr.size();
        int sp = 0;
        int maxSum = 0;
        int currSum = 0;
        for(int ep=0;ep<n+m-1;ep++){
            currSum += arr.get(ep%n);
            if(ep-sp+1>m){
                currSum -= arr.get(sp%n);
                sp++;
            }
            if(ep-sp+1==m){
                maxSum = Math.max(maxSum,currSum);
            }
        }
        return maxSum;
    }
}