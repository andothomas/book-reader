
public class AlphabeticalListV2<T> extends AlphabeticalList<T> implements ListInterface<T>
{
    public LLNode prevLastSearched;
    public AlphabeticalListV2()
    {
        super();
        prevLastSearched = null;
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
            // else, the add method should be called to insert the element in the list.

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
            prevLastSearched = previous;
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

        // look at stored search and see where the new word lies.
        // if it's after the word, start at that point. Else, start from the beginning.
        if(location != null && prevLastSearched != null &&
                target.toString().compareTo((String) prevLastSearched.getInfo()) > 0)
        {
            location = prevLastSearched.getLink();
            previous = prevLastSearched;
        }

        while(location != null)
        {
            comparisons++;
            if(location.getInfo().toString().compareTo((String) target) >= 0)	// This comparison should change for each sorted list. In this case, it is checking for alphabetical order. -1 indicates the expression comes after the compared-to word.
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

                // save location for future reference
                prevLastSearched = previous;
            }
        }
    }
}
