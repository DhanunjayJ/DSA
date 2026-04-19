import java.util.ArrayList;
import java.util.PriorityQueue;

public class MinimumCostToConnectSticks {
    public static long minimumCostToConnectSticks(ArrayList<Integer> arr) {
	    // Wrtie your code here.
		if(arr.size()<2) return 0L;

		PriorityQueue <Integer> pq = new PriorityQueue<>();

		long cost = 0;
		
		for(int stick:arr){
			pq.add(stick);
		}

		while(pq.size()>1){
			int firstSmall = pq.poll();
			int secondSmall = pq.poll();

			int sum = firstSmall+secondSmall;

			cost+= sum;
			pq.add(sum);

		}
		return cost;
	}
}
