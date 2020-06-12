
/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/27/2019

Hashable is an interface which only has one method, hash(), which leaves the actual function of it up to whichever list
implements it.
-   -   -   -   -   -   -
 */

public interface Hashable<T>
{
    // Objects of classes that implement this interface can be used with lists based on hashing
    // This interface is used with the LLNode class, so that nodes can be hashed to determine their
    // appropriate list location.

    int hash();
    // Hash is a mathematical function used to manipulate the key of an element in a list
    // to identify its location in the list

}
