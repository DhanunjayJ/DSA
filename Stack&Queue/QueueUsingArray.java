//o(1)

class myQueue {
    int front;
    int rare;
    int size;
    int cap;
    int [] data;
    // Constructor
    public myQueue(int n) {
        // Define Data Structures
        data = new int[n];
        front = 0;
        rare = 0;
        size = 0;
        cap = n;
    }

    public boolean isEmpty() {
        // Check if queue is empty
        return size==0;
    }

    public boolean isFull() {
        // Check if queue is full
        return size==cap;
    }

    public void enqueue(int x) {
        // Enqueue
        if(size==cap) return;
        data[rare] = x;
        rare = (rare+1)%cap;
        size++;
        // System.out.println("rare "+rare);
    }

    public void dequeue() {
        // Dequeue
        if(size==0) return;
        front = (front+1)%cap;
        size--;
        // System.out.println("front "+front);
    }

    public int getFront() {
        // Get front element
        if(size==0) return -1;
        return data[front];
    }

    public int getRear() {
        // Get last element
        if(size==0) return -1;
        //step back 
        return data[(rare-1+cap)%cap];
    }
}



class myQueue {
    private int top;
    private int [] queue;
    // Constructor
    public myQueue(int n) {
        // Define Data Structures
        queue = new int[n];
        top = -1;
    }


    public boolean isEmpty() {
        // Check if queue is empty
        return top==-1;
    }


    public boolean isFull() {
        // Check if queue is full
        return top==queue.length-1;
    }


    public void enqueue(int x) {
        // Enqueue
        if(top!=queue.length-1){
            queue[++top]=x;
        }
    }


    public void dequeue() {
        // Dequeue
        if(top!=-1){
            for(int i=1;i<=top;i++){
                queue[i-1] = queue[i];
            }
            queue[top--] = 0;
        }
    }


    public int getFront() {
        // Get front element
        if(top!=-1){
            return queue[0];
        }
        return top;
    }


    public int getRear() {
        // Get last element
        if(top!=-1){
            return queue[top];
        }
        return top;
    }
}

