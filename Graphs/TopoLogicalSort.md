Here is the Java implementation of **Topological Sort** using Kahn's Algorithm (BFS-based approach). This method is highly preferred because it also inherently detects if a cycle exists in the graph.

```java
import java.util.*;

public class TopologicalSort {
    
    // Function to perform Topological Sort
    public static List<Integer> topologicalSort(int vertices, List<List<Integer>> adj) {
        int[] inDegree = new int[vertices];
        
        // Step 1: Calculate in-degree of all vertices
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : adj.get(i)) {
                inDegree[neighbor]++;
            }
        }
        
        // Step 2: Queue to store vertices with in-degree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        
        List<Integer> topoOrder = new ArrayList<>();
        
        // Step 3: Process vertices
        while (!queue.isEmpty()) {
            int current = queue.poll();
            topoOrder.add(current);
            
            // Reduce the in-degree of all adjacent vertices
            for (int neighbor : adj.get(current)) { 
                inDegree[neighbor]--;
                
                // If in-degree becomes 0, add it to the queue
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        // Step 4: Check for a cycle (If topoOrder doesn't contain all vertices)
        if (topoOrder.size() != vertices) {
            System.out.println("Graph contains a cycle! Topological sort not possible.");
            return new ArrayList<>(); 
        }
        
        return topoOrder;
    }

    public static void main(String[] args) {
        int vertices = 6;
        List<List<Integer>> adj = new ArrayList<>();
        
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
        
        // Adding edges (Directed Acyclic Graph)
        adj.get(5).add(2);
        adj.get(5).add(0);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);
        
        System.out.println("Topological Sort Order:");
        List<Integer> result = topologicalSort(vertices, adj);
        System.out.println(result);
    }
}

```

### Complexity

* **Time Complexity:** $O(V + E)$, where $V$ is the number of vertices and $E$ is the number of edges. We visit every vertex and edge exactly once.
* **Space Complexity:** $O(V)$ for the in-degree array, queue, and result list.

> **Note:** I have noted your preference! Moving forward, whenever you ask for code, I will provide it in **Java** by default unless you explicitly request a different language.



---

Here is the complete breakdown of the concept behind **Topological Sort**, how it works under the hood, its edge cases, and its real-world applications.

---

## 1. The Core Concept

Think of Topological Sort as a **dependency resolver**.

If you are a university student, you cannot take *Advanced Algorithms* until you have taken *Data Structures*, and you can't take *Data Structures* until you've taken *Intro to Programming*. Topological Sort takes a list of these dependencies and outputs a linear ordering (a flat list) where every prerequisite comes **before** the task that depends on it.

Mathematically, for a directed edge $U \rightarrow V$, vertex $U$ must appear before vertex $V$ in the final ordering.

### The Golden Rule

Topological sort **only** works on a **DAG (Directed Acyclic Graph)**.

* **Directed:** The relationships must have a clear direction (e.g., $A$ leads to $B$).
* **Acyclic:** There must be **no cycles** (e.g., if $A$ requires $B$, $B$ requires $C$, and $C$ requires $A$, it is impossible to start anywhere).

---

## 2. How it Works (Kahn’s Algorithm vs DFS)

There are two primary ways to conceptualize and solve this problem:

### Approach A: Kahn’s Algorithm (In-Degree / BFS-based)

This is the approach coded previously. It works like a factory assembly line:

1. **Find the independent tasks:** Calculate the "in-degree" (number of incoming edges) for every node. Nodes with an in-degree of `0` have no prerequisites.
2. **Queue them up:** Put all nodes with in-degree `0` into a queue.
3. **Process and remove:** Pull a node from the queue, add it to your final sorted list, and "delete" its outgoing edges from the graph.
4. **Update neighbors:** By deleting those edges, some neighboring nodes will now have an in-degree of `0`. Put them into the queue.
5. Repeat until the queue is empty.

### Approach B: DFS-Based Algorithm

This approach works backward from the destination:

1. Start at an unvisited node and explore as deep as possible using Depth-First Search.
2. When you hit a node that has no outgoing edges (a "dead end"), it means nothing else depends on this node. It is the "ultimate consequence."
3. Push this node onto a **Stack**.
4. Backtrack and repeat for all unvisited nodes.
5. Pop everything off the stack to get your topological order.

---

## 3. Detecting Cycles

If a graph contains a cycle, a valid topological sort is **impossible**. Both algorithms handle cycle detection beautifully, but in different ways:

### In Kahn’s Algorithm (BFS)

If there is a cycle, the nodes involved in the cycle will **never** have their in-degree drop to `0`. Consequently, they will never enter the queue.

* **Detection:** At the end of the algorithm, if the number of elements in your sorted list is **less than** the total number of vertices in the graph, a cycle exists.

### In the DFS Approach

To detect a cycle during DFS, you must keep track of nodes currently in the "recursion stack" (the path you are currently exploring).

* **Detection:** If you are performing a DFS and you hit a node that is already marked as "currently visiting" (part of your current path), you have found a back-edge, which means a cycle exists.

---

## 4. Edge Cases and Special Situations

When writing production code or solving interview problems, always keep these scenarios in mind:

* **Multiple Valid Orders:** Topological sort is rarely unique. If multiple nodes have an in-degree of `0` at the same time, the order you process them doesn't matter. Both `[A, B, C]` and `[B, A, C]` could be completely correct answers for the same graph.
* **Disconnected Graphs:** The graph might have two completely separate components (e.g., Tasks A $\rightarrow$ B, and completely unrelated Tasks X $\rightarrow$ Y). Your algorithm must ensure it iterates through all vertices to catch disconnected components, not just start from a single root node.
* **Self-Loops:** A node pointing to itself ($A \rightarrow A$) is the smallest possible cycle. Kahn's algorithm handles this naturally (its in-degree will never hit 0), but ensure your input parsing doesn't break.
* **Empty Graph or Single Node:** A graph with 0 or 1 node is technically already topologically sorted. Ensure your code doesn't throw a `NullPointerException` or `IndexOutOfBoundsException`.

---

## 5. Real-World Applications

Topological sorting is incredibly common under the hood of tools you likely use daily:

* **Build Systems (e.g., Maven, Gradle, Make):** When building a massive project, if Module C depends on Module B, and Module B depends on Module A, the build system uses topological sort to compile Module A first.
* **Package Managers (e.g., `npm`, `pip`, `apt-get`):** When you run `npm install`, the tool builds a dependency graph of libraries and installs them in the precise topological order required so that no library throws a "missing dependency" error.
* **Task Scheduling / Airflow / Jenkins:** Orchestrating complex data pipelines where Step Y requires data generated by Step X.
* **Spreadsheet Formula Evaluation:** In Excel, if cell `C1 = A1 + B1`, Excel uses a topological sort to ensure `A1` and `B1` are evaluated *before* updating `C1`.

---