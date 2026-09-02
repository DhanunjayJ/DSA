public class LC3875ConstructUniformParityArrayI {
    class Solution {
    public boolean uniformArray(int[] nums1) {
        /*
        Here There are four choices,
        odd - even = odd;
        even - odd = odd;
        odd - odd = even;
        even - even = even;

        the observation from the above is that to make all even numbers we need to have all odd or all even, 
        but for odd numbers, we can make it anyway. so odd number can be formed anyway. so we can return just true.
        */
        return true;
    }
}
}
