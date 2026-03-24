class MinStack {
    
    Deque<Long> stack;
    long min;

    public MinStack() {
        stack = new ArrayDeque<>();
        min = Long.MAX_VALUE;
    }
    
    public void push(int val) {
        long diff = 0;
        if(stack.isEmpty()){
            min = val;
            diff = 0;
        }else{
            diff = val - min;
            if(diff<0){
                min = val;
            }
        }
        stack.push(diff);
    }
    
    public void pop() {
        long pop = stack.pop();
        if(pop<0){
            min = min - pop;
        }
    }
    
    public int top() {
        if(stack.peek()<0){
            return (int) min;
        }else{
           return (int)(stack.peek()+min);
        }
    }
    
    public int getMin() {
        return (int) min;
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */