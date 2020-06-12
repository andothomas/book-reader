
/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/07/2019

UnsortedList is the most basic implementation of a linked list. It uses LLNode objects as its nodes and implements its
own add() method, find(), remove(), get(), toString(), reset(), collectData(), and getNext() methods.
-   -   -   -   -   -   -
 */
public class UnsortedList<T> extends StructuredList<T> implements ListInterface<T>

// did not include the contains() method. Made find() instead. Use find() instead.
{
	protected LLNode<T> list;		//
	protected LLNode<T> currentPos;	//
	protected LLNode<T> previous;	//
	protected LLNode<T> location;	//

	public UnsortedList()
	{
		super();
		list = null;
		currentPos = null;
		previous = null;
	}

	@Override
	// by design, list always equals the first element in the list, null if list is empty.
	public void add(T element)
	{
		find(element);
		if(found)
		{
			location.setFrequency(location.getFrequency() + 1); // if found, increments element frequency.
		}
		else
		{
			LLNode<T> newNode = new LLNode<>(element); // creates a new node
			newNode.setLink(list);						// points the new node to the top of the list
			referenceChangeCount++;
			list = newNode;								// points the list to the new node instead of the old node
			referenceChangeCount++;
			numElements++;								// increments the total # of elements in the list
		}
	}

	@Override
	public void find(T target)
	//Searches for target in list. If found, sets found = true,
	//location = node containing target, and previous = node pointing to target.
	//If not found, sets found to false.
	{
		location = list;
		previous = null;
		found = false;
		while(location != null)
		{
			comparisons++;
			if(location.getInfo().equals(target))
			{
				found = true;
				return;
			}
			else
			{
				previous = location;
				location = location.getLink();
			}
		}
	}

	@Override
	public boolean remove(T element)
	{
		find(element);
		if(found)
		{
			numElements--;
			if(numElements == 0)
			{
				if (list == location) {
					list = list.getLink();
					referenceChangeCount++;
				} else
				{
					previous.setLink(location.getLink());
					referenceChangeCount++;
				}
			}
		}
		return found;
	}

	@Override
	public T get(T element)
	{
		find(element);
		if(found)
		{
			return location.getInfo();
		}
		else
		{
			return null;
		}
	}

	@Override
	public String toString()
	//Return a formatted string of the contents of the list
	{
		LLNode<T> currNode = list;
		String listString = "List: [ ";
		while (currNode != null)
		{
			listString = listString + currNode.getInfo() + ", ";
			currNode = currNode.getLink();
		}
		listString = listString + "]";
		
		return listString;
	}

	@Override
	public void reset()
	// Initializes current position for an iteration through this list
	// to the first element on this list.
	{
		currentPos = list;
	}

	@Override
	public T getNext()
	// Preconditions: the list is not empty,
	// the list has been reset,
	// and the list has not been modified since most recent reset.
	//
	// Returns the element at the current position on this list.
	// If the current position is the last element,
	// then it advances the value of the current position to the first element
	// otherwise it advances the value of the current position to the next element
	{
		T next = currentPos.getInfo();
		if(currentPos.getLink() == null)
		{
			currentPos = list;
		}
		else
		{
			currentPos = currentPos.getLink();
		}
		return next;
	}

	@Override
	public void collectData()
	{
		// if the list is not empty, it contains at least one element
		if (list != null)
		{
			nodeCount++;
			wordCount += currentPos.getFrequency();
		}

		while (currentPos.getLink() != null)
		{
			currentPos = currentPos.getLink();
			nodeCount++;
			wordCount += currentPos.getFrequency();
		}
	}
}