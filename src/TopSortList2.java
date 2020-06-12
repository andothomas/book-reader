/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/18/2019

The TopSortList2 is a list that sorts all new and newly encountered elements at the front of the list for quick access.
It inherits the TopSortList1 implementation of the find() method, implements its own version of the remove() method for
optimized removal of nodes, and inherits all other list behaviors from the UnsortedList class.
-   -   -   -   -   -   -
 */
public class TopSortList2<T> extends TopSortList1<T> implements ListInterface<T>
{
    public TopSortList2()
    {
        super();
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
            else
            {
                // if the element is not the topmost
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
        }
        return found;
    }
}
