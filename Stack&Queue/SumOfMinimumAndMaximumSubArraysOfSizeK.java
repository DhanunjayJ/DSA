import java.util.* ;
import java.io.*; 
import java.util.ArrayList;

public class Solution {
    public static long sumOfMaxAndMin(ArrayList<Integer> nums, int n, int k) {
        // Write your code here.
        Deque<Integer> min = new ArrayDeque<>();
        Deque<Integer> max = new ArrayDeque<>();
        long sum = 0;
        for(int ep=0;ep<n;ep++){
            int sp = ep-k+1;
            while(!min.isEmpty() && min.peekFirst()<sp){
                min.removeFirst();
            }
            while(!max.isEmpty() && max.peekFirst()<sp){
                max.removeFirst();
            }
            int val = nums.get(ep);
            while(!min.isEmpty() && val<nums.get(min.peekLast())){
                min.removeLast();
            }
            while(!max.isEmpty() && val>nums.get(max.peekLast())){
                max.removeLast();
            }
            min.addLast(ep);
            max.addLast(ep);
            if(ep>=k-1){
                sum += (nums.get(min.getFirst())+nums.get(max.getFirst()));
            }
        }
        return sum;
    }
}


//block decomposition prefix min and prefix max

import java.util.* ;
import java.io.*; 
import java.util.ArrayList;

public class Solution {
    public static long sumOfMaxAndMin(ArrayList<Integer> nums, int n, int k) {
        // Write your code here.
        long [] left1 = new long[n]; // maximum value in each value from left to right.
        long [] left2 = new long[n]; // min value from left to right
        long [] right1 = new long[n]; // max value of each value from right to left for each blocke
        long [] right2 = new long[n]; // min value from right to left for each block.

        for(int i=0;i<n;i++){
            if(i%k==0){
                left1[i] = nums.get(i);
                left2[i] = nums.get(i);
            }else{
                left1[i] = Math.max(left1[i-1],nums.get(i));
                left2[i] = Math.min(left2[i-1],nums.get(i));
            }
        }

        for(int j=n-1;j>=0;j--){
            int val = nums.get(j);
            if(j==n-1 || (j+1)%k==0){
                right1[j] = val;
                right2[j] = val;
            }else {
                right1[j] = Math.max(val,right1[j+1]);
                right2[j] = Math.min(val,right2[j+1]);
            }
        }

        long sum = 0;

        for(int i=0;i<=n-k;i++){
            int j = i+k-1;
            sum += Math.max(right1[i],left1[j]);
            sum += Math.min(right2[i],left2[j]);
        }

        return sum;
    }
}