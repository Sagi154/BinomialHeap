package BinomialHeap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class testing {

    public static void FirstTheoreticalTest (){
        int nodesCount;
        BinomialHeap heap;
        for ( int i = 1; i <= 6; i++)
        {
            nodesCount = (int)Math.pow(3, i+5) - 1;
            heap = insertFrom1ToCount(nodesCount);
        }
    }

    public static BinomialHeap insertFrom1ToCount(int nodesCount) {
        BinomialHeap heap = new BinomialHeap();
        for (int i = 1; i <= nodesCount; i++) {
            heap.insert(i, "aa");
        }
        return heap;
    }

    public static BinomialHeap insertFromCountTo1(int nodesCount) {
        BinomialHeap heap = new BinomialHeap();
        for (int i = nodesCount; i >= 1; i--) {
            heap.insert(i, "aa");
        }
        return heap;
    }

    public static BinomialHeap insertRandomOrder1ToCount (int nodesCount) {
        Random random = new Random();
        BinomialHeap heap = new BinomialHeap();
        int key;
        Integer[] keysInserted = new Integer[nodesCount];
        for (int i = 0; i < nodesCount; i++) {
            keysInserted[i] = i + 1;
        }

        List<Integer> list = Arrays.asList(keysInserted);
        Collections.shuffle(list);
        list.toArray(keysInserted);

        for (int i = 0; i < nodesCount; i++) {
            key = keysInserted[i];
            heap.insert(key, "aa");
        }
        return heap;
    }

    public static void testInsert(int nodesCount){
        Random random = new Random();
        System.out.println("---------Testing insert-----------");
        BinomialHeap heap = new BinomialHeap();
        PrintHeap.printHeap(heap, true);
        int key;
//        for (int i = 0; i < nodesCount; i++){
//            key = random.nextInt(nodesCount);
//            heap.insert(key, "aa");
//            System.out.println("----------heap after insert: -------------------");
//            PrintHeap.printHeap(heap, true);
//        }
        heap.insert(0, "aa");
        heap.insert(2, "aa");
        heap.insert(8, "aa");
        heap.insert(0, "aa");
        PrintHeap.printHeap(heap, true);
        heap.insert(3, "aa");


        PrintHeap.printHeap(heap, true);
    }
    public static void main (String[] args){
//        testInsert(9);
        BinomialHeap heap = insertFrom1ToCount(10);
        PrintHeap.printHeap(heap, true);
    }

}
