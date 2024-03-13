package BinomialHeap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import BinomialHeap.BinomialHeap.HeapItem;


public class testing {

    public static void FirstTheoreticalTest (int testCount){
        int nodesCount;
        BinomialHeap heap;
        System.out.println("-------------First Theoretical test results----------------");
        long sumOfElapsedTime = 0;
        int sumOfCountLinks = 0;
        int sumOfNumOfTrees = 0;
        for ( int i = 1; i <= 6; i++)
        {
            System.out.println("      --------- i = "+ i + " -------       " );
            nodesCount = (int)Math.pow(3, i+5) - 1;
            for (int j = 1; j <= testCount; j++){
                long start = System.currentTimeMillis();
                heap = insertFrom1ToCount(nodesCount);
                long elapsedTimeMillis = System.currentTimeMillis()-start;
                sumOfElapsedTime += elapsedTimeMillis;
                sumOfCountLinks += heap.countLinks;
                sumOfNumOfTrees += heap.numOfTrees;
            }
            System.out.println("Total runtime: " + (sumOfElapsedTime/testCount));
            System.out.println("Total links: " + (sumOfCountLinks/testCount));
            System.out.println("Number of trees in the end: " + (sumOfNumOfTrees/testCount));
            sumOfElapsedTime = 0;
            sumOfCountLinks = 0;
            sumOfNumOfTrees = 0;
        }
    }

    public static void secondTheoreticalTest (int testCount){
        int nodesCount;
        System.out.println("-------------Second Theoretical test results----------------");
        long sumOfElapsedTime = 0;
        int sumOfCountLinks = 0;
        int sumOfNumOfTrees = 0;
        int sumOfSumOfRanks = 0;
        for ( int i = 1; i <= 6; i++)
        {

            System.out.println("      --------- i = "+ i + " -------       " );
            nodesCount = (int)Math.pow(3, i+5) - 1;

            for (int j = 1; j <= testCount; j++){
                long start = System.currentTimeMillis();
                BinomialHeap heap = insertRandomOrder1ToCount(nodesCount);
                deleteMinNTimes(heap, nodesCount / 2 );
                long elapsedTimeMillis = System.currentTimeMillis()-start;
                sumOfElapsedTime += elapsedTimeMillis;
                sumOfCountLinks += heap.countLinks;
                sumOfNumOfTrees += heap.numOfTrees;
                sumOfSumOfRanks += heap.sumOfRanks;
            }
            System.out.println("Total runtime: " + (sumOfElapsedTime/testCount));
            System.out.println("Total links: " + (sumOfCountLinks/testCount));
            System.out.println("Number of trees in the end: " + (sumOfNumOfTrees/testCount));
            System.out.println("Sum of deleted nodes' rank: " + (sumOfSumOfRanks/testCount));
            sumOfElapsedTime = 0;
            sumOfCountLinks = 0;
            sumOfNumOfTrees = 0;
            sumOfSumOfRanks = 0;
        }
    }

    public static void thirdTheoreticalTest (int testCount){
        int nodesCount;
        BinomialHeap heap;
        long sumOfElapsedTime = 0;
        int sumOfCountLinks = 0;
        int sumOfNumOfTrees = 0;
        int sumOfSumOfRanks = 0;
        System.out.println("-------------Third Theoretical test results----------------");
        for ( int i = 1; i <= 6; i++)
        {
            System.out.println("      --------- i = "+ i + " -------       " );
            nodesCount = (int)Math.pow(3, i+5) - 1;
            int deletesCount = nodesCount - (int)(Math.pow(2, 5) - 1);


            for (int j = 1; j <= testCount; j++){
                long start = System.currentTimeMillis();
                heap = insertFromCountTo1(nodesCount);
                deleteMinNTimes(heap, deletesCount);
                long elapsedTimeMillis = System.currentTimeMillis()-start;
                sumOfElapsedTime += elapsedTimeMillis;
                sumOfCountLinks += heap.countLinks;
                sumOfNumOfTrees += heap.numOfTrees;
                sumOfSumOfRanks += heap.sumOfRanks;
            }
            System.out.println("Total runtime: " + (sumOfElapsedTime/testCount));
            System.out.println("Total links: " + (sumOfCountLinks/testCount));
            System.out.println("Number of trees in the end: " + (sumOfNumOfTrees/testCount));
            System.out.println("Sum of deleted nodes' rank: " + (sumOfSumOfRanks/testCount));
            sumOfElapsedTime = 0;
            sumOfCountLinks = 0;
            sumOfNumOfTrees = 0;
            sumOfSumOfRanks = 0;
        }
    }

    public static void runTests(){
        int testCount = 10;
        FirstTheoreticalTest(testCount);
        secondTheoreticalTest(testCount);
        thirdTheoreticalTest(testCount);
    }

