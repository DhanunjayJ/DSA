public class FloodFill {
    public class Solution {
    /**
     * @param image: a 2-D array
     * @param sr: an integer
     * @param sc: an integer
     * @param newColor: an integer
     * @return: the modified image
     */

    //
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // Write your code here
        if(image[sr][sc]==newColor){
            return image;
        }
        
        dfs(image,sr,sc,newColor,image[sr][sc]);
        return image;
    }
    int [][] nbrs = {{1,0},{-1,0},{0,1},{0,-1}};

    public void dfs(int [][]image,int sr,int sc,int color,int pcolor){
        if(image[sr][sc]!=pcolor)
        return;

        image[sr][sc] = color;
        for(int i=0;i<nbrs.length;i++){
            int nr = nbrs[i][0]+sr;
            int nc = nbrs[i][1]+sc;
            if(nr>=0 && nc>=0 && nr<image.length && nc<image[0].length){
                dfs(image,nr,nc,color,pcolor);
            }
        }

    }
}
}
