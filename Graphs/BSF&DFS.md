Here is the standard, production-ready code for both Breadth-First Search (BFS) and Depth-First Search (DFS) in Java.

To keep things realistic and easy to follow, both algorithms use an **adjacency list** representation of a graph, where each node is represented by an integer ($0$ to $V-1$).

---

## 1. Breadth-First Search (BFS)

BFS explores the graph layer by layer, visiting all neighbors of a node before moving to the next level. It uses a **Queue** (First-In, First-Out) to track which nodes to visit next.

```java
import java.util.*;

public class GraphBFS {
    private final int vertices;
    private final List<List<Integer>> adjList;

    public GraphBFS(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Add an edge to the graph (directed)
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
    }

    // Iterative BFS traversal from a given source node
    public void bfs(int startNode) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();

        // Initialize the starting node
        visited[startNode] = true;
        queue.add(startNode);

        System.out.print("BFS Traversal: ");

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            // Get all adjacent vertices of the pulled vertex
            for (int neighbor : adjList.get(currentNode)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GraphBFS graph = new GraphBFS(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);

        // Expected output: 0 1 2 3 4
        graph.bfs(0);
    }
}

```

---

## 2. Depth-First Search (DFS)

DFS dives as deep as possible down a single path before backtracking. The most standard way to write this is using **recursion**, which implicitly uses the **Call Stack**.

```java
import java.util.*;

public class GraphDFS {
    private final int vertices;
    private final List<List<Integer>> adjList;

    public GraphDFS(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Add an edge to the graph (directed)
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
    }

    // Main DFS method that sets up the visited array
    public void dfs(int startNode) {
        boolean[] visited = new boolean[vertices];
        System.out.print("DFS Traversal: ");
        dfsHelper(startNode, visited);
        System.out.println();
    }

    // Recursive helper method for DFS
    private void dfsHelper(int currentNode, boolean[] visited) {
        // Mark the current node as visited and print it
        visited[currentNode] = true;
        System.out.print(currentNode + " ");

        // Recur for all the vertices adjacent to this vertex
        for (int neighbor : adjList.get(currentNode)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public static void main(String[] args) {
        GraphDFS graph = new GraphDFS(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 4);

        // Expected output: 0 1 3 4 2
        graph.dfs(0);
    }
}

```

---

### Key Takeaways & Differences

* **Time Complexity:** Both algorithms run in $O(V + E)$ time, where $V$ is the number of vertices and $E$ is the number of edges.
* **Space Complexity:** Both use $O(V)$ space to keep track of visited nodes. However, BFS's queue stores a layer's width, while DFS's stack stores a path's depth.
* **Use Cases:** Use **BFS** if you need to find the shortest path in an unweighted graph. Use **DFS** if you need to explore every permutation, check for cycles, or do topological sorting.