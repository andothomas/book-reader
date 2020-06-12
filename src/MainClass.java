/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/07/2019

MainClass contains every instance of the lists used (the names of which are stored in LIST_NAMES for statistics' sake,
the actual instances created in createLists(), the arrays for which list compiling and decompiling statistics can
be exported, and the names of the files to be used.

Boolean SINGLE_FILE determines whether a single file or multiple files will be iterated through and the results
output to the console. List statistics will only be output to a .csv file when SINGLE_FILE is false.

For each case (SINGLE_FILE = true and SINGLE_FILE = false), the list of data structures, list_arr, is iterated through,
and each list is compiled (with statistics collected), and decompiled (also with statistics collected).
-   -   -   -   -   -   -
 */
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainClass
{
	// The lists that the compiler will use to read the document(s). All will be used.
	final static String[] LIST_NAMES = new String[] {
//			"unsortedList",
//			 "alphabeticalList",
//			 "alphabeticalListV2",
//			"topSortList1",
//			"topSortList2",
//			 "microSortList",
			"skipList",
//			"sumHashList",
//			"firstCharHashList",
			"sequenceHashList",
			"bstList"
	};

	// -------------------------------------------------------------------------------------------------------
	// ------------------------------------ Start of Multi-file declarations ------------------------------------

	// Array containing all lists
	private static StructuredList[] list_arr;

	// for a multi-file run, and for gathering data on the lists. Works for any number of documents
	private final static String PATH_DIR = "rsc/test-files/";
	private final static String[] FILE_NAMES = new String[]
			{
//			"A Bee Movie Script.txt",
//			"Anna Karenina - Tolstoy.txt" ,
//			"Bleak House - Dickens.txt" ,
//			"David Copperfield - Dickens.txt" ,
//			"Don Quixote - Cervantes.txt" ,
//			"Green Eggs and Ham.txt" ,
//			"Hamlet.txt" ,
//			"Hamlet-Scene-1.txt" ,
//			"King James Bible.txt" ,
//			"Les Miserables - Hugo.txt" ,
//			"Middlemarch - Eliot.txt" ,
//			"Moby Dick - Melville.txt" ,
//			"Shakespeare.txt" ,
//			"The Brothers Karamazof - Dostoyevsky.txt" ,
//			"The Hunchback of Notre Dame - Hugo.txt" ,
//			"The Three Musketeers - Dumas.txt" ,
//			"To Kill a Mockingbird - Harper Lee.txt" ,
//			 "War and Peace.txt",

			"King James Bible.txt" ,
			"All.txt",
			"Text-01M.txt",
			"Text-02M.txt",
			"Text-05M.txt",
			"Text-10M.txt",
			"Text-20M.txt",
			};

	
	// arrays for gathering compiling/decompiling statistics. Does not include word frequencies for the first 100 nodes.
	static ListStats[][] compileStats = new ListStats[FILE_NAMES.length][12];
	static ListStats[][] decompileStats = new ListStats[FILE_NAMES.length][12];

	// these are for the word frequencies of the top-sort and shift-up sort lists. Not collected by default.
	static String[][] wordFreq3 = new String[102][2];
	static String[][] wordFreq4 = new String[102][2];

	// ------------------------------------- End of Multi-file declarations -------------------------------------
	// -------------------------------------------------------------------------------------------------------

	//  ##########################################
	//  ####### FILE_NAME IS LOCATED HERE ########
	//  ##########################################
	final static String FILE_NAME = "rsc/test-files/hamlet.txt";

	public static void main(String[]args) throws FileNotFoundException
	{
		// either run a single file, or a list of predefined files
		final boolean SINGLE_FILE = false;

		// will collect statistics on lists if set to true
		final boolean ENABLE_METRICS = true;
		int runCount = 0;

		// if changing any of the lists being run, change these, LIST_ARR, and ALL_LISTS
		createLists();

		if(SINGLE_FILE)
		{
			Compiler organizer = new Compiler(ENABLE_METRICS,runCount);

			// make initial passes to fill buffers & time
			organizer.initialize(FILE_NAME, list_arr);

			for(int list = 0; list < list_arr.length; list++)
			{
				// "compile this list by reading this book"
				organizer.readBook(FILE_NAME, list_arr,list,true);

				// "decompile this list by reading this book"
				//organizer.readBook(FILE_NAME,list_arr,list,false);
			}

			System.out.println("Finished.");
		}
		else
		{
			// I don't know how to iterate through files in a folder,
			// so this is something I could expand upon later.
			// only this can print to the CSV file.

			String file_name;
			System.out.printf("Processing %d files...\n",FILE_NAMES.length);
			
			for(String book:FILE_NAMES)
			{
				// if changing any of the lists being run, change these, LIST_ARR, and ALL_LISTS
				createLists();
				Compiler organizer = new Compiler(ENABLE_METRICS,runCount);
				file_name = PATH_DIR + book;

				// make initial passes to fill buffers & time
				organizer.initialize(file_name, list_arr);

				for(int list=0; list < list_arr.length; list++)
				{
					// "compile this list by reading this book"
					organizer.readBook(file_name,list_arr,list,true);

					// "decompile this list by reading this book"
					//organizer.readBook(file_name,list_arr,list,false);
				}

				runCount++;
			}
			if(ENABLE_METRICS)
			{
				try
				{
					SaveStats.saveStats(compileStats,FILE_NAMES, true);
					//SaveStats.saveStats(decompileStats, FILE_NAMES, false);
					//WordStats.wordStats(FILE_NAMES);
				} catch (IOException e)
				{
					System.out.println("Unable to save generated stats. IOException caught.");
					e.printStackTrace();
				}
			}
		}
	}

	private static void createLists() {
		// if changing any of the lists being run, LIST_ARR, and ALL_LISTS
		// instantiates each list and places them all in an array of type StructuredList.

		UnsortedList<String> unsortedList = new UnsortedList<>();
		AlphabeticalList<String> alphabeticalList = new AlphabeticalList<>();
		TopSortList1<String> topSortList1 = new TopSortList1<>();
		TopSortList2<String> topSortList2 = new TopSortList2<>();
		MicroSortList<String> microSortList = new MicroSortList<>();
		AlphabeticalListV2<String> alphabeticalListV2 = new AlphabeticalListV2<>();
		SkipList<String> skipList = new SkipList<>();
		HashList<String> sumHashList = new HashList<>(256, 1);
		HashList<String> firstCharHashList = new HashList<>(256, 2);
		HashList<String> sequenceHashList = new HashList<>(256, 3);
		BSTList<String> bstList = new BSTList<>();

		list_arr = new StructuredList[] {
//				unsortedList,
//				alphabeticalList,
//				alphabeticalListV2,
//				topSortList1,
//				topSortList2,
//				microSortList,
				skipList,
//				sumHashList,
//				firstCharHashList,
				sequenceHashList,
				bstList
		};
	}
}