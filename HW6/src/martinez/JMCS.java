package martinez;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class JMCS {
	
	//DATA MEMBERS
	private File inputFile;
	private ArrayList<String> catalog;

	//CONSTRUCTOR
	public JMCS(File inputFile) {
		this.inputFile = inputFile;
		catalog = new ArrayList<>();
	}

	
	public void buildCatalog() {

		// CREATE A SCANNER TO READ THE FILE
		Scanner fileInputScan = null;
		
		//OPENS THE SCANNER TO THE INPUT FILE AND SEARCHES FOR CATALOG ELEMENTS
		try {
			fileInputScan = new Scanner(inputFile);
			search(fileInputScan);

		} catch (FileNotFoundException e) {
			System.out.println("Error - This file could not be found.");
		} finally {
			if (fileInputScan != null)
				fileInputScan.close();
		}

	}

	// SEARCH UTILITY: FINDS CLASSES, METHODS, AND CONTROL STRUCTURES
	private void search(Scanner fileInputScan) {

		catalog = new ArrayList<String>();
		Stack<String> stack = new Stack<>();
		String codeLine = "";
		String previousCodeLine = ""; // Possible Method, Class, or Structure
		

		while (fileInputScan.hasNextLine()) {
			// TASK 1: GRAB A SINGLE LINE OF CODE AND TRIM WHITESPACE FROM FRONT
			codeLine = fileInputScan.nextLine();
			codeLine = codeLine.trim();

			// TASK 2: CHECK IF THE CODELINE IS THE START OF AN MCS.
			if (codeLine.length() > 0) {
				if (!codeLine.equals("{") && !codeLine.equals("}"))
					previousCodeLine = codeLine;
				else if (codeLine.equals("{"))
					stack.push(previousCodeLine);
				else {
					catalog.add(0, stack.pop());
				}
			}
		}
	}
	
	public String toString() {
		String MCSString = "";
		for (int i = 0; i < catalog.size(); i++) {
			MCSString += catalog.get(i) + "\n";
		}
		return MCSString;
	}


	private boolean isCode(String str) {
	    // Trim whitespace from the string
	    str = str.trim();

	    // Check if the line is empty or a comment
	    if (str.isEmpty() || str.startsWith("//")) {
	        return false; // Ignore empty lines and single-line comments
	    }

	    // Define a simple set of keywords that might indicate valid code
	    String[] keywords = {
	        "public", "private", "protected", "class", "void", "if", "else",
	        "for", "while", "return", "static", "int", "String", "double",
	        "boolean", "try", "catch", "finally", "throw", "throws", "new"
	    };

	    // Check if the line contains any keywords
	    for (String keyword : keywords) {
	        if (str.contains(keyword)) {
	            return true; // If it contains a keyword, consider it valid code
	        }
	    }

	    // Additional checks can be added here as needed
	    return false; // If no conditions matched, consider it invalid
	}




}
