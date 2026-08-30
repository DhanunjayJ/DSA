class PermutationSequence {
    public String getPermutation(int n, int k) {
        /*
        we fix one position and find all the possible permutations 
        of the other values and fix that postion and then next untill not 
        value remains.
        */
        List<Integer> numbers = new ArrayList<>();
        //to get the factorial of each number in o(1) we precompute and
        //store them.
        int [] factorial = new int[n+1];
        factorial[0] = 1;
        for(int i=1;i<=n;i++){
            factorial[i] = factorial[i-1]*i;
        }
        for(int i=1;i<=n;i++){
            numbers.add(i);
        }
        //Subtracting 1 ensures that division (k / block_size) always
        //maps to the exact correct 0-based index in your list of
        //available numbers, avoiding off-by-one errors at boundaries.
        k--;
        StringBuilder st = new StringBuilder();
        for(int i=n;i>0;i--){
            //by fixiin one index we left with i-1 eleetns
            //to check where does that kth perfumation falls in
            //we do k/(i-1)! because each i-1 values create (i-1)! facotrial values.
            int index = k/factorial[i-1];
            st.append(numbers.get(index));
            numbers.remove(index);
            //now the remaining steps will be performed with other
            //remaining elements
            k = k%factorial[i-1];
        }
        return st.toString();
    }
}