
public class MicroSortList<T> extends UnsortedList<T> implements ListInterface<T>
{
	protected LLNode<T> twoBefore;	//
	
	public MicroSortList()
	{
		super();
		twoBefore = null;
	}

	@Override
	public void find(T target)
	//Searches for target in list. If found, sets found = true,
	//location = node containing target, and previous = node pointing to target.
	//If not found, sets found to false.
	{
		location = list;
		found = false;
		twoBefore = null;
		previous = null;
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
				twoBefore = previous; // this pointer addition is the only change from the super class' method
				previous = location;
				location = location.getLink();
			}
		}
	}

	@Override
	public void add(T element)
	{
		// ensure the element is not already in list.
		find(element);			// looks for a node with the same info.
		if(found)
		{
			// if found, increments numElements.
			location.setFrequency(location.getFrequency() + 1);
			
			
			if(twoBefore != null)
			{
				if(previous != null)
				{
					if(location.getLink() != null)
					{
						// set previous' reference to node after location
						previous.setLink(location.getLink());
						referenceChangeCount++;
						
						location.setLink(previous);
						referenceChangeCount++;
						
					}
					else // this means location is currently the last node
					{
						// the previous node becomes the last node
						previous.setLink(null);
						referenceChangeCount++;
					}
					// link the current node to the previous node
					location.setLink(previous);
					referenceChangeCount++;
				}
				twoBefore.setLink(location);
				referenceChangeCount++;
			}

			//else the item is the only one in the list and does not require moving
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
	public void test(){
		System.out.println("Test");
	}
}