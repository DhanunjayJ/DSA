import java.util.ArrayList;
import java.util.Collections;

public class LeadersInArray {
    class Solution {
    static ArrayList<Integer> leaders(int arr[]) {
        // code here
        ArrayList<Integer> ans = new ArrayList<>();
        int n = arr.length;
        int maxTillNow = arr[n-1];
        ans.add(arr[n-1]);
        for(int i=arr.length-2;i>=0;i--){
            if(arr[i]>=maxTillNow){
                ans.add(arr[i]);
                maxTillNow = arr[i];
            }
        }
        Collections.reverse(ans);
        return ans;
    }
}

}
