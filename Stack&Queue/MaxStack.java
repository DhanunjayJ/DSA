import java.util.ArrayDeque;
import java.util.Deque;

class MaxStack {
    private Deque<Long> st;
    private long max;

    public MaxStack() {
        st = new ArrayDeque<>();
        max = 0;
    }
    
    public void push(int val) {
        long diff = 0;
        if (st.isEmpty()) {
            max = val;
        } else {
            diff = (long) val - max;
            if (diff > 0) {
                max = val; // new maximum established
            }
        }
        st.push(diff);
    }
    
    public void pop() {
        long rem = st.pop();
        if (rem > 0) {
            // Restore previous max: old_max = new_max - diff
            max = max - rem;
        }
    }
    
    public int top() {
        long rem = st.peek();
        if (rem > 0) {
            return (int) max;
        } else {
            return (int) (max + rem);
        }
    }
    
    public int getMax() {
        return (int) max;
    }
}