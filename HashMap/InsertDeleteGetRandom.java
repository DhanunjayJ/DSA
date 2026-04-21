class RandomizedCollection {
    /*
    First we need to get random, to get the random numbers in o(n) we need to store
    all the numbers in a arraylist. so that we can easily get the random number by doing
    random the length of the arraylist.

    Since there can be duplicates. we need remove particular number. how can we resolve this one.
    first to get the number if it there or not int he array list we need to have hashmap so that we get 
    it in the O(1) time complexity. 

    once we get it. how we are going to handle the duplicates? by maintaining a HashSet or arraylist where we store the 
    all the indices that paritucalr val is present. 

    now comming to the operatios. 
    1. Insert
       1. we need to check if the element is arleayd there if it then get the arraylist and 
          add the new found element index to the hashmap.
       2. If not, then create a new val in the hasmap and put it. 

    2. Remove
        If the element not present return false;
        a. First, get the arraylist of the numbers we need to remove. from the hashmap. get one out of the arraylist
        and rmove it from the hashmap. (we also need to check for the size).
        b. check if the index that we get is the last one. if it then just remove it.
        c. if not, swap the last index with the index we just found from the hashmap. then add the index we got from the
        hasmap to the lastval index arrraylist. and also remove the lastidx from the lastval hashmpa arraylist.
        d. remove the last val from the arraylist.
        e. once done check if the eleemnt we just removed size of the arraylist is zero if it then remove it from the
           the hashmap. 
        return true.
    */
    HashMap<Integer,ArrayList<Integer>> valToIndxes = new HashMap<>();
    ArrayList<Integer> vals = new ArrayList<>();
    
    public RandomizedCollection() {
        valToIndxes = new HashMap<>();
        vals = new ArrayList<>();
    }
    
    public boolean insert(int val) {
        //if contains then get the arraylist 
        //if not create one.
        boolean isNotPresent = true;
        ArrayList<Integer> indxVals = new ArrayList<>();
        if(valToIndxes.containsKey(val)){
            indxVals = valToIndxes.get(val);
            indxVals.add(vals.size());
            isNotPresent = false;
        }else{
            indxVals.add(vals.size());
            valToIndxes.put(val,indxVals);
        }
        vals.add(val);
        return isNotPresent;
    }
    
    public boolean remove(int val) {
        if(!valToIndxes.containsKey(val)) return false;
        int rmidx = valToIndxes.get(val).iterator().next();
        valToIndxes.get(val).remove((Object)rmidx);
        if(rmidx!=vals.size()-1){
            int lastval = vals.get(vals.size()-1);
            valToIndxes.get(lastval).remove((Object)(vals.size()-1));
            valToIndxes.get(lastval).add(rmidx);
            vals.set(rmidx,lastval);
        }
        if(valToIndxes.get(val).size()==0) valToIndxes.remove(val);
        vals.remove(vals.size()-1);
        return true;
    }
    
    public int getRandom() {
        Random r = new Random();
        int idx = r.nextInt(vals.size());
        return vals.get(idx);
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */