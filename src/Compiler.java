import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

// list building and statistics collecting is performed here.
public class Compiler
{
	private final static String WORD_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
	private final String[] LIST_NAMES = MainClass.LIST_NAMES;
	public long elapsed;
	private boolean ENABLE_METRICS;
	private int runCount;

	Compiler(boolean ENABLE_METRICS, int runCount)
	{
		this.ENABLE_METRICS = ENABLE_METRICS;
		this.runCount = runCount; // this keeps track of the number of documents that have been read. started at 0.
	}

	// initialize runs through once, closes, reopens, times, and closes again
	void initialize(String FILE_NAME, StructuredList<String>[] list_arr) throws FileNotFoundException
	{
		int list = -2;
		System.out.println();
		System.out.println("File: " + FILE_NAME);
		// no timer for first run through

		readBook(FILE_NAME,list_arr,list,true);

		// timed test starts here
		//start timer here
		elapsed = System.currentTimeMillis();
		list = -1;

		readBook(FILE_NAME,list_arr,list, true);
	}

	// dual purpose. passed a boolean "compile" to tell whether readBook should call compile() or decompile()
	// when it finds a word
	void readBook(String FILE_NAME, StructuredList<String>[] list_arr, int list, boolean compile) throws FileNotFoundException
	{
		//list_arr[list].resetStats();

		elapsed = System.currentTimeMillis();
		FileReader docIn = new FileReader(FILE_NAME);
		Scanner wordIn = new Scanner(docIn);
		
		while(wordIn.hasNext())
		{
			// get next word
			String nextWord = wordIn.next();
			
			// check to make sure word does not contain a hyphen or double dash
			// if so, tokenize it by the dash and process both new words
			
			// FIRST NEED TO CHECK IF nextWord IS A WORD
			nextWord = prepWord(nextWord);


			/*
			if(nextWord.contains("--"))
			{
				String[] splitWord = nextWord.split("--");
				
				//for however many words the split creates, but it should only create two
				nextWord = splitWord[0];
				nextWord = prepWord(nextWord);
				
				// if the word is of length 1, check to see if it's actually a word.
				if(nextWord.length() == 1)
				{
					// if it's a valid one letter word and we're not on the initial run, put it in a list
					if(WORD_CHARS.contains(nextWord)) listMutator(nextWord, list_arr, list, compile);
				}
				else
				{
					listMutator(nextWord, list_arr, list, compile);
				}
				
				String loopWord;
				for(int i = 1; i < splitWord.length; i++)
				{
					loopWord = splitWord[i];
					loopWord = prepWord(loopWord);
					
					// if the word is of length 1, check to see if it's actually a word.
					if(loopWord.length() == 1)
					{
						if(WORD_CHARS.contains(loopWord)) listMutator(loopWord, list_arr, list, compile);
					}
					else
					{
						listMutator(loopWord, list_arr, list, compile);
					}
				}
			}
			else*/
			{
				// if the word is of length 1, check to see if it's actually a word.
				if(nextWord.length() >= 1)
				{
					listMutator(nextWord, list_arr, list, compile);
				}
			}
		}
		wordIn.close();

		if(ENABLE_METRICS)
		{
			// insert conditional and another method for performance metrics on decompiling
			performanceMetrics(list_arr, list, compile);
		}
	}

	private String prepWord(String nextWord)
	{
		//trim the word and make everything lowercase
		nextWord = nextWord.toLowerCase();
		
		// remove any unwanted characters, both before the string and after.
		while(!WORD_CHARS.contains(nextWord.substring(0, 1)) && nextWord.length() > 1)
		{
			nextWord = nextWord.substring(1);
		}

		while(nextWord.length() > 0 && !WORD_CHARS.contains(nextWord.substring(nextWord.length()-1)))
		{
			nextWord = nextWord.substring(0, nextWord.length()-1);
		}
		return nextWord;
	}

	// chooses whether the given word should be added or removed from a list in list_arr,
	private void listMutator(String word, StructuredList<String>[] list_arr, int list, boolean compile)
	{
		if (list < 0) return;
		if(compile)
		{
			list_arr[list].add(word);
		}
		else
		{
			// does not check if list_arr[list].list is null
			list_arr[list].remove(word);
		}
	}

	// prints metrics for both compiling and decompiling lists
	private void performanceMetrics(StructuredList<String>[] list_arr, int list, boolean compile)
	{
		int nodeCount = 0;
		int wordCount = 0;
		long comparisonCount = 0;
		int referenceCount = 0;

		if(list == -2)
		{
			// simply running through, no list, thus nothing is in this case
			System.out.println("-------------------------------------------");
			System.out.println("Filled buffers.");
		}
		else if(list == -1)
		{
			//end timer here
			elapsed = System.currentTimeMillis() - elapsed;

			System.out.println("-------------------------------------------");
			System.out.println("Timed Test:");
			System.out.println("Elapsed time: " + elapsed/1000.0 + " seconds.");
			System.out.println("-------------------------------------------");

			// storing the stats to an array of objects to be stored in a csv file
			MainClass.compileStats[runCount][0] = new ListStats();
			MainClass.compileStats[runCount][0].elapsed = elapsed/1000.0;

		}
		else
		{
			//end timer here
			elapsed = System.currentTimeMillis() - elapsed;
			// using list 1, the unsorted list.

			// total word count and unique word count
			list_arr[list].reset();

			if(compile)
			{
				list_arr[list].collectData();
				wordCount = list_arr[list].wordCount;
				nodeCount = list_arr[list].nodeCount;
			}

			// get the list comparisons
			comparisonCount = list_arr[list].getComparisons();
			// get the reference change count
			referenceCount = list_arr[list].getReferenceChangeCount();

			if (compile) System.out.printf("Compiling %s:%n",LIST_NAMES[list]);
			else System.out.printf("Decompiling %s:%n",LIST_NAMES[list]);

			System.out.println("Total number of words: " + wordCount);
			System.out.println("Number of unique words: " + nodeCount);
			System.out.println("Number of comparisons: " + comparisonCount);
			System.out.println("Number of Reference Changes: " + referenceCount);
			System.out.println("Elapsed time: " + elapsed/1000.0 + " seconds.");
			//System.out.println(list_arr[list].toString());
			System.out.println("-------------------------------------------");

			if(compile)
			{
				// storing the stats to an array of objects to be stored in a csv file
				MainClass.compileStats[runCount][list+1] = new ListStats();
				MainClass.compileStats[runCount][list+1].nodeCount = nodeCount;
				MainClass.compileStats[runCount][list+1].wordCount = wordCount;
				MainClass.compileStats[runCount][list+1].comparisonCount = comparisonCount;
				MainClass.compileStats[runCount][list+1].referenceCount = referenceCount;
				MainClass.compileStats[runCount][list+1].elapsed = elapsed/1000.0;
			}
			else
			{
				MainClass.decompileStats[runCount][0] = new ListStats();
				MainClass.decompileStats[runCount][0].elapsed = 000;

				// storing the stats to an array of objects to be stored in a csv file
				MainClass.decompileStats[runCount][list+1] = new ListStats();
				MainClass.decompileStats[runCount][list+1].nodeCount = nodeCount;
				MainClass.decompileStats[runCount][list+1].wordCount = wordCount;
				MainClass.decompileStats[runCount][list+1].comparisonCount = comparisonCount;
				MainClass.decompileStats[runCount][list+1].referenceCount = referenceCount;
				MainClass.decompileStats[runCount][list+1].elapsed = elapsed/1000.0;
			}
		}
	}
}
