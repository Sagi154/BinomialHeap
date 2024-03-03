package BinomialHeap;


public class testing {

    public static void testInsert(){
        BinomialHeap heap = new BinomialHeap();
        PrintHeap.printHeap(heap, true);
        heap.insert(5, "aa");
        PrintHeap.printHeap(heap, true);
    }
    public static void main (String[] args){
        testInsert();
    }
}
