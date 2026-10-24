class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        
        int aliceA = getDaysPassed(Integer.parseInt(arriveAlice.substring(0, 2)),
                Integer.parseInt(arriveAlice.substring(3, 5)));
        int aliceL = getDaysPassed(Integer.parseInt(leaveAlice.substring(0, 2)), Integer.parseInt(leaveAlice.substring(3, 5)));
        int bobA = getDaysPassed(Integer.parseInt(arriveBob.substring(0, 2)),
                Integer.parseInt(arriveBob.substring(3, 5)));
        int bobL = getDaysPassed(Integer.parseInt(leaveBob.substring(0, 2)),
                Integer.parseInt(leaveBob.substring(3, 5)));

        int startOverlap = Math.max(aliceA,bobA);
        int endOverlap = Math.min(aliceL,bobL);

        if(startOverlap<=endOverlap){
            return endOverlap-startOverlap+1;
        }
        return 0;
    }

    public int getDaysPassed(int month, int day) {
        int[] monthDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int days = 0;
        for(int i=0;i<month;i++){
            days += monthDays[i]; 
        }
        days += day;
        return days;
    }
}