/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/18/2019

SkipList is a class for the skip list data structure. It includes find() to look for a given element in the list,
-   -   -   -   -   -   -
 */
import java.util.Random;

public class SkipList<T> extends UnsortedList<T> implements ListInterface<T>
{
    private SLNode<T> head, tail;
    private int height; // height (# of lanes)
    private int length; // items in actual list (slow lane)
    private Random randVal;
    private SLNode foundPos;
    protected SLNode currentPos;

    public SkipList()
    {
        head = new SLNode(SLNode.NEG_INF);
        tail = new SLNode(SLNode.POS_INF);

        head.setRight(tail);
        tail.setLeft(head);
        randVal = new Random();
        length = 0;
        height = 1;
    }

    @Override
    public void find(T item)
    {
        // this is the method replacing the required "search(String s)"
        // it has a different name for consistency, so it can override the superclass method

        SLNode currentPos = head;
        while(true)
        {
            // as long as the word we're looking for belongs after the
            // node were looking at OR is the same. This is what it will return.
            comparisons++;
            while(!currentPos.getRight().word.toString().equals(SLNode.POS_INF)
                    && currentPos.getRight().word.toString().compareTo((String) item) <= 0)
            {
                // look at the next node
                currentPos = currentPos.getRight();
            }
            // if we're at the lowest level, return the current node
            if(currentPos.getDown() == null)
            {
                foundPos = currentPos;
                return;
            }
            currentPos = currentPos.getDown();
        }
    }

    /*
    // this method has no use for book reading, however, if the Skip list contained nodes with information different
    // than the node name, you could use this method to return that information if the node was found
    public String get(String word)
    {
        SLNode currentPos;
        currentPos = find(word);

        // if the item we're looking for exists in the list, return that item.
        if(word.equals(currentPos.word.toString) return currentPos.word;
        else return null;
    }
     */

    // this is the method replacing the required "insert(String s)"
    // it has a different name for consistency, so it can override the superclass method
    @Override
    public void add(T word)
    {
        SLNode point;
        SLNode currentPos, nextPos;
        // number of levels the node will have
        int levels;

        find(word);
        currentPos = foundPos;

        // if found, increment the count
        comparisons++;
        if(word.equals(currentPos.word.toString()))
        {
            currentPos.freq++;
        }
        else
        {
            // create a new entry for the word
            nextPos = new SLNode(word);

            // set the links for the new word using currentPos (the word before the insertion point)
            nextPos.setLeft(currentPos);
            referenceChangeCount++;
            nextPos.setRight(currentPos.getRight());
            referenceChangeCount++;

            currentPos.getRight().setLeft(nextPos);
            referenceChangeCount++;
            currentPos.setRight(nextPos);
            referenceChangeCount++;


            // deciding the number of levels the added node will have in the skip list
            // current level = 1
            levels = 1;

            // "toss coin until it's tails"
            while(randVal.nextDouble() < 0.5)
            {
                // the "toss" was "heads"
                // check to see if we're at the highest layer
                if(levels>=height)
                {
                    addLayer();
                }
                // find the first node with an "up" link. May take multiple loops.
                while(currentPos.getUp() == null)
                {
                    currentPos = currentPos.getLeft();
                }

                // make currentPos point to the node in the upper level. This node will be to the left of the new node
                currentPos = currentPos.getUp();

                // make the new skipNode
                SLNode skipNode = new SLNode(word);
                // initialize the new node's links
                skipNode.setLeft(currentPos);
                referenceChangeCount++;
                skipNode.setRight(currentPos.getRight());
                referenceChangeCount++;
                skipNode.setDown(nextPos);
                referenceChangeCount++;

                // changing neighboring links
                currentPos.getRight().setLeft(skipNode);
                referenceChangeCount++;
                currentPos.setRight(skipNode);
                referenceChangeCount++;
                nextPos.setUp(skipNode);
                referenceChangeCount++;

                // setup for possible next iteration
                nextPos = skipNode;
                levels++;
            }
            length++;
        }
    }

