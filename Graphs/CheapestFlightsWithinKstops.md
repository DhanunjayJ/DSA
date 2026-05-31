Your approach for **LeetCode 787: Cheapest Flights Within K Stops** is on the right track! You’ve correctly identified that this is a shortest-path problem with a constraint (maximum of $k$ stops), and you're using a Breadth-First Search (BFS) / Level-Order Traversal approach to explore the graph level by level (where each level represents the number of stops).

However, your code has a **critical logical flaw** that will cause it to fail on certain test cases, plus a few missed opportunities for optimization.

---

## 🛑 The Issue with Your Current Approach

Your `minPrice` array only keeps track of the absolute minimum price seen so far at any node.

**The Bug:** If your code visits a node via a path that is **cheaper but uses more stops**, it will overwrite `minPrice[node]` and block any subsequent paths to that node. However, a later path might be **more expensive but use fewer stops**, which could actually be the *only* path capable of reaching the destination within the $k$ stops limit.

### Example where your code fails:

Imagine you want to go from `A` to `C` with `k = 1`.

* Path 1: `A -> B` (Price: 10, Stops: 0) -> `B -> D` (Price: 10, Stops: 1) -> `D -> C` (Price: 10, Stops: 2). Total price = 30, but 2 stops (Invalid, since $k=1$).
* Path 2: `A -> D` (Price: 50, Stops: 0) -> `D -> C` (Price: 10, Stops: 1). Total price = 60, 1 stop (Valid!).

If Path 1 reaches node `D` first, it sets `minPrice[D] = 20`. When Path 2 reaches `D` with a price of 50, your condition `if(minPrice[nbr.node] > nextPrice)` fails because $20 < 50$. Path 2 is pruned, and your code will return `-1` instead of `60`.

---

## 🏆 The "Standard" Way: Modified BFS (Level-by-Level)

The cleanest standard way to fix your exact logic is to change the loop structure. Instead of tracking stops inside the queue array element, **process the queue level by level (size-by-size) exactly $k+1$ times**.

To fix the bug mentioned above, we use a clone/copy of our `minPrice` array at each level so that updates within the same number of stops don't prematurely block other valid routes.

### Optimized Standard Solution (BFS):

```java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 1. Build adjacency list
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] flight : flights) {
            adj.get(flight[0]).add(new int[]{flight[1], flight[2]}); // {neighbor, price}
        }

        // 2. minPrice array tracking the cheapest cost to reach each node
        int[] minPrice = new int[n];
        Arrays.fill(minPrice, Integer.MAX_VALUE);
        minPrice[src] = 0;

        // Queue stores only the node ID
        Queue<int[]> q = new LinkedList<>(); 
        q.add(new int[]{src, 0}); // {node, current_price_from_src}

        int stops = 0;
        
        // Loop at most k + 1 times (since k stops means k + 1 edges)
        while (!q.isEmpty() && stops <= k) {
            int size = q.size();
            // Create a copy to prevent updating prices using edges from the same level
            int[] nextMinPrice = Arrays.copyOf(minPrice, n);

            for (int i = 0; i < size; i++) {
                int[] curr = q.poll();
                int u = curr[0];
                int pricePaid = curr[1];

                // If this path is already worse than something we found in a previous level, skip
                if (pricePaid > minPrice[u]) continue;

                for (int[] nbr : adj.get(u)) {
                    int v = nbr[0];
                    int weight = nbr[1];

                    if (pricePaid + weight < nextMinPrice[v]) {
                        nextMinPrice[v] = pricePaid + weight;
                        q.add(new int[]{v, nextMinPrice[v]});
                    }
                }
            }
            minPrice = nextMinPrice; // Move to the next step/stop level
            stops++;
        }

        return minPrice[dst] == Integer.MAX_VALUE ? -1 : minPrice[dst];
    }
}

```

---

## 🔄 Alternative Approaches

Here are the other two popular paradigms used to solve this problem.

### 1. The Bellman-Ford Algorithm (Easiest to implement)

Because we are strictly bounded by $k$ stops ($k+1$ edges), Bellman-Ford is perfect here. We just run the relaxation loop exactly $k+1$ times.

* **Time Complexity:** $\mathcal{O}(K \times E)$ where $E$ is the number of flights.
* **Space Complexity:** $\mathcal{O}(N)$ to store prices.

```java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] prices = new int[n];
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0;

        // Relax all edges k + 1 times
        for (int i = 0; i <= k; i++) {
            int[] temp = Arrays.copyOf(prices, n);
            for (int[] flight : flights) {
                int u = flight[0];
                int v = flight[1];
                int price = flight[2];

                if (prices[u] != Integer.MAX_VALUE && prices[u] + price < temp[v]) {
                    temp[v] = prices[u] + price;
                }
            }
            prices = temp;
        }

        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
    }
}

```

### 2. Modified Dijkstra's Algorithm (Fastest for large graphs)

Standard Dijkstra uses a PriorityQueue sorted by distance/price. To make it work with the $k$-stops constraint, we sort the PriorityQueue by **price**, but we also keep track of how many stops it took to get there. We only prune a path if it is *both* more expensive and takes more stops than a previously seen path.

* **Time Complexity:** $\mathcal{O}(E + N \log N)$
* **Space Complexity:** $\mathcal{O}(N + E)$

```java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] f : flights) adj.get(f[0]).add(new int[]{f[1], f[2]});

        // Priority Queue: {node, price, stops} -> sorted by price ascending
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.add(new int[]{src, 0, 0});

        // Track stops to reach a node. 
        // We only visit a node if we found a path with FEWER stops than before.
        int[] stopsVisited = new int[n];
        Arrays.fill(stopsVisited, Integer.MAX_VALUE);
        stopsVisited[src] = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int price = curr[1];
            int stops = curr[2];

            if (u == dst) return price;
            if (stops > k) continue; // Cannot move forward if we already hit the limit

            for (int[] nbr : adj.get(u)) {
                int v = nbr[0];
                int weight = nbr[1];

                // Optimization: Only push to PQ if it offers fewer stops than previous visits to 'v'
                if (stops + 1 < stopsVisited[v]) {
                    stopsVisited[v] = stops + 1;
                    pq.add(new int[]{v, price + weight, stops + 1});
                }
            }
        }
        return -1;
    }
}

```

## 📊 Summary Comparison

| Approach | Time Complexity | Space Complexity | Best Used For |
| --- | --- | --- | --- |
| **Bellman-Ford** | $\mathcal{O}(K \cdot E)$ | $\mathcal{O}(N)$ | Small $K$, clean and short code. |
| **Standard BFS** | $\mathcal{O}(K \cdot N + E)$ | $\mathcal{O}(N + E)$ | Intuitive level-by-level traversal. |
| **Modified Dijkstra** | $\mathcal{O}(E \log N)$ | $\mathcal{O}(N + E)$ | Dense graphs where finding the absolute cheapest path quickly is vital. |