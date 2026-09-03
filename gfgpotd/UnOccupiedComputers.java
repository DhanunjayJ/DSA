package gfgpotd;

public class UnOccupiedComputers {
    // https://www.geeksforgeeks.org/problems/unoccupied-computers-1646661078/1
    class Solution {
    public int solve(int n, String s) {
        // code here
        int [] entryExit = new int[26];
        int [] allocated = new int[26];
        int count = 0;
        for(int i=0;i<s.length();i++){
            int idx = s.charAt(i)-'A';
            if(entryExit[idx]==0){
                entryExit[idx]++;
                if(n>0){
                    allocated[idx]++;
                     n--;
                }else{
                    count++;
                }
            }else if(entryExit[idx]==1){
                entryExit[idx]--;
                if(allocated[idx]==1) {n++;}
            }
        }
        return count;
    }
}
}
