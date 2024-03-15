package BinomialHeap;
//package binomialheaptester.yourcode;

/*
username - sagihe
id1      - 207190406
name1    - Sagi Eisenberg
id2      - 318674355
name2    - Ido Zacharia
 */

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap {

	public int size = 0;
	public HeapNode last;
	public HeapNode min;

	public int numOfTrees = 0;

	public int countLinks = 0;

	public int sumOfRanks = 0;

	public BinomialHeap() {
	}

	public BinomialHeap(int size, HeapNode last) {
		this.size = size;
		this.last = last;
		if (size > 0 && last != null)
			this.numOfTrees = 1;
	}

	public BinomialHeap(HeapNode last, HeapNode min) {
		this.last = last;
		this.min = min;
		if (last != null && min != null) {
			this.size = 1;
			this.numOfTrees = 1;
		}
	}

	public HeapNode getLast() {
		return last;
	}

	public HeapNode getMin() {
		return this.min;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setLast(HeapNode last) {
		this.last = last;
	}

	public void setMin(HeapNode newMin) {
		this.min = newMin;
	}

	/**
	 * Creates a new HeapItem with the given key and info.
	 * @param key The key of this item.
	 * @param info The info of this item.
	 * @return The created HeapItem.
	 */
	public HeapItem makeNewItem(int key, String info) {
		HeapItem item = new HeapItem(key, info);
		HeapNode node = new HeapNode(item);
		item.setNode(node);
		return item;
	}

	/**
	 * pre: key > 0
	 * <p>
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 */
	public HeapItem insert(int key, String info) {
		HeapItem item = this.makeNewItem(key, info);
		BinomialHeap heap2 = new BinomialHeap(item.getNode(), item.getNode());
		this.meld(heap2);
		// Added a tree of rank 0 to the heap.
		return item;
	}

	/**
	 * Creates a new heap whose roots are the children of the given node.
	 * @param node the root of all the roots of the heap we're about to create.
	 * @return A new BinomialHeap.
	 */
	public static BinomialHeap createHeapFromNodeChildren(HeapNode node) {
		int rank = node.getRank();
		int size = (int) Math.pow(2, rank) - 1;
		HeapNode last = node.getChild();
		node.setChild(null);
		BinomialHeap newHeap = new BinomialHeap(size, last);
		newHeap.numOfTrees = node.getRank();
		if (newHeap.empty()){
			return newHeap;
		}
		newHeap.findNewMin();
		return newHeap;
	}

	/**
	 * Disconnects the node given from this heap by updating this heap size,
	 * pointers of relevant nodes, also updates this heap last and min nodes.
	 * This method is called when we wish to the delete the min of this heap.
	 * @param node The node we wish to disconnect from the heap.
	 */
	public void disconnectNodeFromHeap(HeapNode node) {
		if (this.last.getNext().getRank() == this.last.getRank()) {
			this.size = 0;
			this.last = null;
			this.min = null;
			this.numOfTrees = 0;
			return;
		}
		HeapNode nodeNext = node.getNext();
		// First, we update the size of the heap
		int sizeDec = (int) Math.pow(2, node.getRank());
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
		// Finally, we update the min of the heap and the number of trees.
		this.findNewMin();
		this.numOfTrees--;
	}

	/**
	 * Delete the minimal item
	 */
	public void deleteMin() {
		if (this.size <= 1) {
			this.min = null;
			this.last = null;
			this.size = 0;
			this.numOfTrees = 0;
			return;
		}
		HeapNode deletedMin = this.min;
		// First, we disconnect the deleted node from the heap.
		this.disconnectNodeFromHeap(deletedMin);
		// Second, we create a new heap from the deleted node's children.
		BinomialHeap heap2 = createHeapFromNodeChildren(deletedMin);
		// Finally, we meld the 2 heaps together.
		this.meld(heap2);

	}

	/**
	 * Return the minimal HeapItem
	 */
	public HeapItem findMin() {
		if (!this.empty()) { return this.min.getItem();}
		return null;
	}

	/**
	 *
	 * @param item
	 */
	public void siftUp(HeapItem item){
		HeapNode currNode = item.getNode();
		HeapNode parent = currNode.getParent();
		HeapItem parentItem;
		while (parent != null){
			parentItem = parent.getItem();
			if (item.getKey() > parentItem.getKey()){
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
		if (item.getKey() <= this.getMin().getItem().getKey()){
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
		if (heap2 == null){
			return;
		}
		int heap1StartingNumOfTrees = this.numOfTrees;
		this.numOfTrees += heap2.numOfTrees;
		int thisHeapSize = this.size;
		int heap2HeapSize= heap2.size();
		if (!heap2.empty()){
			// Checking case where heap2 isn't empty
			if (this.empty()){
				/*
				Handles case where the current heap is empty, if so, replace with heap2.
				 */
				this.setMin(heap2.getMin());
				this.setLast(heap2.getLast());
				this.setSize(heap2.size());

				heap2.setLast(null);
				heap2.setMin(null);
				heap2.numOfTrees = 0;
			}
			else {
				/*
				Handles case where both heaps aren't empty.
				 */
				HeapNode heap1Prev = this.getLast();
				HeapNode heap2Last = heap2.getLast();

				HeapNode heap1First = heap1Prev.getNext();
				HeapNode heap1Pointer = heap1First;
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
						if(heap1First == heap1Pointer.getChild()){
							heap1First = heap1Pointer;
						}
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
							if (this.getLast().getRank() < heap1Pointer.getRank()){
								// update the last's pointer in this heap if it changed
								this.setLast(heap1Pointer);
							}
							if(heap1First == heap1Pointer.getChild()){
								heap1First = heap1Pointer;
							}
							counter++;
						}
					}
					else if (heap1Pointer.getRank() > heap2Pointer.getRank()) {
						// we can't link this tree of heap 2, so we need to connect it before this tree of this heap
						heap1Prev.setNext(heap2Pointer);
						heap1Prev = heap1Prev.getNext();
						heap2Pointer.setNext(heap1Pointer);
						heap2Pointer = heap2NextPointer;
						if(heap1First.getRank() > heap1Prev.getRank()){
							heap1First = heap1Prev;
						}
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
				heap2.numOfTrees = 0;
			}
		}
		this.setSize(thisHeapSize + heap2HeapSize);
	}

	/**
	 *
	 * @param heapNode1
	 * @param heapNode2
	 * @return
	 */
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

	/**
	 *
	 * @param biggerHeapNode
	 * @param smallerHeapNode
	 * @return
	 */
	public HeapNode link(HeapNode biggerHeapNode, HeapNode smallerHeapNode){
		biggerHeapNode.setNext(biggerHeapNode);
		if (smallerHeapNode.getChild() != null){
			biggerHeapNode.setNext(smallerHeapNode.getChild().getNext());
			smallerHeapNode.getChild().setNext(biggerHeapNode);
		}
		smallerHeapNode.setChild(biggerHeapNode);
		biggerHeapNode.setParent(smallerHeapNode);
		smallerHeapNode.setRank(1 + smallerHeapNode.getRank());
		if (smallerHeapNode.getItem().getKey() <= getMin().getItem().getKey())
			setMin(smallerHeapNode);
		// Linked two trees so number of trees decreased.
		this.numOfTrees--;
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
		return numOfTrees;
	}

	/**
	 * This method calculates the number of trees in this heap.
	 * @return number of trees.
	 */
	public int calNumTrees()
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

	/**
	 * Finds the new Min node in this heap by traversing the nodes in the roots "list".
	 */
	public void findNewMin (){
		HeapNode last = this.last;
		HeapNode pointer = last;
		HeapNode newMin = last;
		while (pointer.getNext() != last ){
			pointer.setParent(null);
			if (pointer.getItem().getKey() < newMin.getItem().getKey()){
				newMin = pointer;
			}
			pointer = pointer.getNext();
		}
		pointer.setParent(null);
		if (pointer.getItem().getKey() < newMin.getItem().getKey()){
			newMin = pointer;
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
