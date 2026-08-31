import java.util.Scanner;

public class PrintingMaximumSubArray {
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int [] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }

        //find the maximum subarray sum (print the acutal subarray)

        int currSum = arr[0]; 
        int maxSum = arr[0];

        int start = 0;
        int end = 0;
        int tempStart = 0;

        for(int i=1;i<n;i++){

            if(arr[i]>currSum+arr[i]){
                //update the temp Start when starting new subarray 
                tempStart = i;
                currSum = arr[i];
            }else{
                currSum += arr[i];
            }

            if(currSum>maxSum){
                //when ever the currSum is greater updat ehte start with the temp value
                //end will be the current value. 
                maxSum = currSum;
                start = tempStart;
                end = i;
            }
        }

        for(int i=start;i<=end;i++){
            System.out.print(arr[i]+" ");
        }

    }
}
