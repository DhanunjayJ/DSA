class Solution {
    int [] par;
    int [] rank;
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        /*
        */

        //1. construct a hashmap of emails and accounts it belongs to.
        //2. Iterate thorugh all the emails and do union.
        //3. all the accounts which has the same root needs to be merges
        //so we intilize an array with each root having the index to be merged
        //then we merge and return.

        HashMap<String,List<Integer>> emailsToAccount = new HashMap<>();
        
        for(int j=0;j<accounts.size();j++){
            for(int i=1;i<accounts.get(j).size();i++){
                emailsToAccount.computeIfAbsent(accounts.get(j).get(i),k -> new ArrayList<>()).add(j);
            }
        }

        int n = accounts.size();

        par = new int[n];
        rank = new int[n];

        for(int i=0;i<n;i++){
            par[i] = i;
        }

        // merging the common accouns
        for(String key : emailsToAccount.keySet()){
            List<Integer> cAccounts = emailsToAccount.get(key);
            if(cAccounts.size()>1){
                for(int i=0;i<cAccounts.size()-1;i++){
                    union(cAccounts.get(i),cAccounts.get(i+1));
                }
            }
        }

        List<Integer>[] commonAccounts = new ArrayList[n];

        //merge the common parent compoennts.
        for(int i=0;i<n;i++){
            int root = find(i);
            if(commonAccounts[root]==null){
                commonAccounts[root] = new ArrayList<>();
            }
            commonAccounts[root].add(i);
        }

        List<List<String>> ans = new ArrayList<>();

        for(int i=0;i<n;i++){
            if(commonAccounts[i]!=null)
            {
            HashSet<String> emails = new HashSet<>();
            String name = "";
            for(int index : commonAccounts[i]){
                List<String> account = accounts.get(index);
                name = account.get(0);
                for(int j=1;j<account.size();j++){
                    emails.add(account.get(j));
                }
            }
            List<String> temp = new ArrayList<>(emails);
            Collections.sort(temp);
            temp.add(0,name);
            ans.add(temp);
            }
        }

        return ans;
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
        }else if(rank[px]<rank[py]){
            par[px] = py;
        }else{
            par[px] = py;
            rank[py]++;
        }

    }
}