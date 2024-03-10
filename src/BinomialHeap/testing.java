package BinomialHeap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import BinomialHeap.BinomialHeap.HeapItem;


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

    public static BinomialHeap testInsert(){
        System.out.println("---------Testing insert-----------");
        BinomialHeap heap = new BinomialHeap();
        HeapItem item839 = heap.insert(839, "aa");
        heap.insert(4209, "aa");
        heap.insert(1743, "aa");
        heap.insert(2424, "aa");
        heap.insert(1190, "aa");
        heap.insert(1647, "aa");
        heap.insert(3647, "aa");
        heap.insert(3830, "aa");
        heap.insert(647, "aa");
        heap.insert(2405, "aa");
        heap.insert(1871, "aa");
        heap.insert(2464, "aa");

        PrintHeap.printHeap(heap, true);
        heap.decreaseKey(item839, 193);
        PrintHeap.printHeap(heap, true);
        heap.delete(item839);
        PrintHeap.printHeap(heap, true);


        BinomialHeap heaps = new BinomialHeap();
        System.out.println("000000000000000000000000000000000 heaps 00000000000000000000000000000000");
        heaps.insert(647, "aa");
        heaps.insert(2405, "aa");
        heaps.insert(1871, "aa");
        heaps.insert(2464, "aa");
        PrintHeap.printHeap(heaps, true);

        //HeapGraph.draw(heaps);

        System.out.println("00000000000111111111111111111111 heapa 0000000000000111111111111111111111111");
        BinomialHeap heapa = new BinomialHeap();
        heapa.insert(1190, "aa");
        heapa.insert(1647, "aa");
        heapa.insert(3647, "aa");
        heapa.insert(3830, "aa");
        heapa.insert(1743, "aa");
        heapa.insert(2424, "aa");
        heapa.insert(4209, "aa");
        PrintHeap.printHeap(heapa, true);
        HeapGraph.draw(heapa);





        return heap;
    }
    public static void main (String[] args){
//        testInsert(9);
//        BinomialHeap heap = insertRandomOrder1ToCount(20);
        BinomialHeap heap = testInsert();
//        PrintHeap.printHeap(heap, true);
        HeapGraph.draw(heap);
//        PrintHeap.printHeap(heap, true);
    }

}
