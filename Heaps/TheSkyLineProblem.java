class Pair implements Comparable<Pair> {
    int x;
    int h;
    Pair(int x,int h){
        this.x = x;
        this.h = h;
    }
    public int compareTo(Pair o){
        if(this.x!=o.x){
            return this.x-o.x;
        }else{
            return this.h-o.h;
        }
    }
}
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Pair> points = new ArrayList<>();
        for(int i=0;i<buildings.length;i++){
            int sp = buildings[i][0];
            int ep = buildings[i][1];
            int h = buildings[i][2];
            points.add(new Pair(sp,-h));
            points.add(new Pair(ep,h));
        }
        Collections.sort(points);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.add(0);
        int prevht = 0;
        for(int i=0;i<points.size();i++){
            int x = points.get(i).x;
            int h = points.get(i).h;
            if(h<0){pq.add(-h);}else{pq.remove(h);}
            if(pq.peek()!=prevht){
                ArrayList<Integer> t = new ArrayList<>();
                t.add(x);
                t.add(pq.peek());
                ans.add(t);
                prevht = pq.peek();
            }
        }
        return ans;
    }
}


// using treemap

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> ans = new ArrayList<>();
        List<int[]> points = new ArrayList<>();
        for(int [] b : buildings){
            int x = b[0];
            int y = b[1];
            int h = b[2];
            points.add(new int[]{x,-h});//to frame it as start index
            points.add(new int[]{y,h});//for the end index.
        }
        Collections.sort(points,(a,b) -> {
            //if both star the same, the compare the heights
            //most negative to most postive.
            if(a[0]==b[0]) return Integer.compare(a[1],b[1]); 
            return Integer.compare(a[0],b[0]);
        });

        TreeMap<Integer,Integer> map = new TreeMap<>();
        map.put(0,1); // ground level;
        //sweep line algo
        int prevHt = 0;
        for(int i=0;i<points.size();i++){
            int x = points.get(i)[0];
            int h = points.get(i)[1];
            if(h<0){
                //this is the starting so insert it if not already 
                //present in the treeMap.
                int actualHeight = -h;
                map.put(actualHeight,map.getOrDefault(actualHeight,0)+1);
            }else{
                //we have to remove this height/
                if(map.get(h)==1){
                    map.remove(h); // logn
                }else{
                    map.put(h,map.get(h)-1);
                }
            }
            int currMaxHeight = map.lastKey();
            //when ever there is a change in the current height
            //from the prev height because of the current height
            // we add that point to the answer. 
            if(currMaxHeight!=prevHt){
                ans.add(Arrays.asList(x,currMaxHeight));
                prevHt = currMaxHeight;
            }
        }
        return ans;
    }
}