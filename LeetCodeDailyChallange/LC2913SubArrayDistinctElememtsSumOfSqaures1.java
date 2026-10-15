class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int sum = 0;
        for(int i=0;i<n;i++){
            int [] count = new int[101];
            int dist = 0;
            for(int j=i;j<n;j++){
                if(count[nums.get(j)]==0)dist++;
                count[nums.get(j)]++;
                sum += (dist*dist);
            }
        }
        return sum;
    }
}