class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        int x1 = rec1[0], y1 = rec1[1], x2 = rec1[2], y2 = rec1[3];
        int x3 = rec2[0], y3 = rec2[1], x4 = rec2[2], y4 = rec2[3];
        //if either of this is true means there is not overlap. 
        boolean xOverlap = !(x1>=x4 || x3>=x2); 
        boolean yOverlap = !(y3>=y2 || y1>=y4);
        //if there is a overlap one both should be true.
        return (xOverlap&&yOverlap);
    }
}