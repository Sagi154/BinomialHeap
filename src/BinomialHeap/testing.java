package BinomialHeap;


public class testing {

    public static void testInsert(){
        System.out.println("---------Testing insert-----------");
        BinomialHeap heap = new BinomialHeap();
        PrintHeap.printHeap(heap, true);
        int key;
        heap.insert(5, "aa");
        key = 5;
        System.out.println("Heap after inserting key: "+ key +" \n");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap size after inserting key:" + key + "\n " + heap.size());
        heap.insert(8, "ab");
        key = 8;
        System.out.println("Heap after inserting key: "+ key +" \n");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap size after inserting key:" + key + "\n " + heap.size());
        heap.insert(4, "Ab");
        key = 4;
        System.out.println("Heap after inserting key: "+ key +" \n");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap size after inserting key:" + key + "\n " + heap.size());
        heap.insert(10, "Ab");
        key = 10;
        System.out.println("Heap after inserting key: "+ key +" \n");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap size after inserting key:" + key + "\n " + heap.size());
        heap.insert(15, "Ab");
        key = 15;
        System.out.println("Heap after inserting key: "+ key +" \n");
        PrintHeap.printHeap(heap, true);
        System.out.println("Heap size after inserting key:" + key + "\n " + heap.size());
//        PrintHeap.printHeap(heap, true);
    }
    public static void main (String[] args){
        testInsert();
    }
}
