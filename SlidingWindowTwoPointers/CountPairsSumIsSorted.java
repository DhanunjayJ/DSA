class Solution {
    int countPairs(int arr[], int target) {
        //  Code Here
        int n = arr.length;
        int i = 0;
        int j = n-1;
        int count = 0;
        while(i<j){
            int sum = arr[i]+arr[j];
            if(sum<target){
                i++;
            }else if(sum>target){
                j--;
            }else{
                int c1 = 0;
                int c2 = 0;
                int num1 = arr[i];
                int num2 = arr[j];
                while(i<=j && arr[i]==num1){
                    i++;
                    c1++;
                }
                while(i<=j && arr[j]==num2){
                    j--;
                    c2++;
                }
                if(num1==num2){
                    count += (c1*(c1-1))/2;
                }else{
                    count += (c1*c2);
                }
            }
        }
        return count;
    }
}
