import java.io.*;
import java.util.*;

public class Main {
   public static class HashMap<K,V> {
        
        private class HMNode{
            K key;
            V value;
            
            HMNode(K key,V value){
                this.key = key;
                this.value = value;
            }
        }
        
        private int size = 0;
        private LinkedList<HMNode>[] buckets;
        
        HashMap(){
            initbuckets(4);
            size = 0;
        }
        
        public void initbuckets(int N){
            //assign the size to the array
            // creating an array of type linkedlist.
            buckets = new LinkedList[N];
            for(int i=0;i<N;i++){
                buckets[i] = new LinkedList<>();
            }
            //earch index is a linkedlist here
        }
        
        public int hashFunction(K key) throws Exception {
            int hc = key.hashCode();
            int bi = Math.abs(hc)%buckets.length;
            return bi;
        }
        
        public int findInBucket(int bi,K key){
            int di = 0;
            for(HMNode node: buckets[bi]){
                if(node.key.equals(key)){
                    return di;
                }
                di++;
            }
            return -1;
        }
        
        public void rehash()throws Exception{
            LinkedList<HMNode>[] obs = buckets;
            initbuckets(obs.length*2);
            size = 0;
            for(int i=0;i<obs.length;i++){
                for(HMNode node:obs[i]){
                    put(node.key,node.value);
                }
            }
        }
        
        public void put(K key,V value) throws Exception{
            int bi = hashFunction(key);
            int di = findInBucket(bi,key);
            
            if(di==-1){
                HMNode node = new HMNode(key,value);
                buckets[bi].addLast(node);
                size++;
            }else{
                HMNode node = buckets[bi].get(di);
                node.value = value;
            }
            
            double lambda = size * 1.0 / buckets.length;
            if(lambda>2.0){rehash();}
        }
        
        public V get(K key) throws Exception {
            int  bi = hashFunction(key);
            int di = findInBucket(bi,key);
            if(di==-1){
                return null;
            }else{
                return buckets[bi].get(di).value;
            }
        }
        
        public boolean containsKey(K key) throws Exception {
            int bi = hashFunction(key);
            int di = findInBucket(bi,key);
            if(di==-1){
                return false;
            }else{
                return true;
            }
        }
        
        public ArrayList<K> keyset() throws Exception {
            ArrayList<K> set = new ArrayList<>();
            for(int i=0;i<buckets.length;i++)
            {
                for(HMNode node:buckets[i]){
                    set.add(node.key);
                }
            }
            return set;
        }
        
        public V remove (K key) throws Exception {
            int bi = hashFunction(key);
            int di = findInBucket(bi,key);
            
            if(di==-1){
                return null;
            }else{
                HMNode node = buckets[bi].remove(di);
                size--;
                return node.value;
            }
        }
        
        public int size() {
            return size;
        }
        
        public void display () throws Exception {
            System.out.println("display starts here");
            for(int i=0;i<buckets.length;i++){
                System.out.println("In bucket"+ i);
                for(HMNode node : buckets[i]){
                    System.out.println("key "+node.key+ "@" + "Value :" + node.value);
                }
                System.out.println(".");
            }
            System.out.println("Display ends here");
        }
        
    }
    
public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    HashMap<String, Integer> map = new HashMap();

    String str = br.readLine();
    while (str != null && str.equals("quit") == false) {
      if (str.startsWith("put")) {
        String[] parts = str.split(" ");
        String key = parts[1];
        Integer val = Integer.parseInt(parts[2]);
        map.put(key, val);
      } else if (str.startsWith("get")) {
        String[] parts = str.split(" ");
        String key = parts[1];
        System.out.println(map.get(key));
      } else if (str.startsWith("containsKey")) {
        String[] parts = str.split(" ");
        String key = parts[1];
        System.out.println(map.containsKey(key));
      } else if (str.startsWith("remove")) {
        String[] parts = str.split(" ");
        String key = parts[1];
        System.out.println(map.remove(key));
      } else if (str.startsWith("size")) {
        System.out.println(map.size());
      } else if (str.startsWith("keyset")) {
        System.out.println(map.keyset());
      } else if (str.startsWith("display")) {
        map.display();
      }
      str = br.readLine();
    }
  }
}