package BinomialHeap;

import java.util.Random;


public class testing {

    public static void testInsert(int nodesCount){
        Random random = new Random();
        System.out.println("---------Testing insert-----------");
        BinomialHeap heap = new BinomialHeap();
        PrintHeap.printHeap(heap, true);
        int key;
       for (int i = 0; i < nodesCount; i++){
            key = random.nextInt(nodesCount);
           heap.insert(key, "aa");
           //System.out.println("----------heap after insert: -------------------");
           //PrintHeap.printHeap(heap, true);
        }
//        heap.insert(0, "aa");
//        heap.insert(2, "aa");
//        heap.insert(8, "aa");
//        heap.insert(0, "aa");
//          PrintHeap.printHeap(heap, true);
//        heap.insert(3, "aa");


        PrintHeap.printHeap(heap, true);
    }
    public static void main (String[] args){
        testInsert(4);
    }
}
