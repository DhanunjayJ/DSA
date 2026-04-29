class Solution {
    public int minPlatform(int arr[], int dep[]) {
        //  code here
        int n = arr.length;
        
        Arrays.sort(arr);
        Arrays.sort(dep);
        
        int platforms = 0;
        
        int i = 0;
        int j = 0;
        
        int maxPlatforms = 0;
        
        while(i<n && j<n){
            if(arr[i]<=dep[j]){
                platforms++;
                i++;
            }else{
                platforms--;
                j++;
            }
            maxPlatforms = Math.max(platforms,maxPlatforms);
        }
        
        return maxPlatforms;
    }
}
