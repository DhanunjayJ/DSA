Here is the complete implementation of Kruskal's Algorithm in Java.

As we discussed, this implementation uses an efficient **Disjoint Set Union (DSU)** data structure with both **Path Compression** and **Union by Rank** to keep the cycle detection lightning-fast.

---

## Java Implementation

```java
import java.util.*;

// Class to represent a graph edge
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Comparator function used for sorting edges based on their weight
    @Override
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

// Class to represent the Disjoint Set (Union-Find)
class DisjointSet {
    int[] parent, rank;

    // Constructor
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        // Initially, all vertices are in their own set with a rank of 0
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find the representative/root of the set containing element 'i'
    // (Uses Path Compression)
    public int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // Path compression step
        }
        return parent[i];
    }

    // Unites the set that includes 'x' and the set that includes 'y'
    // (Uses Union by Rank)
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // If they are already in the same set, a cycle is detected
        if (rootX == rootY) {
            return false; 
        }

        // Attach smaller rank tree under root of high rank tree
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}

public class KruskalMST {

    public static void kruskalAlgo(int vertices, List<Edge> edges) {
        // Step 1: Sort all edges in non-decreasing order of their weight
        Collections.sort(edges);

        DisjointSet dsu = new DisjointSet(vertices);
        List<Edge> mst = new ArrayList<>();
        int mstWeight = 0;

        // Step 2: Iterate through the sorted edges
        for (Edge edge : edges) {
            // If the components are successfully merged, no cycle was formed
            if (dsu.union(edge.src, edge.dest)) {
                mst.add(edge);
                mstWeight += edge.weight;
            }

            // Optimization: Stop if we have gathered V - 1 edges
            if (mst.size() == vertices - 1) {
                break;
            }
        }

        // Print the resulting MST
        System.out.println("Edges in the constructed MST:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " -- " + edge.dest + " == " + edge.weight);
        }
        System.out.println("Minimum Spanning Tree Total Cost: " + mstWeight);
    }

    public static void main(String[] args) {
        int vertices = 4;
        List<Edge> edges = new ArrayList<>();

        // Creating a sample weighted, connected graph
        // Edge: src, dest, weight
        Edge e1 = new Edge(); e1.src = 0; e1.dest = 1; e1.weight = 10;
        Edge e2 = new Edge(); e2.src = 0; e2.dest = 2; e2.weight = 6;
        Edge e3 = new Edge(); e3.src = 0; e3.dest = 3; e3.weight = 5;
        Edge e4 = new Edge(); e4.src = 1; e4.dest = 3; e4.weight = 15;
        Edge e5 = new Edge(); e5.src = 2; e5.dest = 3; e5.weight = 4;

        edges.add(e1); edges.add(e2); edges.add(e3); edges.add(e4); edges.add(e5);

        kruskalAlgo(vertices, edges);
    }
}

```

---

## Code Breakdown

* **`Edge` Class:** Implements `Comparable<Edge>`. This makes it incredibly easy to use `Collections.sort(edges)`, sorting them by weight in a single line.
* **`find(int i)`:** This uses **path compression**. As it traverses up to find the absolute parent node, it updates the parent array for all nodes along the way, flattening the structure so subsequent lookups take $O(1)$ time.
* **`union(int x, int y)`:** This uses **union by rank**. It attaches the shorter tree to the root of the taller tree to ensure the tree depth stays as balanced as possible. It returns `false` if a loop/cycle is detected.

## Complexity Analysis

* **Time Complexity:** $O(E \log E)$ or $O(E \log V)$. Sorting the edges takes $O(E \log E)$ time. The DSU operations (Find and Union) take nearly $O(1)$ time per edge due to path compression and ranking. Thus, the sorting step dominates the runtime.
* **Space Complexity:** $O(V + E)$ to store the graph structures and the tracking arrays (`parent` and `rank`) in the Disjoint Set.