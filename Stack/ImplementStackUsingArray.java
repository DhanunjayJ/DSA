class myStack {
    private int top;
    private int [] stack;
    public myStack(int n) {
        // Define Data Structures
        stack = new int[n];
        top = -1;
    }

    public boolean isEmpty() {
        // check if the stack is empty
        return top==-1;
    }

    public boolean isFull() {
        // check if the stack is full
        return top==stack.length-1;
    }

    public void push(int x) {
        // Inserts x at the top of the stack
        if(top!=stack.length-1){
            stack[++top] = x;
        }
    }

    public void pop() {
        // Removes an element from the top of the stack
        if(top!=-1){
            stack[top--] = 0;
        }
    }

    public int peek() {
        // Returns the top element of the stack
        if(top!=-1){
            return stack[top];
        }
        return top;
    }
}