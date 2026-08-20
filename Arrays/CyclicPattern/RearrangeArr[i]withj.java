//https://www.geeksforgeeks.org/dsa/rearrange-array-arrj-becomes-arri-j/

// Java Code to Rearrange Array Elements
// using Cycle Replacement
import java.util.*;

class Main {

    // Rearrange elements in a cycle 
    // starting at arr[i]
    static void rearrangeUtil(int[] arr, int i) {
        
        int n = arr.length;
        
        // get the value
        int val = -(i + 1);
	//get the index where we need to store the value.
        i = arr[i] - 1;

        while (arr[i] > 0) {
	    //before storing the value store the next index to a variable
            int next = arr[i] - 1;

            // Go ahead with the replacement of the value. 
            arr[i] = val;
         
            //and again start to get the value to store for the next cycle of data.
            val = -(i + 1);
          System.out.println("inside val"+val);

            i = next;
          System.out.println("inside i"+i);
        }
    }

    // Rearrange arr[] so that arr[j] 
    // becomes i if arr[i] is j
    static void rearrange(int[] arr) {

        int n = arr.length;

        // Increment all values
	//Since the array will have multiple cycles we need to know
	//if the values are visited or not for that we make them negative
	//if we make negative, then zero can't be negative. so we increment
	//the values by one. 
        for (int i = 0; i < n; i++) {  
            arr[i]++;
        }

        // Process cycle
        for (int i = 0; i < n; i++) {

            if (arr[i] > 0) {  
              System.out.println("start of cycle "+i);
                rearrangeUtil(arr, i);
            }
        }

        // Restore original range by subtraching one and removing negative.
        for (int i = 0; i < n; i++) {  
            arr[i] = (-arr[i]) - 1;
        }
    }

    static void printArray(int[] arr) {

        for (int num : arr) {  
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int[] arr = {2, 0, 1, 4, 5, 3};

        rearrange(arr); 
        
        printArray(arr);   
    }
}

// doing the same using the / and %

// Java Code to Rearrange Array Elements
import java.util.Arrays;

public class Main {

    // Rearrange arr[] so that arr[j] becomes i if arr[i] is j
    public static void rearrange(int[] arr) {

        int n = arr.length;

        // Store new values using modulo
        for (int i = 0; i < n; i++) {
            arr[arr[i] % n] += i * n;
        }

        // Extract new values
        for (int i = 0; i < n; i++) {
            arr[i] /= n;
        }
    }

    public static void main(String[] args) {

        int[] arr = {2, 0, 1, 4, 5, 3};

        rearrange(arr);

        System.out.println(Arrays.toString(arr));
    }
}