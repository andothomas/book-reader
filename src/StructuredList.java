/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/27/2019


StructuredList is the most basic "shell" of a list, such that all lists created in succession of it can pass the "is-a"
test in order to be aggregated into a list for easy iteration. Simplifies future list implementations.
-   -   -   -   -   -   -
 */
public class StructuredList<T> implements ListInterface<T>
{
    // for data collection
    int nodeCount;
    int wordCount;
    public long comparisons;
    int referenceChangeCount;
    protected boolean found;

    int numElements;

    public StructuredList()
    {
        nodeCount = 0;
        wordCount = 0;
        comparisons = 0;
        referenceChangeCount = 0;

        numElements = 0;
        found = false;
    }

    @Override
    public int size()
    {
        System.out.println("Called Super Class' size() method.");
        return 0;
    }

    @Override
    public void add(T element)
    {
        System.out.println("Called Super Class' add() method.");
    }

    public void add(Hashable element)
    {
        System.out.println("Called Super Class' add(Hashable element) method.");
    }

    @Override
    public void find(T target)
    {
        System.out.println("Called Super Class' find() method.");
    }

    public void find(Hashable element)
    {

    }

    @Override
    public boolean remove(T element)
    {
        System.out.println("Called Super Class' remove() method.");
        return false;
    }

    @Override
    public T get(T element)
    {
        System.out.println("Called Super Class' get) method.");
        return null;
    }

    @Override
    public void reset()
    {
        System.out.println("Called Super Class' reset() method.");
    }

    @Override
    public T getNext()
    {
        System.out.println("Called Super Class' getNext() method.");
        return null;
    }

    public int getReferenceChangeCount()
    {
        return referenceChangeCount;
    }

    public long getComparisons()
    {
        return comparisons;
    }

    public void collectData()
    {
        System.out.println("Called Super Class' collectData() method.");
    }

    public void resetStats()
    {
        comparisons = 0;
        referenceChangeCount = 0;
    }
}