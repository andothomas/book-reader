public class LLHNode<T> extends LLNode<T>  implements Hashable<T>
{
    int hashFunc;
    public LLHNode(T info, int hashFunc)
    {
        super(info);
        this.hashFunc = hashFunc;
    }

    @Override
    public int hash()
    {
        double hash = 0;
        String currentWord = this.getInfo().toString();

        switch (hashFunc)
        {
            // hashing method 1.
            case 1:
            {
                for(int i = 0; i < currentWord.length(); i++)
                {
                    // charAt(i) returns the Unicode value of the character at index i of currentWord
                    hash += currentWord.charAt(i);
                }
                hash = hash % 256;
                break;
            }

            // hashing method 2.
            case 2:
            {
                hash += currentWord.charAt(0);
                break;
            }

            // hashing method 3.
            case 3:
            {
                for (int i = 0; i < currentWord.length(); i++)
                    hash = (hash * 31) + currentWord.charAt(i);
                hash = hash % 256;
                break;
            }
        }
        return (int) hash;
    }
}
