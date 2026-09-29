
class StockSpanner {
    //stroing the values as a array
    // one for storing the val and one for the span
    Deque<int[]> stack;

    public StockSpanner() {    
        stack = new ArrayDeque<>();
    }
    
    public int next(int price) {
        // span is nothing but the number days the value is <= current day price. 
        // so every time when we encounter this senario we pop it and add one to the 
        // the current span of the stackspan and update the top of the elemtn. 
        // the stack contains values in decreaisn ofer (botto to top)
        int span = 1;
        while(!stack.isEmpty() && stack.peek()[0]<=price){
            span+=stack.pop()[1];
        }
        stack.push(new int[]{price,span});
        return stack.peek()[1];
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */