public class Solution {
    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder(String[] words) {
        // Write your code here
        if(words.length==0 || words==null) return "";

        HashMap<Character,HashSet<Character>> graph = new HashMap<>();
        HashMap<Character,Integer> indegree = new HashMap<>();

        //intializaing the grpah and indegree
        for(int i=0;i<words.length;i++){
            for(char c : words[i].toCharArray()){
                graph.putIfAbsent(c, new HashSet<>());
                indegree.put(c,0);
            }
        }

        //building the graph
        for(int i=0;i<words.length-1;i++){
            
            String word1 = words[i];
            String word2 = words[i+1];

            //prefix rule if the example case "abc" "ab" present.
            if(word1.length()>word2.length() && word1.startsWith(word2)){
                return "";
            }

            int min = Math.min(word1.length(),word2.length());

            for(int j=0;j<min;j++){
                char parent = word1.charAt(j);
                char child = word2.charAt(j);

                if(parent!=child){
                    if(!graph.get(parent).contains(child)){
                        graph.get(parent).add(child);
                        indegree.put(child,indegree.get(child)+1);
                    }
                    break;
                }
            }

        }

        //for normal alphabetical ordering incase of zero indegree
        PriorityQueue<Character> pq = new PriorityQueue<>();
        for(char c : indegree.keySet()){
            if(indegree.get(c)==0) pq.add(c);
        }

        StringBuilder st = new StringBuilder();

        while(!pq.isEmpty()){
            char rem = pq.remove();
            st.append(rem);
            for(char c: graph.get(rem)){
                indegree.put(c,indegree.get(c)-1);
                if(indegree.get(c)==0) pq.add(c);
            }
        }

        // there is a cycle.
        if(st.length()<indegree.size()){
            return "";
        }

        return st.toString();

    }
}