class Solution {
    public List<String> topKFrequent(String[] words, int k) {
        /*
        1. Hashmap <String,Integer> -> count
        2. priority compare base on the freq (min pq)
        3. use sorting for lexigophically smaller value when two freq are same.
        */
        HashMap<String,Integer> wordCount = new HashMap<>();
        for(String word : words) wordCount.put(word,wordCount.getOrDefault(word,0)+1);
        PriorityQueue<String> pq = new PriorityQueue<>(
            (a,b) -> {
                if(wordCount.get(a).equals(wordCount.get(b))){
                    return b.compareTo(a);
                }
                return wordCount.get(a)-wordCount.get(b);
            }
        );
        for(String word : wordCount.keySet()){
            pq.offer(word);
            if(pq.size()>k) pq.poll();
        }
        List<String> ans = new ArrayList<>();
        while(!pq.isEmpty()){
            ans.add(pq.remove());
        }
        Collections.reverse(ans);
        return ans;
    }
}