    private void addLayer()
    {
        SLNode newHead, newTail;

        // new marker nodes for the layer
        newHead = new SLNode(SLNode.NEG_INF);
        newTail = new SLNode(SLNode.POS_INF);

        // link them together
        newHead.setRight(newTail);
        referenceChangeCount++;
        newTail.setLeft(newHead);
        referenceChangeCount++;

        // link the new top layer to the *PRIOR* top layer
        newHead.setDown(head);
        referenceChangeCount++;
        newTail.setDown(tail);
        referenceChangeCount++;

        // link the *PRIOR* top layer to the new top layer
        head.setUp(newHead);
        referenceChangeCount++;
        tail.setUp(newTail);
        referenceChangeCount++;

        // move the end marker pointers to the top layer
        head = newHead;
        tail = newTail;

        // increment the height
        height++;
    }

    private void removeLayer()
    {
        // remove reference between markers
        head.setRight(null);
        referenceChangeCount++;
        tail.setLeft(null);
        referenceChangeCount++;

        // move down a layer
        head = head.getDown();
        tail = tail.getDown();

        // remove down reference from each marker on top layer
        head.getUp().setDown(null);
        referenceChangeCount++;
        tail.getUp().setDown(null);
        referenceChangeCount++;

        // remove up reference from each marker on the layer underneath
        head.setUp(null);
        referenceChangeCount++;
        tail.setUp(null);
        referenceChangeCount++;

        // set the new list height
        height--;
    }

    // this is the method replacing the required "remove(String s)"
    // it has a different signature for consistency, so it can override the superclass method
    @Override
    public boolean remove(T word)
    {
        // removes a word from the list. This method only works for the "reading a book" implementation,
        // as it decrements the word count first, and decides if it should remove all references to the word
        // (if the word count reaches 0)

        SLNode currentPos;

        find(word);
        currentPos = foundPos;
        if(word.equals(currentPos.word.toString()))
        {
            // decrement word count
            currentPos.freq--;

            // if the word count is zero, remove the nodes from the list entirely
            if(currentPos.freq == 0)
            {
                while(currentPos != null)
                {
                    // unlink currPos from neighbors
                    currentPos.getLeft().setRight(currentPos.getRight());
                    referenceChangeCount++;
                    currentPos.getRight().setLeft(currentPos.getLeft());
                    referenceChangeCount++;

                    // move up
                    currentPos = currentPos.getUp();
                }
                // if the removal of these nodes renders a layer completely empty, remove that layer.
                // repeats if there is more than one empty layer, until the bottom layer is reached.
                // does not remove bottom layer if empty.
                while(head.getRight().word.toString().equals(SLNode.POS_INF) && height != 1) removeLayer();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        // prints the list out to a string such that all levels of the list can be viewed

        SLNode currentPos, nextPos;
        // set currentPos to the sentinel pillar
        currentPos = head;

        // while there is a lower pillar, go down a level
        while(currentPos.getDown() != null)
        {
            currentPos = currentPos.getDown();
        }

        // move currentPos to the right one node
        currentPos = currentPos.getRight();

        while(!currentPos.word.toString().equals(SLNode.POS_INF))
        {
            nextPos = currentPos;
            do
            {
                System.out.print(nextPos.word + " ");
                nextPos = nextPos.getUp();
            }
            while(nextPos != null);
            System.out.println();
            currentPos = currentPos.getRight();
        }
        return "List Printed.";
    }

    @Override
    public void reset()
    {
        // resets the currentPos such that it points to the start of the list (top left sentinel node)
        currentPos = head;
    }

    @Override
    public void collectData()
    {
        System.out.println("Height:" + height);
        // collects node data from the skip list.
        while(currentPos.getDown() != null)
        {
            currentPos = currentPos.getDown();
        }

        while (!currentPos.getRight().word.toString().equals(SLNode.POS_INF))
        {
            currentPos = currentPos.getRight();
            nodeCount++;
            wordCount += currentPos.freq;
        }
    }

    @Override
    public long getComparisons()
    {
        return comparisons;
    }

    @Override
    public int getReferenceChangeCount()
    {
        return referenceChangeCount;
    }
}
