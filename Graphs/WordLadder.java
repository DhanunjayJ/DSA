class WordS{
    String word;
    int sNo;
    WordS(String word,int sNo){
        this.word = word;
        this.sNo = sNo;
    }
}

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        
        if (beginWord.equals(endWord)) return 1;
        
        if (!wordList.contains(endWord)) return 0;

        HashMap<String,ArrayList<String>> adj = new HashMap<>();

        for(int i=0;i<wordList.size()-1;i++){
            String word1 = wordList.get(i);
            for(int j=i+1;j<wordList.size();j++){
                String word2 = wordList.get(j);
                if(isSimilar(word1,word2)){
                    ArrayList<String> nbrs1 = adj.getOrDefault(word1,new ArrayList<>());
                    nbrs1.add(word2);
                    ArrayList<String> nbrs2 = adj.getOrDefault(word2,new ArrayList<>());
                    nbrs2.add(word1);
                    adj.put(word1,nbrs1);
                    adj.put(word2,nbrs2);
                }
            }
        }

        Queue<WordS> words = new LinkedList<>();

        HashSet<String> visited = new HashSet<>();

        for(String word:wordList){
            if(isSimilar(beginWord,word)){
                words.add(new WordS(word,2));
                visited.add(word);
            }
        }

        int minSeq = Integer.MAX_VALUE;

        while(!words.isEmpty()){

            WordS rem = words.remove();

            if(rem.word.equals(endWord)){
                minSeq = Math.min(rem.sNo,minSeq);
            }

            for(String nbr : adj.getOrDefault(rem.word, new ArrayList<>())){
                if(!visited.contains(nbr)){
                    visited.add(nbr);
                    words.add(new WordS(nbr,rem.sNo+1));
                }
            }

        }

        return minSeq == Integer.MAX_VALUE ? 0 : minSeq;
    }

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