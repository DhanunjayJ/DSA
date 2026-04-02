class FreqStack {
    /*
    push operation increases the frequency of the element. 
    when ever the push then we need to change the frequency.
    to do this we use a hashmap of the ele and stack of the elements with the same freqency
    when ever a pop happens it will remove from the top of the max frequency stack.
    but how can we hadnle the case of the element that is at the end of the stack.
    it is asked to push then we need to remove it fromt he currentfrequency stack??
    should we keep it?? 
    I think keeping it at the same place make sense  
    
    Now how can we know in which frequency the current element is in?? 

    to know that in o(1) time complexiy we need another hashmap that maps ele to the

    frequency. so that we ge tthe frequency in o(1). and incermet it when we need to add.


    Final Data structure.

    to push, first we have to check if the element is there in the hasmap.
    if it there then get it's frequency and incement it. and if the
    new frequncy stack is there then push it or else we need to create one.

    and push it to the stack put in the both hashmap.

    pop()
    for pop to work, we maintain a varibale called maxfreq which will store the max frequcney and atuomatically decremnts when the stack of the maxfreq is empty.
    and go the next frewucney. do it untill freq of the stack maxfreq is 0 or empty 
    in the stack.
    since given in the questoin that there will be atleast one ele in the stack before calling pop we don't need to hadnle that case. 
    */

    HashMap<Integer,Deque<Integer>> freqToStack;
    HashMap<Integer,Integer> eleToFreq;
    int maxFreq = 1;

    public FreqStack() {
        freqToStack = new HashMap<>();
        eleToFreq = new HashMap<>();
    }
    
    public void push(int val) {
        //Check if the val is present 
        int freq = 1;
        if(eleToFreq.containsKey(val)){
            freq = eleToFreq.get(val);
            freq++;
        }
        //update the maxfreq
        maxFreq = Math.max(freq,maxFreq);
        // if it is not there create one and push it
        Deque<Integer> st = freqToStack.getOrDefault(freq,new ArrayDeque<>());
        st.push(val);
        //update the two hashmap
        eleToFreq.put(val,freq);
        freqToStack.put(freq,st);
    }
    
    public int pop() {
        //first get the maxfreq stack
        //pop the element.
        // if the stack is empty remove the stack and reduce the frequency.
        // since in the questoin it is given that while calling pop there will be
        //atleast one element we don't need to handle the case of the no element when
        // pop is called.
        Deque<Integer> st = freqToStack.get(maxFreq);
        int res = st.pop();
        //reduce the frequency of the element when poping()
        eleToFreq.put(res,maxFreq-1);
        if(st.isEmpty()){
            freqToStack.remove(maxFreq);
            maxFreq--;
        }
        return res;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */