
public class ListStats
{
	// class for an object containing various statistic information.
	// Intended for one object to be used per list type, per document run for all general statistics data
	String listSelection;
	int wordCount;
	int nodeCount;
	long comparisonCount;
	int referenceCount;
	double elapsed;
	
	public ListStats()
	{
		listSelection = null;
		wordCount = 0;
		nodeCount = 0;
		comparisonCount = 0;
		referenceCount = 0;
		elapsed = -1;
	}
	
	public ListStats(String listSelection, int wordCount, int nodeCount,
			int comparisonCount, int referenceCount, long elapsed)
	{
		this.listSelection = listSelection;
		this.wordCount = wordCount;
		this.nodeCount = nodeCount;
		this.comparisonCount = comparisonCount;
		this.referenceCount = referenceCount;
		this.elapsed = elapsed;
	}
}
