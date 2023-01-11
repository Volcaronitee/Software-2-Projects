import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jared Koharik and Allen Zheng
 *
 */
public final class TagCloudGeneratorJava {

    /**
     *
     * @author jakoh
     *
     */
    private static class NumberCompare implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {

            return o2.compareTo(o1);
        }

    }

    /**
     *
     * @author jakoh
     *
     */
    private static class WordCompare implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {

            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }

    }

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorJava() {
    }

    /**
     * Prepares html file for tag cloud input.
     *
     * @param fileOut
     *            Uses {@code fileOut} to output to an html file.
     * @param n
     *            top word count
     * @param inputFileName
     *            Name of the given input file.
     * @param orderedWords
     *            words in alphabetical order.
     * @param cutMap
     *            top n words with highest count
     * @param minSize
     *            minimum font size
     * @param maxSize
     *            maximum font size
     */
    public static void setUpFileOutput(PrintWriter fileOut, int n,
            String inputFileName, List<String> orderedWords,
            Map<String, Integer> cutMap, int minSize, int maxSize) {
        // Heading
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println(
                "<title>Top " + n + " words in " + inputFileName + "</title>");
        fileOut.println("<link href=\"http://web.cse.ohio-state.edu/software/"
                + "2231/web-sw2/assignments/projects/tag-cloud-generator/data"
                + "/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println("</head>");
        // Body
        fileOut.println("<body>");
        fileOut.println("<h2>");
        fileOut.println("Top " + n + " words in " + inputFileName + "</h2>");
        fileOut.println("<hr>");
        fileOut.println("<div class=\"cdiv\">");
        fileOut.println("<p class=\"cbox\">");

        while (orderedWords.size() > 0) {
            final int maxFont = 37;
            final int minFont = 11;

            String word = orderedWords.remove(0);
            int wordSize = cutMap.get(word);
            int wordFont = minFont;
            if (maxSize == minSize) {
                wordFont = (maxFont * (wordSize - minSize)) + minFont;
            } else {
                wordFont = (maxFont * (wordSize - minSize)
                        / (maxSize - minSize)) + minFont;
            }

            fileOut.println("<span style=\"cursor:default\" class=\"f"
                    + wordFont + "\" title=\"" + "count:" + wordSize + "\">"
                    + word + "</span>");
        }

        fileOut.println("</p>");
        fileOut.println("</div>");

        // Ending
        fileOut.println("</body>");
        fileOut.println("</html>");
    }

    /**
     * Takes in a file of words and counts them, then returns a map that
     * contains each word and their amount of occurrences.
     *
     * @param inputFile
     *            {@code SimpleReader} used to read imput file.
     *
     * @return Map with words and their amount of occurrences.
     */
    public static Map<String, Integer> countWords(BufferedReader inputFile) {
        Map<String, Integer> uniques = new HashMap<>();

        try {
            String line = inputFile.readLine();

            while (line != null) {
                line = line.replaceAll("\\W", " ").replaceAll("_", " ");
                String[] words = line.split(" ");

                for (String word : words) {
                    if (!uniques.containsKey(word) && word.length() > 0) {
                        uniques.put(word, 1);
                    } else if (word.length() > 0) {
                        uniques.replace(word, uniques.get(word) + 1);

                    }

                    line = inputFile.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file!");

        }

        return uniques;
    }

    /**
     * Takes in a map and removes and returns the pair with the highest value.
     *
     * @param wordsAndAmounts
     *            Map where pair is to be removed.
     * @updates wordsAndAmounts
     * @ensures wordsAndAmounts = #wordsAndAmounts - Pair with highest int
     *          value.
     * @return Pair in map with the largest value.
     */
    public static Map.Entry<String, Integer> returnMostCommonPair(
            Map<String, Integer> wordsAndAmounts) {

        int largestSize = 0;
        String mostCommonWord = "";

        Map.Entry<String, Integer> tempPair = null;
        Map.Entry<String, Integer> finalPair = null;

        Set<Map.Entry<String, Integer>> entrySet = wordsAndAmounts.entrySet();

        for (Map.Entry<String, Integer> currentEntry : entrySet) {
            tempPair = currentEntry;
            if (tempPair.getValue() > largestSize) {
                mostCommonWord = tempPair.getKey();
                largestSize = tempPair.getValue();
                finalPair = tempPair;
            }
        }

        wordsAndAmounts.remove(mostCommonWord);

        return finalPair;

    }

    /**
     * Takes in a map of words and their occurrences, and then returns a new map
     * of the top n common words.
     *
     * @param wordsAndAmounts
     *            Map of all words and their occurrences in a file.
     * @param n
     *            Amount of desired words to be put in the tag cloud.
     * @clears wordsAndAmounts
     * @return New map that has the top desired words.
     */
    public static Map<String, Integer> removeTopWords(
            Map<String, Integer> wordsAndAmounts, int n) {

        int i = n;

        Map<String, Integer> newMap = new HashMap<>();
        Map.Entry<String, Integer> p = null;

        while (i > 0) {
            p = returnMostCommonPair(wordsAndAmounts);
            newMap.put(p.getKey(), p.getValue());
            i--;
        }

        wordsAndAmounts.clear();

        return newMap;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // Read user input for file information
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        int n = 2;
        String inputFileName = null;
        String outputFileName = null;

        try {

            System.out.print("Enter input file name: ");
            inputFileName = in.readLine();
            System.out.print("Enter output file name: ");
            outputFileName = in.readLine();
            System.out.print("Enter desired number of words: ");
            n = Integer.parseInt(in.readLine());
            if (n < 2) {
                n = 2;
            }
        } catch (IOException e) {
            System.err.println("Error reading keyboard input");
        }

        BufferedReader inputFile = null;

        PrintWriter fileOut = null;

        // Creating files
        try {
            inputFile = new BufferedReader(new FileReader(inputFileName));
            fileOut = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFileName)));
        } catch (IOException e) {
            System.err.println("Problem opening file.");
        }

        // Get top words and counts
        Map<String, Integer> wordsAndAmounts = countWords(inputFile);

        if (n > wordsAndAmounts.size()) {
            n = wordsAndAmounts.size();
        }

        Map<String, Integer> cutMap = removeTopWords(wordsAndAmounts, n);

        Comparator<String> cS = new WordCompare();

        Set<Map.Entry<String, Integer>> entrySet = cutMap.entrySet();

        List<String> wordList = new ArrayList<>();

        List<Integer> numberList = new ArrayList<>();

        for (Map.Entry<String, Integer> pair : entrySet) {
            wordList.add(pair.getKey());
        }
        Collections.sort(wordList, cS);

        Comparator<Integer> cN = new NumberCompare();

        for (Map.Entry<String, Integer> pair : entrySet) {
            numberList.add(pair.getValue());
        }

        Collections.sort(numberList, cN);

        // Get max and min font size
        int maxSize = 0;
        int minSize = 0;

        maxSize = numberList.remove(0);

        while (numberList.size() > 1) {
            numberList.remove(0);
        }

        minSize = numberList.remove(0);

        // html page setup and close inputs/outputs
        setUpFileOutput(fileOut, n, inputFileName, wordList, cutMap, minSize,
                maxSize);

        try {
            in.close();
            inputFile.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Problem closing files.");
        }
    }

}
