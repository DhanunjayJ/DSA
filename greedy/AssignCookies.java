package greedy;

import java.util.Arrays;

public class AssignCookies {
    class Solution {
    public int findContentChildren(int[] g, int[] s) {
        /*
        So first we need to maximize the childern that are content. 
        with the given cookies if we can't make the childersn who are having he less greedy
        factor then we can't satisfy the childern who are having more greedy factor. 

        so based on this intuation. we can sort the grredy child array. and also we sort the 
        cookies array.

        then we check for each childern if we can satisfy this chiildern with the current cookie
        which is j if we can we do incemet the both i and j.

        if not then check if the next cookie can satisify him? so do j++.
        do this untill we reach the ead of the cookies or the child.
        then we just return the i. which is the length of the child content. 

        we only do i++ when we can make the childern content!!!
        */
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        int j = 0;
        while(i<g.length && j<s.length){
            if(s[j]>=g[i]){
                i++;
                j++;
            }else{
                j++;
            }
        }
        return i;
    }
}
}
