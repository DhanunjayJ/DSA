package greedy;

public class LemonadeChange {
    class Solution {
    public boolean lemonadeChange(int[] bills) {
        /*
        Maintain the count of the change for 5,10 and 20 
        when user pays 5 take it and updates the count.
        when cus pays 10 update the count of 10 and check if 5 count > 0
        if it then decrese one 5
        when cus pays 20 update the count of 20 check 
        for these two cases
        1. one 10 and one 5
        or 
        2. 3 fives are there
        if is the decremne those nuebrs accrdingly and return true;
        if the above are false then return false; 
        */
        int fives = 0;
        int tens = 0;
        for(int bill: bills){
            if(bill == 5){
                fives++;
            }else if(bill==10){
                if(fives>=1){
                tens++;
                    fives--;
                }else{
                    return false;
                }
            }else if(bill == 20){
                /*
                the greedy choice : The 5 bill is more valuable as change than the 10 bill.
                A 5 can help make change for both a 10 and a 20. A 10 can only help make change for a 20.
                Therefore, when someone hands you a 20, the greedy choice is to give away your 10 first (saving your flexible 5s for later).
                */
                if(tens>=1 && fives>=1){
                    tens--;
                    fives--;
                }else if(fives>=3){
                    fives-=3;
                }else{
                    return false;
                }
            }
        }
        return true;
    }
}
}