    public static void deleteMinNTimes (BinomialHeap heap, int deleteCount){
        for (int i = 1 ; i <= deleteCount ; i++) {
            heap.deleteMin();
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

    public static BinomialHeap insertRandomOrderRandomKeys (int size, int range) {
        Random random = new Random();
        BinomialHeap heap = new BinomialHeap();
        int key;
        Integer[] keysInserted = new Integer[size];
        for (int i = 0; i < size; i++) {
            keysInserted[i] = random.nextInt(1, range + 1);
        }

        List<Integer> list = Arrays.asList(keysInserted);
        Collections.shuffle(list);
        list.toArray(keysInserted);

        for (int i = 0; i < size; i++) {
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
//        heap.decreaseKey(item839, 193);
        PrintHeap.printHeap(heap, true);
        heap.delete(item839);
        PrintHeap.printHeap(heap, true);


//        BinomialHeap heaps = new BinomialHeap();
//        System.out.println("000000000000000000000000000000000 heaps 00000000000000000000000000000000");
//        heaps.insert(647, "aa");
//        heaps.insert(2405, "aa");
//        heaps.insert(1871, "aa");
//        heaps.insert(2464, "aa");
//        PrintHeap.printHeap(heaps, true);
//
//        //HeapGraph.draw(heaps);
//
//        System.out.println("00000000000111111111111111111111 heapa 0000000000000111111111111111111111111");
//        BinomialHeap heapa = new BinomialHeap();
//        heapa.insert(1190, "aa");
//        heapa.insert(1647, "aa");
//        heapa.insert(3647, "aa");
//        heapa.insert(3830, "aa");
//        heapa.insert(1743, "aa");
//        heapa.insert(2424, "aa");
//        heapa.insert(4209, "aa");
//        PrintHeap.printHeap(heapa, true);
//        HeapGraph.draw(heapa);





        return heap;
    }


    public static void testCreateHeapFromNodeChildren (){
        // First we create a binomial tree
        BinomialHeap heap = insertRandomOrder1ToCount(1);
        System.out.println("The heap we're testing looks like: ");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap last is: \n" + heap.getLast().getItem());
        System.out.println("Heap min is: \n" + heap.getMin().getItem());
        BinomialHeap heapnew = BinomialHeap.createHeapFromNodeChildren(heap.getLast());
        System.out.println("The heap we're getting looks like: ");
        PrintHeap.printHeap(heapnew, true);
//        HeapGraph.draw(heapnew);
    }

    public static void testMeld(){
        int size2 = 5;
        int size1 = 2;
        BinomialHeap heap = insertRandomOrderRandomKeys(size1, size1 * 10);
        PrintHeap.printHeap(heap, true);
        BinomialHeap heap2 = insertRandomOrderRandomKeys(size2, size2 * 10);
        PrintHeap.printHeap(heap2, true);
        heap.meld(heap2);
        HeapGraph.draw(heap);
    }

    public static void testMeldSpecialCase(){
        BinomialHeap heap1 = new BinomialHeap();
        heap1.insert(306, "aa");
        heap1.insert(2042, "aa");
        heap1.insert(596, "aa");
        PrintHeap.printHeap(heap1, true);

        BinomialHeap heap2 = new BinomialHeap();
        heap2.insert(41, "aa");
        heap2.insert(3143, "aa");
        heap2.insert(146, "aa");
        heap2.insert(1195, "aa");
        heap2.insert(51, "aa");
        heap2.insert(623, "aa");
        heap2.insert(277, "aa");
        heap2.insert(2773, "aa");
        heap2.insert(1529, "aa");

        System.out.println("000000000000000000000000000000000 heap1 00000000000000000000000000000000");
        PrintHeap.printHeap(heap1, true);
//        HeapGraph.draw(heap1);
        System.out.println("000000000000000000000000000000000 heap2 00000000000000000000000000000000");
        PrintHeap.printHeap(heap2, true);
//        HeapGraph.draw(heap2);
        heap1.meld(heap2);
        ///HeapGraph.draw(heap2);
    }



    public static void testNumOfTrees(){

        int treesNum1 = 5;
        int size1 = (int)Math.pow(2, treesNum1) - 1;
        BinomialHeap heap = insertRandomOrderRandomKeys(size1, size1 * 10);
        int treesNum2 = 3;
        int size2 = (int)Math.pow(2, treesNum2) - 1;
        BinomialHeap heap2 = insertRandomOrderRandomKeys(size2, size2 * 10);
        System.out.println("---------- Heap in test number of trees ---------");
        PrintHeap.printHeap(heap, true);


        HeapGraph.draw(heap);

        System.out.println("---------- Heap2 in test number of trees ---------");
        PrintHeap.printHeap(heap2, true);
        HeapGraph.draw(heap2);

        System.out.println("Number of trees in heap1 is: \n" + heap.numOfTrees);
        System.out.println("Number of trees in heap2 is: \n" + heap2.numOfTrees);
//        heap.meld(heap2);
        System.out.println("Number of trees in heap1 after meld is: \n" + heap.numOfTrees);
    }
    public static void main (String[] args){
//        testNumOfTrees();
//        testMeldSpecialCase();
        runTests();
    }

}
