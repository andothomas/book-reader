/*
-   -   -   -   -   -   -
Written by Andrew Thomas
& Created on 11/07/2019

WordStats prints the first 100 nodes for each list and stores it in a csv file.
-   -   -   -   -   -   -
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WordStats
{
	// this saves the frequency for the first 100 nodes of top-sort and shift-up sort lists to a .csv file
	private static PrintWriter outFile;
	
	public static void wordStats(String[] FILE_NAMES) throws IOException
	{
		outFile = new PrintWriter(new FileWriter("word_statistics.csv"));
		outFile.println(FILE_NAMES[0]);
		outFile.println("FrontSort List");
		outFile.println("Word, Frequency");
		for(int i = 0; i< MainClass.wordFreq3.length-1; i++)
		{
			outFile.printf("%s,%s\n", MainClass.wordFreq3[i][0], MainClass.wordFreq3[i][1]);
		}
		outFile.println();
		
		outFile.println("MicroSort List");
		outFile.println("Word, Frequency");
		for(int i = 0; i< MainClass.wordFreq4.length-1; i++)
		{
			outFile.printf("%s,%s\n", MainClass.wordFreq4[i][0], MainClass.wordFreq4[i][1]);
		}
		outFile.println();
		
		outFile.close();
		System.out.println("Statistics saved to \"word_statistics.csv\".");
	}
}
