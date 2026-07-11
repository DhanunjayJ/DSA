```java

class Solution {
    int [] par;
    int [] rank;
    public int removeStones(int[][] stones) {
        /*
        If a point has the same row or col as the point that is already
        there then we can remove it.

        if we take that a conncectd compoennts. instead of removing them
        we connect them to make one coomponent.

        so atlast we return the stones removed as stones- connected components.

        to do this we maintain two arrays of 
        rows
        cols

        and par array as the stone.

        rows and cols intailaly filled with -1.

        when a new stone comes

        iit check if row or cols anyhting either of them is 
        -1 or not.

        if yes
        get the index of the stone. which is either in 
        row or col.

        if one one row is mapped and other is -1.

        set it the same index of the stone. 

        then go to the par array and mark the 
        current stone as the index that is present 
        int he row or col array. 


        if not
        meaing btoh x and y arrays are -1
        meaning there is not stone tha is having the same coordinates
        then we take index of the current stone

        put in the both row and col array. 
        and keep the parent as same as the index.

        at last count the conncedte components and return stone - connected.
        */

        int n = stones.length;
        par = new int[n];
        rank = new int[n];

        for(int i=0;i<n;i++){
            par[i] = i;
        }

        int [] rows = new int[10001];
        int [] cols = new int[10001];

        Arrays.fill(rows,-1);
        Arrays.fill(cols,-1);

        for(int i=0;i<n;i++){
            
            int x = stones[i][0];
            int y = stones[i][1];

            if(rows[x]==-1 && cols[y]==-1){
                rows[x] = i;
                cols[y] = i;
            }else{
                int parStone = -1;
                if(rows[x]!=-1 && cols[y]==-1){
                    parStone = rows[x];
                    //making them both should have the same stone
                    cols[y] = parStone;
                }else if(rows[x]==-1 && cols[y]!=-1){
                    parStone = cols[y];
                    rows[x] = parStone;
                }else{
                    //if both has the same stone.
                    //not equal to -1.
                    if(rows[x]!=cols[y]){
                        //then first we need to ake them union
                        union(rows[x],cols[y]);
                    }
                    parStone = rows[x];
                }
                //do the union of the current stone
                //index with the parent stone index.
                union(parStone,i);
            }
        }

        int components = 0;
        for(int i=0;i<n;i++){
            if(i==par[i]) components++;
        }
        return n-components;
    }
    
    public int find(int x){
        if(x==par[x]) return x;
        int temp = find(par[x]);
        par[x] = temp;
        return temp;
    }

    public void union(int x,int y){
        int px = find(x);
        int py = find(y);
        if(px==py) return;
        if(rank[px]>rank[py]){
            par[py] = px;
        }else if(rank[py]>rank[px]){
            par[px] = py;
        }else{
            par[px] = py;
            rank[py]++;
        }
    }
}

```

Your fundamental logic for LeetCode 947 ("Most Stones Removed with Same Row or Column") is completely correct! The insight that **$\text{Max Removed Stones} = \text{Total Stones} - \text{Connected Components}$** is the absolute optimal mathematical way to solve this problem.

That said, **you did overcomplicate the execution.** Managing three separate index-tracking states (`rows`, `cols`, and `par` indices) forces you into an intricate `if-else` maze to figure out which stone index to bind to what coordinate. It works, but it's very easy to create edge-case bugs that way.

---

## How to Uncomplicate It: Coordinate Mapping

Instead of treating the DSU elements as *stone indices* and mapping rows/cols to them, a much cleaner approach is to treat **the rows and columns themselves as the nodes of your DSU.**

If a stone is placed at `(row, col)`, it simply acts as an edge that connects row node `x` to column node `y`.

### The Coordinate Clash Problem

A row numbered `5` and a column numbered `5` are completely different entities, but in a standard flat DSU array, they would both map to index `5`. To separate them seamlessly without creating complex logic, you can use a bitwise NOT operator (`~col`) or shift the column indices past the maximum possible row index value:

$$\text{Unique Column ID} = \text{col} + 10001$$

This transforms rows `0` to `10000` and columns `0` to `10000` into a single, clean sequence of numbers from `0` to `20002`.

---

## Refactored Java Code

By treating the row and shifted column numbers as the actual DSU values, your main processing loop collapses into just a single, unconditional call to `union()`.

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    int[] par;
    int[] rank;

    public int removeStones(int[][] stones) {
        // The constraints state that coordinates are between 0 and 10000.
        // Rows occupy 0-10000. Shifted columns occupy 10001-20002.
        int maxNodes = 20003; 
        par = new int[maxNodes];
        rank = new int[maxNodes];

        for (int i = 0; i < maxNodes; i++) {
            par[i] = i;
        }

        // 1. Union the row and column component for every stone
        for (int[] stone : stones) {
            int rowNode = stone[0];
            int colNode = stone[1] + 10001; // Offset to isolate columns
            union(rowNode, colNode);
        }

        // 2. Count the number of unique component roots present in the scene
        Set<Integer> uniqueRoots = new HashSet<>();
        for (int[] stone : stones) {
            uniqueRoots.add(find(stone[0]));
        }

        // Max removed stones = Total stones - Connected Components
        return stones.length - uniqueRoots.size();
    }

    public int find(int x) {
        if (x == par[x]) return x;
        return par[x] = find(par[x]); // Path compression
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return;

        if (rank[rootX] > rank[rootY]) {
            par[rootY] = rootX;
        } else if (rank[rootY] > rank[rootX]) {
            par[rootX] = rootY;
        } else {
            par[rootX] = rootY;
            rank[rootY]++;
        }
    }
}

```

---

## Why this structure is better

1. **No complex branching:** You completely bypass the nested `if-else` cascades checking for `-1` in rows and columns.
2. **True linear processing:** Every stone represents exactly one edge insertion. The loop body is a crisp, single statement: `union(row, shiftedCol);`.
3. **Easy tracking:** Instead of trying to maintain a dynamic component counter while juggling distinct array boundaries, counting the final unique roots generated by active stone positions via a `HashSet` guarantees an accurate component tally.