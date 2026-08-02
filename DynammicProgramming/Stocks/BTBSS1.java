class Solution {

    //greedy way
    public int maxProfit(int[] prices) {
        /*
        We keep updating the max profit for every day.
        when the min goes down than the previous ones
        then we alos update the max, since we can't sell
        in the past. if the max is at the past.
        */
        int min = 10001;
        int max = 0;
        int maxProfit = 0;
        for(int price : prices){
            max = Math.max(price,max);
            if(price<min){
                min = price;
                max = min;
            }
            maxProfit = Math.max(maxProfit,max-min);
        }
        return maxProfit;
    }
}

