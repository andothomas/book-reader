/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/29/2019

SaveStats contains one function, saveStats(), which prints the collected statistics out to a csv file.
-   -   -   -   -   -   -
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveStats
{
	// this saves the general statistics for all lists to a .csv file
	
	private static PrintWriter outFile;
	private static String statsName;
	
	public static void saveStats(ListStats[][] statsArr, String[] FILE_NAMES, boolean compile) throws IOException
	{
		/*
		statsArr is either the decompileStats array or compileStats array, FILE_NAMES is the String array of
		all of the files used, and compile determines whether the file is the statistics on the decompiling behavior or
		the compiling behavior of each list.
		*/
		if(compile)
		{
			statsName = "compile";
		}
		else
		{
			statsName = "decompile";
		}
		outFile = new PrintWriter(new FileWriter(statsName + "-stats.csv"));

		for(String name: MainClass.LIST_NAMES)
		{
			outFile.printf(",%s,,,,,",name);
		}
		outFile.println();

		for(String name:MainClass.LIST_NAMES)
		{
			outFile.print(",Total Word Count,Unique Word Count,Comparison Count,Reference Change Count,Elapsed Time,");
		}
		outFile.println();

		for(int i = 0; i < FILE_NAMES.length; i++)
		{
			outFile.print(FILE_NAMES[i]);
			for(int j = 0; j < MainClass.LIST_NAMES.length; j++)
			{
				outFile.printf(",%d,%d,%d,%d,%f,",
						MainClass.compileStats[i][j+1].wordCount, MainClass.compileStats[i][j+1].nodeCount,
						MainClass.compileStats[i][j+1].comparisonCount,
						MainClass.compileStats[i][j+1].referenceCount, MainClass.compileStats[i][j+1].elapsed);
			}
			outFile.println();
		}
		outFile.close();
		System.out.printf("Statistics saved to \"%s-stats.csv\".%n", statsName);
	}
}