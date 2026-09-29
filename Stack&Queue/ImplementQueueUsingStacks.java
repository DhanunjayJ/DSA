//amortized O(1)

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class MyQueue {
    private final Deque<Integer> inStack;
    private final Deque<Integer> outStack;

    public MyQueue() {
        inStack = new ArrayDeque<>();
        outStack = new ArrayDeque<>();
    }

    // Push element x to the back of the queue: O(1)
    public void push(int x) {
        inStack.push(x);
    }

    // Removes the element from the front of the queue: Amortized O(1)
    public int pop() {
        shiftStacks();
        if (outStack.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return outStack.pop();
    }

    // Get the front element: Amortized O(1)
    public int peek() {
        shiftStacks();
        if (outStack.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return outStack.peek();
    }

    // Returns whether the queue is empty: O(1)
    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    // Helper: Transfers elements from inStack to outStack ONLY if outStack is empty
    private void shiftStacks() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
    }
}

//costly push way

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