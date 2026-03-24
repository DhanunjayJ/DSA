class MyQueue {
    Deque<Integer> primary;
    Deque<Integer> helper;
    public MyQueue() {
        primary = new ArrayDeque<>();
        helper = new ArrayDeque<>();
    }
    
    public void push(int x) {
        while(!primary.isEmpty()){
            helper.push(primary.pop());
        }
        primary.push(x);
        while(!helper.isEmpty()){
            primary.push(helper.pop());
        }
    }
    
    public int pop() {
       return primary.pop();
    }
    
    public int peek() {
        return primary.peek();
    }
    
    public boolean empty() {
        return primary.isEmpty();
    }
}


/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */