//TLE 1
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        
        List<List<String>> ans = new ArrayList<>();

        if(beginWord.equals(endWord)) {
            ans.add(new ArrayList<>(List.of(beginWord)));
            return ans;
        }
        
        if(!wordList.contains(endWord)) return ans;

        HashMap<String,ArrayList<String>> wordMap = new HashMap<>();

        List<String> allWords = new ArrayList<>(wordList);
        
        if(!allWords.contains(beginWord)){
            allWords.add(beginWord);
        }

        for(int i=0;i<allWords.size()-1;i++){
            String word1 = allWords.get(i);
            for(int j=i+1;j<allWords.size();j++){
                String word2 = allWords.get(j);
                if(isSimilar(word1,word2)){
                    wordMap.computeIfAbsent(word1, k -> new ArrayList<>()).add(word2);
                    wordMap.computeIfAbsent(word2, k -> new ArrayList<>()).add(word1);
                }
            }
        }

        List<String> seq = new ArrayList<>();
        HashSet<String> vis = new HashSet<>();
        seq.add(beginWord);
        vis.add(beginWord);
        backtrack(beginWord,endWord,seq,ans,wordMap,vis);
        List<List<String>> ans1 = new ArrayList<>();
        for(List<String> l : ans){
            if(l.size()==minSeq){
                ans1.add(l);
            }
        }
        return ans1;
    }

    public void backtrack(String bw,String ew,List<String> seq,List<List<String>> ans,HashMap<String,ArrayList<String>> wordMap,HashSet<String> vis){
        if(bw.equals(ew)){
            if(seq.size()<=minSeq){
                minSeq = seq.size();
                ans.add(new ArrayList<>(seq));
            }
            return;
        }
        for(String n:wordMap.getOrDefault(bw,new ArrayList<>())){
            if(!vis.contains(n)){
                vis.add(n);
                seq.add(n);
                backtrack(n,ew,seq,ans,wordMap,vis);
                vis.remove(n);
                seq.remove(seq.size()-1);
            }
        }
    }

    int minSeq = Integer.MAX_VALUE;

    public boolean isSimilar(String word1,String word2){
        int count = 0;
        for(int i=0;i<word1.length();i++){
            if(word1.charAt(i)!=word2.charAt(i)){
                count++;
            }
            if(count>1) return false;
        }
        return count==1;
    }
}


//TLE 2

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        
        if(beginWord.equals(endWord)){
            ans.add(new ArrayList<>(List.of(beginWord)));
            return ans;
        }
        //hashset for getting the checking if the word is present in the dict
        HashSet <String> dict = new HashSet<>();
        for(String word:wordList) dict.add(word);

        if(!dict.contains(endWord))return ans;

        //do bfs and find the shortest distacne between each and every string from the beginning. 

        HashMap<String,Integer> shortDist = new HashMap<>();

        bfs(beginWord,shortDist,dict);
        List<String> temp = new ArrayList<>();

        HashSet<String> vis = new HashSet<>();
        dfs(beginWord,endWord,shortDist,temp,ans,dict,vis,0);

        return ans;
    }

    public void bfs(String bw,HashMap<String,Integer> shortDist,HashSet <String> dict){
        HashSet<String> vis = new HashSet<>();
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(bw,0));
        vis.add(bw);

        while(!q.isEmpty()){

            Pair rem = q.remove();
            
            String word = rem.word;
            int seq  = rem.sNo;

            shortDist.put(word,seq);

            char [] wordC = word.toCharArray();

            for(int i=0;i<wordC.length;i++){

                char originalWord = wordC[i];

                for(char c='a';c<='z';c++){
                    
                    if(c==originalWord) continue;

                    wordC[i] = c;
                    String nbr = String.valueOf(wordC);

                    if(dict.contains(nbr) && !vis.contains(nbr)){
                        vis.add(nbr);
                        q.add(new Pair(nbr,seq+1));
                    }

                }

                wordC[i] = originalWord;

            }

        }

    }

    public void dfs(String bw,String ew,HashMap<String,Integer> shortDist,List<String> temp,List<List<String>> ans,HashSet <String> dict,
    HashSet<String> vis,int seq){
        
        vis.add(bw);
        temp.add(bw);

        if(bw.equals(ew)){
            ans.add(new ArrayList<>(temp));
            vis.remove(bw);
            temp.remove(temp.size()-1);
            return;
        }

        char [] wordC = bw.toCharArray();
        for(int i=0;i<wordC.length;i++){
            char originalWord = wordC[i];
            for(char c='a';c<='z';c++){
                if(c==originalWord) continue;
                wordC[i] = c;
                String nbr = String.valueOf(wordC);
                if(dict.contains(nbr) && !vis.contains(nbr) && shortDist.containsKey(nbr) && seq+1==shortDist.get(nbr)){
                    dfs(nbr,ew,shortDist,temp,ans,dict,vis,seq+1);
                }
            }
            wordC[i] = originalWord;
        }
        vis.remove(bw);
        temp.remove(temp.size()-1);
    }
}

class Pair{
    String word;
    int sNo;
    Pair(String word,int sNo){
        this.word = word;
        this.sNo = sNo;
    }
}


//The working Solution!!

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        /*
        Since doing bfs to find the shortest path from every word from the beginword
        and doing dfs from the begin word and only visit the word if it equal to the shortest path
        is giving tle. 

        so, what is the reason for that? 
        because there can many dead ends we are possibly visiting because of that we are geting tle. 
        even though we contructed graph and give the shortest distane to the ceratins word. 

        so the input we are giving to the dfs is again exploring all the dead ends. even though it foud
        ad shortest path. 

        so what we do it, we consructr a reverse graph. 

        what is reverse graph?
        when ever we reach a child from a parent. we map the child to parent. 
        so that this way dfs only need to see the hasmap to child to parent relations ship with no dead ends. 
        because if we reach child in multiple they will be a route to te parent. 

        this way we get the all possible shortest paths. 

        the approach
        1. we put all the wordilst to a dict. and use the same dict as visted array by removing vlaues
        once we vist that.
        2. we construct a hasmap of word -> hashset of childs. for reverse graph 
        3. do we need a queue? here? if we use queue. and wehn two two parents try to access a child
        if we use queue and delete and mark child as visited. it would lead to neglecting other paths.
        waht we do is. first we use two hashsets. current level next level. 
        once the current level is visited we add the next level things tot eh next level sets.

        this way if there are duplicates we could reove it since the next level is a ahshset. 
        this will not effect in  creating the child to parent graph.

        */
        List<List<String>> ans = new ArrayList<>();

        HashSet<String> dict = new HashSet<>(wordList);
        
        if(!dict.contains(endWord)) return ans;

        // this will hold the reverse graph.

        HashMap<String,HashSet<String>> childToParent = new HashMap<>();

        //to allow the parents to visit the same child multiple times
        //we need two hashsets. current and next

        HashSet<String> currentLevel = new HashSet<>();
        currentLevel.add(beginWord);

        //we need a boolean to check if we can acutally reach the endword.
        //if not we can just optimize by not doing dfs and return empty list.
        boolean found = false;

        while(!currentLevel.isEmpty() && !found){
            
            HashSet<String> nextLevel = new HashSet<>();

            for(String word : currentLevel){

                char [] wordChar = word.toCharArray();

                for(int i=0;i<wordChar.length;i++){

                    char originalChar = wordChar[i];

                    for(char c='a';c<='z';c++){

                        if(c==originalChar) continue;

                        wordChar[i] = c;

                        String nextW = String.valueOf(wordChar);

                        if(dict.contains(nextW)){

                            //put the mapping to the graph.
                            //add this to the next level

                            childToParent.computeIfAbsent(nextW, k -> new HashSet<>()).add(word);

                            nextLevel.add(nextW);

                            if(nextW.equals(endWord)) found = true;
                        }
                    }

                    wordChar[i] = originalChar;
                }
            }

            //once we complete the currentlevel then we need to delte the words from the nextleve
            //from the dict to mark it as already visited.

            for(String nextW : nextLevel){
                dict.remove(nextW);
            }

            currentLevel = nextLevel;
        }

        if(found)
        {
            dfs(childToParent,beginWord,endWord,ans,new ArrayList<>());
        }

        return ans;
    }

    public void dfs(HashMap<String,HashSet<String>> cToP, String finalWord, String srcWord, List<List<String>> ans, List<String> temp){

        temp.add(srcWord);

        if(srcWord.equals(finalWord)){
            List<String> validPath = new ArrayList<>(temp);
            Collections.reverse(validPath);
            ans.add(validPath);
        } else if(cToP.containsKey(srcWord)){
            for(String word:cToP.get(srcWord)){
                dfs(cToP,finalWord,word,ans,temp);
            }
        }
        
        temp.remove(temp.size()-1);
    }
}