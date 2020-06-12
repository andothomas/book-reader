/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/27/2019
-   -   -   -   -   -   -
HashList is the first "flavor" of the hash list (with linked list chaining as the solution to collisions).
It will have 256 places in the hash table. Each "chain" will be a TopSortList.

All other HashList data types will be a child class of this HashList class, each implementing their own hashing
function in the add() method.
-   -   -   -   -   -   -
 */
public class HashList<T> extends StructuredList<T>
{
    private LLHNode[] table;
    private LLHNode<T> foundElem;
    protected LLHNode currentPos;

    protected LLHNode previous;
    protected LLHNode location;
    protected int hashFunc;

    public HashList(int length, int hashFunc)
    {
        table = new LLHNode[length];
        foundElem = null;
        previous = null;
        location = null;
        this.hashFunc = hashFunc;
    }

    @Override
    public void add(T element)
    {
        find(element);

        LLHNode<T> newNode = new LLHNode<>(element, hashFunc);
        int hashIndex;
        // hashes the info of the newNode, not the newNode itself (contrary to the syntax).
        hashIndex = newNode.hash();

        comparisons++;
        if(location != null && element.equals(location.getInfo()))
        {
            location.setFrequency(location.getFrequency() + 1);

            // move element to the front of the list
            if(previous != null)
            {
                // set previous' reference to node after location
                previous.setLink(location.getLink());
                referenceChangeCount++;

                // link the current node to the first node so it now becomes the first node in the list
                location.setLink(table[hashIndex]);
                referenceChangeCount++;

                // set the list node to point to the new first node (location)
                table[hashIndex] = location;
                referenceChangeCount++;
            }
        }
        else
        {
            // add element to the list at location element.hash()
            if(table[hashIndex] == null)
            {
                //LLHNode is a Hashable object, but a Hashable is not an LLHNode. Is casting an appropriate fix?
                table[hashIndex] = newNode;
                referenceChangeCount++;
                numElements++;
            }
            else // otherwise, the location is occupied, and the element must be chained
            {
                newNode.setLink(table[hashIndex]);
                referenceChangeCount++;
                table[hashIndex] = newNode;
                referenceChangeCount++;
            }
        }
    }

    @Override
    public void find(T element)
    {
        //Sets instance variable to e such that e.equals(element).
        LLHNode<T> given = new LLHNode<>(element, hashFunc);

        int hashIndex;
        hashIndex = given.hash();

        location = table[hashIndex];
        previous = null;

        while (location != null && !location.getInfo().equals(element))
        {
            comparisons++;
            previous = location;
            location = (LLHNode) location.getLink();
        }
        comparisons++;
    }

    @Override
    public void reset()
    // Initializes current position for an iteration through this list
    // to the first element on this list.
    {
        currentPos = table[0];
    }

    @Override
    public void collectData()
    {
        // a stats array to store the number of elements in each chain in the table
        int[] chainLength = new int[256];

        // traverse the hash table and collect data on the elements contained within each entry.
        for (int i = 0; i < table.length; i++)
        {
            currentPos = table[i];
            while (currentPos != null)
            {
                chainLength[i]++;
                nodeCount++;
                wordCount += currentPos.getFrequency();
                currentPos = (LLHNode) currentPos.getLink();
            }
        }

        // finding min, max, and average
        int min = chainLength[0];
        int max = chainLength[0];
        double mean = 0;
        double stDev = 0;
        //System.out.print("All 256 Chain Lengths:");
        for (int length:chainLength)
        {
            if (length < min)
            {
                min = length;
            }
            if (length > max)
            {
                max = length;
            }
            mean += length;
            //System.out.printf("%n%d",length);
        }
        System.out.println();
        mean = mean/256;

        // calculating stDev:
        for(int length:chainLength)
        {
            stDev += Math.pow(length-mean, 2);
        }
        stDev = stDev/256;
        stDev = Math.sqrt(stDev);

        System.out.printf("Preliminary HashList Stats:%nMinimum: %d%nMaximum: %d%nMean: %f%nStandard Deviation: %f%n%n", min, max, mean, stDev);
    }
}