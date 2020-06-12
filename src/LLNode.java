
public class LLNode<T>
{
	private T info;
	private int frequency;
	private LLNode link;
	
	public LLNode(T info)
	{
		this.info = info;
		link = null;
		frequency = 1;
	}
	
	public void setInfo(T Info)
	{
		this.info = info;
	}
	
	public T getInfo()
	{
		return info;
	}
	
	public void setLink(LLNode link)
	{
		this.link = link;
	}
	
	public LLNode getLink()
	{
		return link;
	}
	
	public int getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(int newNodeCount)
	{
		frequency = newNodeCount;
	}
}
