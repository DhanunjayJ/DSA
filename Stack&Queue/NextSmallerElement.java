class Solution {
    static ArrayList<Integer> nextSmallerEle(int[] arr) {
        // code here
        int n = arr.length;
        int [] ans = new int[n];
        Arrays.fill(ans,-1);
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i=0;i<n;i++){
            while(!stack.isEmpty() && arr[stack.peek()]>arr[i]){
                ans[stack.pop()] = arr[i];
            }
            stack.push(i);
        }
        ArrayList<Integer> temp = new ArrayList<>();
        for(int num:ans){
            temp.add(num);
        }
       return temp;
    }
}