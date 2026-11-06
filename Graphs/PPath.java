import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class PPath {
    static boolean[] isPrime = new boolean[10000];

    public static void sieve(){
        Arrays.fill(isPrime,true);
        isPrime[0] = isPrime[1] = false;
        for(int i=2;i*i<10000;i++){
            if(isPrime[i]){
                for(int j=i*i;j<10000;j+=i){
                    isPrime[j] = false;
                }
            }
        }
    }

    public static void main (String [] args){
        sieve();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        while(n-- >0 ){

        int start = sc.nextInt();
        int target = sc.nextInt();

        if(start==target){
            System.out.println(0);
            continue;
        }

        int [] dist = new int[10000];
        Arrays.fill(dist,-1);

        boolean reached = false;

        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        dist[start] = 0;

        while(!q.isEmpty()){
            int rem = q.remove();

            if(rem==target){
                reached = true;
                break;
            }

            char [] chars = String.valueOf(rem).toCharArray();

            for(int i=0;i<4;i++){
                char oc = chars[i];
                char startDigit = i==0 ? '1' : '0';
                for(char c=startDigit;c<='9';c++){
                    if(c==oc) continue;
                    chars[i] = c;

                    int nextVal = ((chars[0]-'0')*1000) + ((chars[1]-'0')*100) + ((chars[2]-'0')*10) + (chars[3]-'0');
                    //if the next val is a prime and not alreayd visited.
                    if(isPrime[nextVal] && dist[nextVal]==-1){
                        dist[nextVal] = dist[rem]+1;
                        q.add(nextVal);
                    }
                }
                chars[i] = oc;
            }
        }
        
        if(reached) System.out.println(dist[target]);
        else System.out.println("Impossible");
        }
    }
}