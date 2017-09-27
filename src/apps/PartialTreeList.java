package apps;
import java.util.Iterator;
import java.util.NoSuchElementException;
import structures.Vertex;
public class PartialTreeList implements Iterable<PartialTree>
{
	/**
	 * Inner class - to build the partial tree circular linked list
	 * 
	 */
	public static class Node
	{
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		/**
		 * Next node in linked list
		 */
		public Node next;
		/**
		 * Initializes this node by setting the tree part to the given tree, and
		 * setting next part to null
		 * 
		 * @param tree
		 *            Partial tree
		 */
		public Node(PartialTree tree)
		{
			this.tree = tree;
			next = null;
		}
	}
	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	/**
	 * Initializes this list to empty
	 */
	public PartialTreeList()
	{
		rear = null;
		size = 0;
	}
	/**
	 * Adds a new tree to the end of the list
	 * 
	 * @param tree
	 *            Tree to be added to the end of the list
	 */
	public void append(PartialTree tree)
	{
		Node ptr = new Node(tree);
		if(rear == null)
		{
			ptr.next = ptr;
		}
		else
		{
			ptr.next = rear.next;
			rear.next = ptr;
		}
		rear = ptr;
		size++;
	}
	/**
	 * Removes the tree that is at the front of the list.
	 * 
	 * @return The tree that is removed from the front
	 * @throws NoSuchElementException
	 *             If the list is empty
	 */
	/*
	 * This method is just deletion of an item from a CLL Two cases: Case 1: CLL
	 * only has one item Case 2: CLL has more than 1 item
	 */
	public PartialTree remove() throws NoSuchElementException
	{
		if(size <= 0)
			throw new NoSuchElementException("Empty Tree");
		// If only one element in CLL, delete it
		if(size == 1)
		{
			PartialTree tmp = rear.tree; // Store the node so it can be returned
			rear = null; // Set the lsit to null
			size = 0;
			return tmp;
		}
		PartialTree tmpTree = rear.next.tree; // Store the tree of the first
												// node so it can be returned
		rear.next = rear.next.next; // Delete the first node of the CLL
		size--; // Decrease the size
		return tmpTree;
	}
	/**
	 * Removes the tree in this list that contains a given vertex.
	 * 
	 * @param vertex
	 *            Vertex whose tree is to be removed
	 * @return The tree that is removed
	 * @throws NoSuchElementException
	 *             If there is no matching tree
	 */
	/*
	 * This method is pretty much a search and delete in a CLL Will have 3 cases
	 * which will require slightly different edit of the CLL Case 1: Target is
	 * the only item in the CLL Case 2: The target is the rear of the CLL Case
	 * 3: The target is anywhere else in the CLL
	 */
	public PartialTree removeTreeContaining(Vertex vertex) throws NoSuchElementException
	{
		if(size <= 0)
			throw new NoSuchElementException("Empty Tree");
		Node prev = rear; // Keep track of a prev and cur node
		Node cur = rear.next;
		// Do while loop is most efficient to traverse a CLL
		do
		{
			// If the vertex given is found in the CLL
			if(cur.tree.getRoot().equals(vertex.getRoot()))
			{
				// If the vertex given is the rear of the CLL
				if(rear.tree.getRoot().equals(vertex.getRoot()))
				{
					prev.next = rear.next;
					rear = prev; // Set the rear of the list to the prev node
					size--;
					return cur.tree;
				}
				// Case if it is the only element
				if(size == 1)
				{
					PartialTree tmp = rear.tree;
					size = 0;
					rear = null;
					return tmp;
				}
				// General case
				prev.next = cur.next; // Just delete the specified node
				size--;
				return cur.tree;
			}
			prev = prev.next; // Advance prev and cur
			cur = cur.next;
		}
		while (cur != rear.next); // Stop when the list has been traversed
		throw new NoSuchElementException("Tree Not Found"); //If the element is not found
	}
	/**
	 * Gives the number of trees in this list
	 * 
	 * @return Number of trees
	 */
	public int size()
	{
		return size;
	}
	/**
	 * Returns an Iterator that can be used to step through the trees in this
	 * list. The iterator does NOT support remove.
	 * 
	 * @return Iterator for this list
	 */
	public Iterator<PartialTree> iterator()
	{
		return new PartialTreeListIterator(this);
	}
	private class PartialTreeListIterator implements Iterator<PartialTree>
	{
		private PartialTreeList.Node ptr;
		private int rest;
		public PartialTreeListIterator(PartialTreeList target)
		{
			rest = target.size;
			ptr = rest > 0 ? target.rear.next : null;
		}
		public PartialTree next() throws NoSuchElementException
		{
			if(rest <= 0)
			{
				throw new NoSuchElementException();
			}
			PartialTree ret = ptr.tree;
			ptr = ptr.next;
			rest--;
			return ret;
		}
		public boolean hasNext()
		{
			return rest != 0;
		}
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
	}
}
