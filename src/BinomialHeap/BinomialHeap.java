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

	public BinomialHeap(HeapNode last, HeapNode min) {
		this.last = last;
		this.min = min;
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
		BinomialHeap heap2 = new BinomialHeap(item.getNode(), item.getNode());
		this.meld(heap2);
		return item;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

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
		return; // should be replaced by student code   		
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

	public void find_new_min (BinomialHeap binomial_Heap){
		HeapNode last = binomial_Heap.getLast();
		HeapNode pointer = last;
		HeapNode new_min = last;
		while (pointer.getNext() != last ){
			if (pointer.getItem().getKey() < new_min.getItem().getKey()){
				new_min = pointer;
			}
			pointer.getNext();
		}
		binomial_Heap.setMin(new_min);
	}


	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public class HeapNode{
		protected HeapItem item;
		protected HeapNode child;
		protected HeapNode next;
		protected HeapNode parent;
		protected int rank = 0;

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
		protected HeapNode node;
		protected int key;
		protected String info;

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
	}

}
