
public class AlphabeticalList<T> extends UnsortedList<T> implements ListInterface<T>
{
	public AlphabeticalList()
	{
		super();
	}

	@Override
	public void add(T element)
	{
		// ensure the element is not already in list.
		find(element);			// looks for a node with the same info or an insertion point.
		if(found)
		{
			location.setFrequency(location.getFrequency() + 1); // if found, increments numElements.
		}
		else
		{
			// Prepare node for insertion.
			LLNode<T> newNode = new LLNode<T>(element);
			
			// Insert node into list.
			if (previous == null)
			{
				// Insert node into list
				newNode.setLink(list);
				referenceChangeCount++;
				list = newNode;
				referenceChangeCount++;
			}
			else
			{
				// Insert node elsewhere.
				newNode.setLink(location);
				referenceChangeCount++;
				previous.setLink(newNode);
				referenceChangeCount++;
			}
			numElements++;
		}
	}

	@Override
	public void find(T target)
	//Searches for insertion point and target in list. If target is found, sets found = true,
	//location = node containing target, and previous = node pointing to target.
	//If not found, sets found to false, but the insertion point is still kept.
	{
		location = list;
		previous = null;
		found = false;
		while(location != null)
		{
			comparisons++;
			// This comparison should change for each sorted list. In this case, it is checking
			// for alphabetical order. -1 indicates target should be after location.getInfo().toString().
			if(location.getInfo().toString().compareTo((String) target) >= 0)
			{
				if(location.getInfo().toString().compareTo((String) target) == 0)
				{
					found = true;
				}
				return;
			}
			else
			{
				previous = location;
				location = location.getLink();
			}
		}
	}
}
