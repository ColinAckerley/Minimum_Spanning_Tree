package apps;
import structures.*;
import java.util.ArrayList;
public class MST
{
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph
	 *            Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	/*
	 * This method is just writing the 1st two steps of the algorithim in code
	 * Wrote pseudo code and then transcribed into actual code
	 */
	public static PartialTreeList initialize(Graph graph)
	{
		// List to store the resulting partial trees
		PartialTreeList treeList = new PartialTreeList();
		for(Vertex v:graph.vertices) // Enhanced for loop to traverse the graph
		{
			PartialTree curTree = new PartialTree(v); // Creates a new partial
														// tree with current
														// vertex as its vertex
			// Create a new min heap
			MinHeap<PartialTree.Arc> minHp = new MinHeap<PartialTree.Arc>();
			Vertex curVer = v; // Copy of the current vertex that can be
								// modified
			while (curVer.neighbors != null) // Traverse the neighbors of
												// current vertex
			{
				// Makes a new arc with the two set vertices and the weight
				// between them
				PartialTree.Arc arc = new PartialTree.Arc(v, curVer.neighbors.vertex, curVer.neighbors.weight);
				// Insert the arc into the min heap
				minHp.insert(arc);
				// Advance to the next neighbor of current vertex
				curVer.neighbors = curVer.neighbors.next;
			}
			// Merge to make sure the min heap has all of the arcs for the
			// vertex
			curTree.getArcs().merge(minHp);
			treeList.append(curTree); // Append the current tree to the partial
										// tree list
		}
		for(PartialTree p:treeList)
		{
			System.out.println(p);
		}
		return treeList; // Return the list of partial trees
	}
	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree
	 * list
	 * 
	 * @param ptlist
	 *            Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is
	 *         irrelevant
	 */
	/*
	 * This is following the remaining 7 steps of the given algorithim. Wrote
	 * pseudo code & then transcribed to real code Basically checking if two
	 * vertexes are connected, and if not, connecting them and then merging
	 * everything together at the end
	 * 
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist)
	{
		// Arraylist for the results
		ArrayList<PartialTree.Arc> results = new ArrayList<PartialTree.Arc>();
		while (ptlist.size() > 1) // While the given list still has stuff in it
		{
			PartialTree ptx = ptlist.remove(); // Remove the partial tree
			MinHeap<PartialTree.Arc> ptMin = ptx.getArcs(); // Get the priorty
															// queue for the
															// partial tree
			PartialTree.Arc minArc = ptMin.deleteMin(); // Delete the highest
														// priority arc and
														// store it
			Vertex v1 = minArc.v1;
			Vertex v2 = minArc.v2; // Store the second vertex of the arc
			while (ptx.getArcs().size() > 0) // Check all of the arcs
			{
				if(v1.getRoot().equals(v2.getRoot())) // If both have the same
														// root
				{
					minArc = ptMin.deleteMin(); // Get the next highest priority
												// item
					v2 = minArc.v2; // Update v2
				}
				else // Otherwise break out of the loop
					break;
			}
			ptx.merge(ptlist.removeTreeContaining(v2)); /// Remove the tree with
														/// v2 in it and then
														/// merge with ptx
			results.add(minArc); // Add the min arc to results list
			ptlist.append(ptx); // Append partial tree to ptlist
		}
		return results;
	}
}
