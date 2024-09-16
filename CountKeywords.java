//created by Elijah Gonzalez, September 16th 2024.

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//programs checks to see if certain keywords are in an input file.
// if they are in a comment or in a string, they get excluded
public class CountKeywords {
    public static void main(String[] args) throws Exception {
        //we must scan
        Scanner input = new Scanner(System.in);

        //ask user for the source file

        System.out.print("Enter a Java source file: ");

        //we read the next line the user inuts, which should be the file name
        String filename = input.nextLine();

        //file object with the same name
        File file = new File(filename);

        //hopefully it exists
        if (file.exists()) {

            //if the file does exist, we ball. this tells the user how many keywords are there.
            System.out.println("The number of keywords in " + filename
                + " is " + countKeywords(file));
                
        } else {
            //if the file does not exist we let them know
            System.out.println("File " + filename + " does not exist");
        }
    }

    //this is how we determine if the keywords are present or not.
    public static int countKeywords(File file) throws Exception {

        //these are the words we are looking out for
        String[] keywordString = {"abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum",
            "extends", "for", "final", "finally", "float", "goto",
            "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private",
            "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile",
            "while", "true", "false", "null"};

        //this is how we find them quickly
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));

        //we gotta make an int to count them so we do that here
        int count = 0; 

        //we use BufferedReader to read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            //this is how we look for if anytrhing is inside of a string, if it is, we exclude it.
            Pattern quotePattern = Pattern.compile("\"[^\"]*\"");
            while ((line = br.readLine()) != null) {
                
                //this is how we check if the code is inside of a comment
                if (line.trim().startsWith("//")) {

                    //we skip over it if it is!
                    continue;
                }

                //we take out all of the content inside of the quote. then...
                Matcher matcher = quotePattern.matcher(line);
                line = matcher.replaceAll("");

                //we scan the rest here to look for keywords
                Scanner lineScanner = new Scanner(line);
                while (lineScanner.hasNext()) {
                    String word = lineScanner.next();

                    //if one is, we add it to the count!
                    if (keywordSet.contains(word)) {

                        //this just means count = count +1, or add 1 for everytime we find a keywor
                        count++;
                    }
                }
                //close count because its polite to
                lineScanner.close();
            }
        }
        
        //return the count for earlier!
        return count;
    }
}