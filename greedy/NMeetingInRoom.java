import java.util.Arrays;

public class NMeetingInRoom {
    
class Solution {
    // Function to find the maximum number of meetings that can
    // be performed in a meeting room.
    public int maxMeetings(int start[], int end[]) {
        // add your code here
        /*
        By sorting the values based on the end time we are 
        guranteeing that the maximum amount of time is left for 
        other meetings. so we just sort and schedule the ones
        that can fit in the time.
        
        */
        
        int n = start.length;
        int [] [] meetings = new int[n][2];
        
        for(int i=0;i<meetings.length;i++){
            meetings[i][0] = start[i];
            meetings[i][1] = end[i];
        }
        
        Arrays.sort(meetings,(a,b) -> Integer.compare(a[1],b[1]));
        
        int count = 1;
        int lastMeetingEnd = meetings[0][1];
        
        for(int i=1;i<meetings.length;i++){
            if(meetings[i][0]>lastMeetingEnd){
              count++;
              lastMeetingEnd = meetings[i][1];
            }
        }
        
        return count;
    }
}

}
