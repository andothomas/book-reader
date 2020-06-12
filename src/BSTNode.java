/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 12/11/2019

BSTNode is the node class for the binary search tree list, differing from a regular linked list node such that
each node has a parent, left child, and right child link. The root node will always have a null parent link, while the
lowest children in the list will have null references to their LCH and RCH links.
-   -   -   -   -   -   -
 */
public class BSTNode<T>
{
    public T info;
    private int freq;
    // LCH and RCH are the left child and right child of the given node (respectively)
    private BSTNode lCH, rCH;
    private BSTNode parent;

    public BSTNode(T info)
    {
        this.info = info;
        freq = 1;
        lCH = null;
        rCH = null;
        parent = null;
    }

    public void setInfo(T info)
    {
        this.info = info;
    }

    public T getInfo()
    {
        return info;
    }

    public BSTNode getParent()
    {
        return parent;
    }

    public void setParent(BSTNode parent)
    {
        this.parent = parent;
    }

    public BSTNode getLCH()
    {
        return lCH;
    }

    public void setLCH(BSTNode lCH)
    {
        this.lCH = lCH;
    }

    public BSTNode getRCH()
    {
        return rCH;
    }

    public void setRCH(BSTNode rCH)
    {
        this.rCH = rCH;
    }

    public int getFreq()
    {
        return freq;
    }

    public void incrementFreq()
    {
        freq++;
    }
}
