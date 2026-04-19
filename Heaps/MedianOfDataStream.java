import java.util.Collections;
import java.util.PriorityQueue;

public class MedianOfDataStream {
    class MedianFinder {

    /*
    First we need median, median is calculated by the middle elements of the two arrays.
    so what we need to do is we want the two elements that are on left and that are on right. 
    so, we could maintain a two arrays with left and right. but what woud be result of the median?
    for sorted arrat the values in the middle are always the reason for the median. so. we try to
    maintian the median values always at the hand. so what we can do is...
    we can maintaint two priorty queues. one on the left which is the max pq and one on the right 
    was min pq. 
    if the current length == even, then we first add the element to the left one and get the max
    and put to the the right one and if the lefnt == odd and get the min from theright and put it on
    the left one.
    then when mediun is need we get the size is odd then peek the left one or elase 
    ge thte aveage of he both peeks.
    */
    PriorityQueue<Integer> left;
    PriorityQueue<Integer> right;
    int size = 0;

    public MedianFinder() {
        
        left = new PriorityQueue<>(Collections.reverseOrder());
        right = new PriorityQueue<>();
        size = 0;

    }
    
    public void addNum(int num) {
        if(size%2!=0){
            left.offer(num);
            right.offer(left.poll());
        }else{
            right.offer(num);
            left.offer(right.poll());
        }
        size++;
    }
    
    public double findMedian() {
        if(size%2==0){
            return (((right.peek())+(left.peek()))/2.0);
        }else{
            return left.peek()*1.0;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
}
