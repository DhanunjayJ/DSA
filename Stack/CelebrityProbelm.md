In the context of **Graph Theory** (which is the foundation for the Celebrity Problem), **indegree** and **outdegree** describe the direction of "relationships" or "edges" connected to a specific person (a node).

Imagine a social network where an arrow from **Person A** to **Person B** means "A knows B."

---

### 1. Outdegree (Outgoing arrows)
This represents the number of people a specific person **knows**.
* **In the Celebrity Problem:** A celebrity must have an **outdegree of 0**. They don't know anyone else at the party.
* **Visual:** If you are a node, the outdegree is the count of arrows pointing *away* from you toward others.

### 2. Indegree (Incoming arrows)
This represents the number of people who **know** a specific person.
* **In the Celebrity Problem:** A celebrity must have an **indegree of $N-1$** (where $N$ is the total number of people). This means every single other person at the party knows them.
* **Visual:** If you are a node, the indegree is the count of arrows pointing *at* you from others.



---

### Summary Table

| Term | Direction | Meaning in Celebrity Problem |
| :--- | :--- | :--- |
| **Outdegree** | **From** the person | How many people they **know**. |
| **Indegree** | **To** the person | How many people **know them**. |

