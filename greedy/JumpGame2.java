public class JumpGame2 {
    /*
    Since we don't care about the acutal path we just walk throught the current range 
    and update the furthest point when we reach the current end then we update dthe curent
    end tot he furthest point.!!
    */
    class Solution {
    public int jump(int[] nums) {
        int jumps = 0;
        int furthest = 0;
        int currentEnd = 0;
        for(int i=0;i<nums.length-1;i++){
            furthest = Math.max(i+nums[i],furthest);
            if(i==currentEnd){
                jumps++;
                currentEnd = furthest;
            }
        }
        return jumps;
    }
}
}
