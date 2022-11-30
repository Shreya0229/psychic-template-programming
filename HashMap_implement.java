import java.io.*;
import java.util.*;

public class Main {

  public static class HashMap<K, V> {  
      
    private class HMNode {
      K key;
      V value;

      HMNode(K key, V value) {// constructor
        this.key = key;
        this.value = value;
      }
    }

    private int size; // n
    private LinkedList<HMNode>[] buckets; // N = buckets.length

    public HashMap() {
      initbuckets(4);
      size = 0;
    }

    public void put(K key, V value) throws Exception {   //  6-> 124  hashmap.put(6, 124) but 6->9 already present
    
      int bi = hashFunction(key);    //2 (after hashcode-> abs+modulus = 2 bucket index)
      int di = findInBucket(bi, key);

      if (di == -1) {
        HMNode node = new HMNode(key, value);
        buckets[bi].addLast(node);
        size++;
        
      } else {
        HMNode node = buckets[bi].get(di); // bucket[2].get(6)   == 9
        node.value = value;                // not increase the size as just replacing value
      }

      double lambda = size * 1.0 / buckets.length;
      if (lambda > 2.0) {
        rehash();
      }
    }

    public V get(K key) throws Exception {
      int bi = hashFunction(key);
      int di = findInBucket(bi, key);

      if (di == -1) {
        return null;
      } else {
        HMNode node = buckets[bi].get(di);
        return node.value;
      }
    }

    public boolean containsKey(K key) {
      int bi = hashFunction(key);
      int di = findInBucket(bi, key);

      if (di == -1) {
        return false;
      } else {
        return true;
      }
    }

    public V remove(K key) throws Exception {
      int bi = hashFunction(key);
      int di = findInBucket(bi, key);

      if (di == -1) {
        return null;
      } else {
        HMNode node = buckets[bi].remove(di);
        size--;
        return node.value;
      }
    }

    public ArrayList<K> keyset() throws Exception {
      ArrayList<K> set = new ArrayList<>();

      for (int bi = 0; bi < buckets.length; bi++) {
        for (HMNode node : buckets[bi]) {
          set.add(node.key);
        }
      }

      return set;
    }

    public int size() {
      return size;
    }

    public void display() {
      System.out.println("Display Begins");
      for (int bi = 0; bi < buckets.length; bi++) {
        System.out.print("Bucket" + bi + " ");
        for (HMNode node : buckets[bi]) {
          System.out.print( node.key + "@" + node.value + " ");
        }
        System.out.println(".");
      }
      System.out.println("Display Ends");
    }
    
    // returns bucket index for a key
    private int hashFunction(K key) {   // hashFunction(6)
      int hc = key.hashCode();                  //     (3276572463873284)%4 = [0, 1, 2, 3 ]   
      int bi = Math.abs(hc) % buckets.length;   //    = 2
      return bi;  // 2
    }

    // return data index for a bucket and key
    private int findInBucket(int bi, K key) {   // findInBucket(2, 6)
      int di = 0;
      for (HMNode node : buckets[bi]) {
        if (node.key.equals(key)) {
          return di;
        }
        di++;
      }

      return -1;
    }

    // when lambda crosses a threshold
    private void rehash() throws Exception {
      LinkedList<HMNode>[] oba = buckets;
      initbuckets(2 * oba.length);
      size = 0;

      for (int bi = 0; bi < oba.length; bi++) {
        for (HMNode onode : oba[bi]) {
          put(onode.key, onode.value);
        }
      }
    }

    private void initbuckets(int N) {   // initbuckets(4)
      buckets = new LinkedList[N];
      for (int bi = 0; bi < buckets.length; bi++) {
        buckets[bi] = new LinkedList<>();
      }
    }
  }
  
