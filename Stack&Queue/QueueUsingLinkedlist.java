// Node class
class Node {
    int data;
    Node next;

    Node(int new_data) {
        data = new_data;
        next = null;
    }
}

// Queue class
class myQueue {
    Node front;
    Node rear;
    int size = 0;
    public myQueue() {
        // Initialize your data members
        size = 0;
        front = null;
        rear = null;
    }

    public boolean isEmpty() {
        // check if the queue is empty
        return front==null;
    }

    public void enqueue(int x) {
        // Adds an element x at the rear of the queue.
        Node node = new Node(x);
        if(front==null){
            front = node;
            rear = node;
        }else{
            rear.next = node;
            rear = rear.next;
        }
        size++;
    }

    public void dequeue() {
        // Removes the front element of the queue
        front = front.next;
        size--;
        if(front==null){
            rear = null;
        }
    }

    public int getFront() {
        // Returns the front element of the queue.
        // If queue is empty, return -1.
        if(front==null){
            return -1;
        }else{
            return front.data;
        }
    }

    public int size() {
        // Returns the current size of the queue.
        return size;
    }
}
