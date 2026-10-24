class Solution {
    public int maxPoints(int[][] points) {
        int maxPointsLine = 0;
        int n = points.length;
        for(int i=0;i<n;i++){
            int duplicates = 0;
            HashMap<String,Integer> phm = new HashMap<>();
            int localMax = 0;
            for(int j=i+1;j<n;j++){
                int dx = points[i][0]-points[j][0];
                int dy = points[i][1] - points[j][1];
                //if duplicate points
                if(dx==0 && dy==0){
                    duplicates++;
                    continue;
                }

                int gcd = gcd(dx,dy);
                dx /= gcd;
                dy /= gcd;

                //normalizing when dx<0 for same slope
                if(dx<0){
                    dx = -dx;
                    dy = -dy;
                }else if(dx==0){
                    dy = Math.abs(dy);
                }else if(dy==0){
                    dx = Math.abs(dx);
                }
                String key = dx+","+dy;
                phm.put(key,phm.getOrDefault(key,0)+1);
                localMax = Math.max(localMax,phm.get(key));
            }
            maxPointsLine = Math.max(localMax+duplicates+1,maxPointsLine);
        }
        return maxPointsLine;
    }
    public int gcd(int x,int y){
        return x==0 ? y : gcd(y%x,x);
    }
}