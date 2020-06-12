/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/07/2019

The TopSortList1 is a list that sorts all new and newly encountered elements at the front of the list for quick access.
It contains its own implementation of the find() method, and inherits all other list behaviors
from the UnsortedList class.
-   -   -   -   -   -   -
 */
public class TopSortList1<T> extends UnsortedList<T> implements ListInterface<T>
{
	public TopSortList1()
	{
		super();
	}

	@Override
	public void add(T element)
	{
		// ensure the element is not already in list.
		find(element);			// looks for a node with the same info.
		if(found)
		{
			location.setFrequency(location.getFrequency() + 1); // if found, increments numElements.
			if(previous != null)
			{
				// set previous' reference to node after location
				previous.setLink(location.getLink());
				referenceChangeCount++;

				// link the current node to the first node so it now becomes the first node in the list
				location.setLink(list);
				referenceChangeCount++;

				// set the list node to point to the new first node (location)
				list = location;
				referenceChangeCount++;
			}
		}
		else
		{
			LLNode<T> newNode = new LLNode<T>(element); // creates a new node
			newNode.setLink(list);						// points the new node to the top of the list
			referenceChangeCount++;
			list = newNode;								// points the list to the new node instead of the old node
			referenceChangeCount++;
			numElements++;								// increments the total # of elements in the list
		}
	}
}
