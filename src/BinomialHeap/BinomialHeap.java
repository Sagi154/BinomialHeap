package BinomialHeap;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap{

	public int size = 0;
	public HeapNode last;
	public HeapNode min;

	public BinomialHeap() {	}

	public BinomialHeap(int size, HeapNode last) {
		this.size = size;
		this.last = last;
	}

	public BinomialHeap(HeapNode last, HeapNode min) {
		this.last = last;
		this.min = min;
		this.size = 1;
	}

	public HeapNode getLast() { return last; }

	public HeapNode getMin(){ return this.min; }

	public void setSize(int size) {
		this.size = size;
	}

	public void setLast(HeapNode last) {
		this.last = last;
	}

	public void setMin(HeapNode newMin) {
		this.min = newMin;
	}

	public HeapItem makeNewItem(int key, String info){
		HeapItem item = new HeapItem(key, info);
		HeapNode node = new HeapNode(item);
//		node.setItem(item);
		item.setNode(node);
		return item;
	}

	/**
	 *
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info)
	{
		HeapItem item = this.makeNewItem(key, info);
		System.out.println("------------------About to insert:" + item + "---------------------");
		BinomialHeap heap2 = new BinomialHeap(item.getNode(), item.getNode());
		System.out.println("last is:" + heap2.getLast().getItem());
		System.out.println("min is:" + heap2.getMin().getItem());
		this.meld(heap2);
		return item;
	}

	public BinomialHeap createHeapFromNodeChildren(HeapNode node){
		int rank = node.getRank();
		int size = (int)Math.pow(2, rank);
		HeapNode last = node.getChild();
		BinomialHeap newHeap = new BinomialHeap(size, last);
		newHeap.findNewMin();
		return newHeap;
	}

	public void disconnectNodeFromHeap (HeapNode node){
		// handles case where node is the only node in heap
		if (this.last.getNext().getRank() == this.last.getRank()){
			this.size = 0;
			this.last = null;
			this.min = null;
			return;
		}
		HeapNode nodeNext = node.getNext();
		// First, we update the size of the heap
		int sizeDec = (int)Math.pow(2, node.getRank());
		this.setSize(this.size - sizeDec);
		// Second, we find HeapNode pointing to the node we disconnect
		HeapNode nodePrev = node.getNext();
		while (nodePrev.getNext().getRank() != node.getRank()) {
			nodePrev = nodePrev.getNext();
		}
		// Third, we update the last pointer of the heap and skip the node.
		if (this.last.getRank() == node.getRank())
			this.setLast(nodePrev);
		nodePrev.setNext(nodeNext);
		node.setNext(node);
		// Finally, we update the min of the heap.
		this.findNewMin();
	}

	/**
	 *
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		if (this.size == 0){
			return ;
		}
		HeapNode deletedMin = this.min;
		// First, we disconnect the deleted node from the heap.
		this.disconnectNodeFromHeap(deletedMin);
		// Second, we create a new heap from the deleted node's children.
		BinomialHeap heap2 = this.createHeapFromNodeChildren(deletedMin);
		// Finally, we meld the 2 heaps together.
		this.meld(heap2);
	}

	/**
	 *
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin() { return this.min.getItem(); }

	public void siftUp(HeapItem item){
		HeapNode currNode = item.getNode();
		HeapNode parent = currNode.getParent();
		HeapItem parentItem;
		while (parent != null){
			parentItem = parent.getItem();
			if (item.getKey() >= parentItem.getKey()){
				break;
			}
			// If reached here, we need to switch the items.
			currNode.setItem(parentItem);
			parentItem.setNode(currNode);
			parent.setItem(item);
			item.setNode(parent);
			// Made switch, now we need to advance parent
			currNode = parent;
			parent = currNode.getParent();
		}
	}

	/**
	 *
	 * pre: 0 < diff < item.key
	 *
	 * Decrease the key of item by diff and fix the heap.
	 *
	 */

	public void decreaseKey(HeapItem item, int diff)
	{
		// First, we update the key of item to the new key.
		item.setKey(item.getKey() - diff);
		// Second, we fix the tree by sifting up if needed.
		this.siftUp(item);
		// Last, we update the min if needed.
		if (item.getKey() < this.getMin().getItem().getKey()){
			this.setMin(item.getNode());
		}
	}

	/**
	 *
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item)
	{
		// First, we decrease the key, so it becomes the minimum.
		int diff = item.getKey() - this.getMin().getItem().getKey() + 1;
		this.decreaseKey(item, diff);
		// Then, we use deleteMin.
		this.deleteMin();
	}

	/**
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		int thisHeapSize = this.size;
		int heap2HeapSize= heap2.size();
		System.out.println("------------------About to meld----------------");
		System.out.println("This heap look like: \n ");
		PrintHeap.printHeap(this, true);
		System.out.println("Heap2 looks like: \n ");
		PrintHeap.printHeap(heap2, true);
		if (!heap2.empty()){
			if (this.empty()){ 					// if the heap is empty we replace it with heap2
				this.setMin(heap2.getMin());
				this.setLast(heap2.getLast());
				this.setSize(heap2.size());

				heap2.setLast(null);
				heap2.setMin(null);
			}
			else {								// if both heaps aren't empty
				HeapNode heap1Prev = this.getLast();
				HeapNode heap2Last = heap2.getLast();

				HeapNode heap1First = heap1Prev.getNext();
				HeapNode heap1Pointer = heap1First;

				int heap1StartingNumOfTrees = this.numTrees();

				int counter = 0;

				HeapNode heap2Pointer = heap2Last.getNext();

				while (counter <= heap1StartingNumOfTrees && (heap2Pointer != null)) {
					// the loop will run as long as we still have trees in both of the heaps that we haven't visited

					HeapNode heap2NextPointer = heap2Pointer.getNext();
					if (heap2Pointer == heap2NextPointer) {
						// check if heap2Pointer pointing the last tree in heap 2 we didn't connect yet

						heap2NextPointer = null;
					}

					if (heap1Pointer.getRank() == heap2Pointer.getRank()) {
						// check if we can connect the trees

						heap2Last.setNext(heap2NextPointer);
						HeapNode heap1_futureNext = heap1Pointer.getNext();

						heap1Pointer = compareHeapNodesAndLink(heap1Pointer, heap2Pointer); //link the trees

						if (heap1Prev != heap1Pointer.getChild()){
							// there were more than 1 node in this heap before the linking
							heap1Prev.setNext(heap1Pointer);
							heap1Pointer.setNext(heap1_futureNext);
						}
						else{
							heap1Prev = heap1Pointer;
							heap1Pointer.setNext(heap1Pointer);
						}

						if (this.getLast().getRank() < heap1Pointer.getRank()){
							// update the last's pointer in this heap if it changed
							this.setLast(heap1Pointer);
						}

						System.out.println("parent: " + heap1Pointer.getItem().getKey() + "\n child: " + heap1Pointer.getChild().getItem().getKey());


						heap2Pointer = heap2NextPointer;

						while (heap1Pointer.getRank() == heap1Pointer.getNext().getRank() && heap1Pointer != heap1Pointer.getNext()){

							// the loop run as long as there are 2 different trees in our heap (this) that have the same rank

							HeapNode heap1_future_next_pointer = heap1Pointer.getNext().getNext();
							heap1Pointer = compareHeapNodesAndLink(heap1Pointer, heap1Pointer.getNext());

							if (heap1Prev == heap1Pointer.getChild()){
								// special case 1 - we had only 2 trees in this heap before we linked them and heap1Pointer hasn't changed:
								// we need to update heap1Prev and the next of heap1Pointer (they pointed to a child)

								heap1Prev = heap1Pointer;
								heap1Pointer.setNext(heap1Pointer);
							}
							else if (heap1Prev == heap1Pointer) {
								// special case 2 - we had only 2 trees in this heap before we linked them and heap1Pointer has been changed:
								// we need to update the next of heap1Pointer (it pointed to a child)
								heap1Pointer.setNext(heap1Pointer);
							}
							else { //(heap1Prev != heap1Pointer.getChild() && heap1Prev != heap1Pointer)
								// there were more than 2 trees before the linking (it doesn't matter if heap1Pointer changed)
								heap1Prev.setNext(heap1Pointer);
								heap1Pointer.setNext(heap1_future_next_pointer);
							}


							//if (heap1Pointer.getNext() == heap1Pointer.getChild()){
							// 2 optional cases:
							// 1. heap1Pointer hasn't changed: we need to update his next (is former one is now his child)
							// 2. heap1Pointer has been changed, but we had only 2 trees in this heap before we linked them
							//	if (heap1_future_next_pointer != heap1Pointer.getChild()){
							// there were more than 2 trees before the linking: we are in case 1
							//		heap1Pointer.setNext(heap1_future_next_pointer);
							//	}
							//	else {
							// there were only 2 trees before the linking
							//		heap1Pointer.setNext(heap1Pointer);
							//	}

							//	}
							//	else{
							//		heap1Pointer.setNext(heap1_future_next_pointer);
							//	}

							//if (heap1Prev.getNext() == heap1Pointer.getChild()){
							// heap1Pointer has been changed, and now we need to update heap1Prev's
							// next (former heap1Pointer is now a child )
							//		heap1Prev.setNext(heap1Pointer);
							//	}

							if (this.getLast().getRank() < heap1Pointer.getRank()){
								// update the last's pointer in this heap if it changed
								this.setLast(heap1Pointer);
							}
							counter++;
						}
					}

					else if (heap1Pointer.getRank() > heap2Pointer.getRank()) {
						// we can't link this tree of heap 2, so we need to connect it before this tree of this heap
						heap1Prev.setNext(heap2Pointer);
						heap2Pointer.setNext(heap1Pointer);
						heap2Pointer = heap2NextPointer;

						if (heap2Pointer != null){
							// check if we finished to meld
							heap2Last.setNext(heap2NextPointer);
						}
					}

					else {
						// we can't link this tree of heap 2 to this tree of this heap, so we need to check with a bigger
						// rank tree in this heap
						heap1Prev = heap1Pointer;
						heap1Pointer = heap1Pointer.getNext();
						counter++;
					}
				}
				if(heap2Pointer != null){
					// we reached the biggest rank of this heap (including if it formed after the linking with some of heap2 trees),
					// but we still have a tree in heap2 we didn't connect to this heap
					heap2.getLast().setNext(heap1First);
					this.getLast().setNext(heap2Pointer);
					this.setLast(heap2.getLast());
				}
				if(heap2.getMin().getItem().getKey() < this.getMin().getItem().getKey()){
					// the minimum key of both heaps was in heap2
					this.setMin(heap2.getMin());
				}
				heap2.setMin(null);
				heap2.setLast(null);
			}
		}
		this.setSize(thisHeapSize + heap2HeapSize);
	}

	public HeapNode compareHeapNodesAndLink(HeapNode heapNode1, HeapNode heapNode2 ){
		if (heapNode1.getItem().getKey() < heapNode2.getItem().getKey()){
			heapNode1 = link(heapNode2, heapNode1);
			return heapNode1;
		}
		else {
			heapNode2 = link(heapNode1, heapNode2);
			return heapNode2;
		}

	}

	public HeapNode link(HeapNode biggerHeapNode, HeapNode smallerHeapNode){
		System.out.println("------------------in link-----------------");
		System.out.println("About to link biggerHeapNode: " + biggerHeapNode.getItem().getKey() + "\n to smallerHeapNode: " + smallerHeapNode.getItem().getKey());
		biggerHeapNode.setNext(biggerHeapNode);
		if (smallerHeapNode.getChild() != null){
			biggerHeapNode.setNext(smallerHeapNode.getChild().getNext());
			smallerHeapNode.getChild().setNext(biggerHeapNode);
		}
		smallerHeapNode.setChild(biggerHeapNode);
		biggerHeapNode.setParent(smallerHeapNode);
		smallerHeapNode.setRank(1 + smallerHeapNode.getRank());
		System.out.println("parent: " + biggerHeapNode.getParent().getItem().getKey() + " child " + smallerHeapNode.getChild().getNext().getItem().getKey());

		return smallerHeapNode;
	}


	/**
	 *
	 * Return the number of elements in the heap
	 *
	 */
	public int size() { return this.size; }

	/**
	 *
	 * The method returns true if and only if the heap
	 * is empty.
	 *
	 */
	public boolean empty() { return this.size() == 0;}

	/**
	 *
	 * Return the number of trees in the heap.
	 *
	 */
	public int numTrees()
	{
		HeapNode currTree = this.last;
		int lastRank = this.last.getRank();
		int count = 1;
		currTree = currTree.getNext();
		while(currTree.getRank() != lastRank){
			count++;
			currTree = currTree.getNext();
		}
		return count;
	}

	public void findNewMin (){
		HeapNode last = this.last;
		HeapNode pointer = last;
		HeapNode newMin = last;
		while (pointer.getNext() != last ){
			if (pointer.getItem().getKey() < newMin.getItem().getKey()){
				newMin = pointer;
			}
			pointer = pointer.getNext();
		}
		this.setMin(newMin);
	}


	/**
	 * Class implementing a node in a Binomial Heap.
	 *
	 */
	public class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank = 0;

		public HeapNode(){

		}

		public HeapNode(HeapItem item){
			this.item = item;
			this.next = this;
		}

		public HeapNode(HeapItem item, HeapNode next){
			this.item = item;
			this.next = next;
		}

		public HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent){
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
		}

		public HeapItem getItem(){ return item; }

		public HeapNode getChild() { return child; }

		public HeapNode getNext() { return next; }

		public HeapNode getParent() { return parent; }

		public int getRank() { return rank; }

		public void setItem(HeapItem item) { this.item = item; }

		public void setChild(HeapNode child) { this.child = child; }

		public void setNext(HeapNode next) { this.next = next; }

		public void setParent(HeapNode parent) { this.parent = parent; }

		public void setRank(int rank) { this.rank = rank; }

	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *
	 */
	public class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		public HeapItem(){

		}

		public HeapItem(int key, String info){
			this.key = key;
			this.info = info;
		}

		public HeapItem(HeapNode node, int key, String info){
			this.node = node;
			this.key = key;
			this.info = info;
		}

		public HeapNode getNode() { return node; }

		public int getKey() { return key; }

		public String getInfo() { return info; }

		public void setKey(int key){
			this.key = key;
		}

		public void setNode(HeapNode node) { this.node = node; }

		public String toString(){
			return "\n    key: " + this.key + "\n    info: " + this.info + "\n    rank: " + this.node.getRank();
		}
	}

}
