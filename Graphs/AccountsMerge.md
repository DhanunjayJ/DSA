Your logic is mostly solid, but there is one **major bug** in how you are grouping the accounts at the end, and a **small optimization** you should make to your loop.

### The Bug: The "Root" vs. "Parent" Problem

In this block:

```java
for(int i=0; i<n; i++){
    int root = par[i]; // ❌ BUG HERE
    // ...
}

```

You are using `par[i]` to group accounts. **`par[i]` is not necessarily the absolute root of the set.** Because of path compression, `par[i]` only points to the *immediate parent* of `i`. If you have a chain `0 -> 1 -> 2`, `par[0]` might be `1`, but the actual root is `2`.

Because you aren't calling `find(i)`, your `commonAccounts` array is grouping accounts based on their *direct parent*, not their *final representative*.

**The Fix:** Change `int root = par[i];` to `int root = find(i);`.

---

### The Cleaned-Up Solution

Here is the corrected code with the `find(i)` fix and a slight adjustment to the email logic so you don't have to manually add the name back in at the end.

```java
import java.util.*;

class Solution {
    int[] par;
    int[] rank;

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        par = new int[n];
        rank = new int[n];
        for(int i = 0; i < n; i++) par[i] = i;

        // Map email to the index of the FIRST account that claimed it
        HashMap<String, Integer> emailToId = new HashMap<>();

        for(int i = 0; i < n; i++) {
            for(int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);
                if(!emailToId.containsKey(email)) {
                    emailToId.put(email, i);
                } else {
                    // Union current account with the one that first claimed this email
                    union(i, emailToId.get(email));
                }
            }
        }

        // Grouping by ROOT
        Map<Integer, List<String>> rootToEmails = new HashMap<>();
        for(String email : emailToId.keySet()) {
            int root = find(emailToId.get(email));
            rootToEmails.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
        }

        // Building the result
        List<List<String>> ans = new ArrayList<>();
        for(int root : rootToEmails.keySet()) {
            List<String> emails = rootToEmails.get(root);
            Collections.sort(emails);
            
            // Re-attach the name
            List<String> account = new ArrayList<>();
            account.add(accounts.get(root).get(0));
            account.addAll(emails);
            ans.add(account);
        }
        return ans;
    }

    public int find(int x) {
        if(x == par[x]) return x;
        return par[x] = find(par[x]);
    }

    public void union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if(px != py) {
            if(rank[px] > rank[py]) par[py] = px;
            else if(rank[px] < rank[py]) par[px] = py;
            else {
                par[px] = py;
                rank[py]++;
            }
        }
    }
}

```

### Why this version is better:

1. **Efficiency:** Instead of creating a `List` for every email (which wastes memory), `emailToId` stores only the first index encountered. Any subsequent account containing that email is immediately `union`-ed with the first one.
2. **Correctness:** By using `rootToEmails` (a map keyed by the result of `find(i)`), we guarantee that all emails belonging to the same component are grouped together perfectly, regardless of the internal tree structure.
3. **No HashSets:** We add emails directly to lists and sort them once at the end, which is cleaner than converting back and forth between Sets and Lists.

---

Yes, **mapping the emails directly to an account index** and using DSU to merge those indices is the absolute standard, optimal way to solve this problem!

Your high-level strategy of finding common elements across accounts and merging their sets was 100% correct. The clean refactoring using `TreeSet` simply handles the unique-and-sorted constraint automatically, and mapping to the *first seen* account index instead of keeping a list of all account indices saves a lot of overhead.

### Let's verify why this is optimal (Time Complexity)

Let $N$ be the total number of accounts and $K$ be the maximum number of emails in an account.

* **Step 1: Mapping & Union:** We iterate through all emails across all accounts. There are roughly $N \times K$ total emails. For each email, we do a hash map lookup ($O(1)$) and a DSU `union` operation ($O(\alpha(N))$). This takes **$O(N \times K \cdot \alpha(N))$**.
* **Step 2: Grouping:** We iterate through the emails again to group them by their absolute root. This takes **$O(N \times K \cdot \log(N \times K))$** because inserting into a `TreeSet` takes logarithmic time to maintain sorted order.
* **Step 3: Final Output:** Building the final list takes linear time proportional to the total number of unique emails.

Overall, the time complexity is dominated by the sorting/TreeSet phase: **$O(N \times K \log(N \times K))$**, which is the absolute best you can achieve for this problem because of the sorting requirement.

---

### Master Checklists for your upcoming DSU questions

Since you've tackled a few of these now, keep these two golden rules in mind to avoid these exact bugs down the road:

1. **Never use `par[i]` to find the group leader:** Always use `find(i)` when you are doing your final pass to count components or group data. Flattening via path compression is a lazy operation—it only updates when `find()` is called!
2. **Watch your coordinate limits:** If a problem mixes rows and columns (like the Stones problem), shift one of them to prevent collisions (`col + offset`). If a problem maps elements to indices (like this Accounts problem), make sure you strip away non-element metadata (like the user names) before throwing them into your DSU loops.

---

