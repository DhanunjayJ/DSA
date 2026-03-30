
class Node {
    int key;
    int value;
    Node prev = null;
    Node next = null;
    Node (int key,int value){
        this.key = key;
        this.value = value;
    }
    Node () {
    }
}

class LRUCache {
    
    Node head;
    Node tail;
    HashMap<Integer,Node> hm;
    int cap;
    int size;

    public LRUCache(int capacity) {
        cap = capacity;
        size = 0;

        head = new Node();
        tail = new Node();

        head.next = tail;
        tail.prev = head;

        hm = new HashMap<>();
    }

    public Node removeNode(Node node){
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        return node;
    }

    public void addLast(Node node){
        Node prev = tail.prev;
        prev.next = node;
        node.next = tail;
        tail.prev = node;
        node.prev = prev;
    }

    public int get(int key) {
        if(!hm.containsKey(key)) return -1;
        Node gNode = hm.get(key);
        removeNode(gNode);
        addLast(gNode);
        return gNode.value;
    }
    
    public void put(int key, int value) {
        if(!hm.containsKey(key)){
            Node node = new Node(key,value);
            if(cap==size){
                Node rem = removeNode(head.next);
                hm.remove(rem.key);
                size--;
            }
            addLast(node);
            hm.put(key,node);
            size++;
        }
        Node temp = hm.get(key);
        temp.value = value;
        removeNode(temp);
        addLast(temp);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */