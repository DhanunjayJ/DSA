class Solution {
    public int[][] kClosest(int[][] points, int k) {
        quickSelect(points,0,points.length-1,k);
        return Arrays.copyOfRange(points,0,k);
    }
    public void quickSelect(int [][] points,int left,int right,int k){
        if(left>=right) return ;
        
        int pivotIdx = partition(points,left,right);

        if(pivotIdx==k){
            return;
        }else if(pivotIdx>k){
            quickSelect(points,left,pivotIdx-1,k);
        }else{
            quickSelect(points,pivotIdx+1,right,k);
        }
    }
    public int partition (int [][] points,int left,int right){
        int pivot = left + (int) (Math.random()*(right-left+1));
        int pDist = calDist(points[pivot]);
        swap(points,left,pivot);
        int p1 = left+1;
        int p2 = right;
        while(p1<=p2){
            if(calDist(points[p1])<=pDist){
                p1++;
            }else if(calDist(points[p2])>pDist){
                p2--;
            }else{
                swap(points,p1,p2);
                p1++;
                p2--;
            }
        }
        swap(points,p2,left);
        return p2;
    }
    public int calDist(int [] point){
        return point[0]*point[0] + point[1]*point[1];
    }
    public void swap(int [][] points , int i,int j){
        int [] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
}