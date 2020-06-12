/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/18/2019

SLNode is the node class for the skip list, differing from a regular linked list node such that each node has an
up, down, left, and right link. Nodes can also be configured to act as sentinel "marker" nodes.
-   -   -   -   -   -   -
 */
public class SLNode<T>
{
    // trying with all of the access modifiers "public" this time.
    public T word;
    public int freq;

    private SLNode up, down, left, right;
    private SLNode link;
    public static final String NEG_INF = "negInf";
    public static final String POS_INF = "posInf";

    protected SLNode currentPos;


    public SLNode(T word)
    {
        this.word = word;
        up = null;
        down = null;
        left = null;
        right = null;
        link = right;
        freq = 1;
    }
    public void setUp(SLNode up)
    {
        this.up = up;
    }

    public SLNode getUp()
    {
        return up;
    }

    public void setDown(SLNode down)
    {
        this.down = down;
    }

    public SLNode getDown()
    {
        return down;
    }

    public void setLeft(SLNode left)
    {
        this.left = left;
    }

    public SLNode getLeft()
    {
        return left;
    }

    public void setRight(SLNode right)
    {
        this.right = right;
    }

    public SLNode getRight()
    {
        return right;
    }

    public SLNode getLink()
    {
        SLNode next = getRight();
        if (next.word.toString() == POS_INF) return null;
        return getRight();
    }
}
