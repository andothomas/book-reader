/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 12/11/2019
-   -   -   -   -   -   -
HashList is the only "flavor" of the binary search tree list.
It will not be self-balancing.
-   -   -   -   -   -   -
 */
public class BSTList<T> extends StructuredList<T> implements ListInterface<T>
{
    private BSTNode<T> root;
    private BSTNode<T> foundElem;
    protected BSTNode<T> currentPos;

    protected BSTNode previous;
    protected BSTNode location;

    // should the new node to be inserted be placed on the
    protected boolean leftChild;

    public BSTList()
    {
        super();
        root = null;
        foundElem = null;
        previous = null;
        location = null;
        leftChild = true;
    }

    @Override
    public void add(T element)
    {
        find(element);
        if(found)
        {
            // the element was found in the list, so only the frequency needs to be incremented.
            location.incrementFreq(); // if found, increments freq.
        }
        else
        {
            // the element is to be inserted into the tree.
            // prepare a new node for insertion
            BSTNode<T> newNode = new BSTNode<T>(element);

            // assuming location is set to the proper insertion point when find() is exited
            if (previous == null)
            {
                // only if the list is empty.
                root = newNode;
                referenceChangeCount++;
            }
            else
            {
                //If the list has one (or more) element(s) in it, previous should point to the
                // first element (or another) and location should point to one of the null children
                // pointers of previous.

                // this may not work.
                // add newNode as a child of previous, considering which specific child it should be
                if(leftChild)
                {
                    previous.setLCH(newNode);
                    referenceChangeCount++;
                }
                else
                {
                    previous.setRCH(newNode);
                    referenceChangeCount++;
                }
                newNode.setParent(previous);
                referenceChangeCount++;
            }
        }
    }

    @Override
    public void find(T target)
    {
        // find() looks for target in the list. If found, sets found to true. Else, the location last captured is
        // the exact location the element should be inserted.
        location = root;
        previous = null;
        found = false;
        leftChild = false;
        int compare;

        // loop runs until either a new leaf location (null lCH or rCH pointer) is obtained, or the target is found to
        // already be in the list. Only a "found" target will set --> "found = true;"
        while(location != null)
        {
            compare = location.getInfo().toString().compareTo((String) target);
            comparisons++;
            if(compare > 0)
            {
                // the target comes lexicographically before the current word,
                // so continue the loop with the left child of location
                previous = location;
                location = location.getLCH();
                leftChild = true;
            }
            else if(compare < 0)
            {
                // the target comes lexicographically after the current word,
                // so continue the loop with the left child of location
                previous = location;
                location = location.getRCH();
                leftChild = false;
            }
            else
            {
                // else, the target is equal to the current word, so the word was found.
                found = true;
                return;
            }
        }
    }

    @Override
    public String toString()
    {
        // look at the traversal slides for a recursive way to implement this
        return "The BSTList does not currently support conversion to a string.";
    }

    @Override
    public void reset()
    {
        // this method is for the list implementor to use.
        location = root;
    }

    @Override
    public void collectData()
    {
        System.out.println("The height of the tree is " + getHeight(root, 0));
        //displayFirst100();
        traverse(root);
    }

    private BSTNode successor(BSTNode n)
    {
        // returns the immediate successor of a given element
        previous = null;
        location = n;
        if(location.getRCH() != null)
        {
            return minimum(location.getRCH());
        }

        // previous will always be the parent node of location
        previous = location.getParent();

        // as long as following the path from location to previous (child to parent) is a "left oblique"
        while(previous != null && location == previous.getRCH())
        {
            location = previous;
            previous = previous.getParent();
        }
        return previous;
    }

    private BSTNode minimum(BSTNode n)
    {
        // finds the lexicographically smallest node in a
        // subtree (if n is not the root) or the whole tree (if n is the root).
        location = n;
        while (location.getLCH() != null)
        {
            location = location.getLCH();
        }
        return location;
    }

    public void traverse(BSTNode n)
    {
        BSTNode currentNode = n;

        if(currentNode != null)
        {
            nodeCount++;
            wordCount += currentNode.getFreq();
           // System.out.println(currentNode.getInfo().toString());
            if(currentNode.getLCH() != null) traverse(currentNode.getLCH());
            if(currentNode.getRCH() != null) traverse(currentNode.getRCH());
        }
    }

    private int getHeight(BSTNode n, int currentHeight)
    {
        int height = currentHeight;
        int lHeight = 0;
        int rHeight = 0;
        if(n != null)
        {
            height++;
            if(n.getLCH() != null) lHeight = getHeight(n.getLCH(), height);
            if(n.getRCH() != null) rHeight = getHeight(n.getRCH(), height);
            if(n.getRCH() == null && n.getLCH() == null)
            {
                return height;
            }

            if(lHeight < rHeight)
            {
                return rHeight;
            }
            else return lHeight;
        }
        return height;
    }

    public void displayFirst100()
    {
        BSTNode p = minimum(root); // Get smallest thing in tree
        System.out.println("First 100 entries:");
        for (int i = 0; i < 100; i++) // Print 100 items,
        { // starting from there
            if(p==null)
            {
                System.out.println("The selected document generated a list with fewer than 100 nodes.");
                break;
            }
            System.out.println(p.getInfo() + " " + p.getFreq());
            p = successor(p); // Go on to NEXT item
        }
    }
}
