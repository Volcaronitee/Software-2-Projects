import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Jared Koharik and Allen Zheng
 *
 */
public final class TagCloudGenerator {

    /**
     *
     * @author jakoh
     *
     */
    private static class NumberCompare implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {

            int compareValue = 0;

            if (o1 > o2) {
                compareValue = -1;
            } else if (o2 > o1) {
                compareValue = 1;
            }

            return compareValue;
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
    private TagCloudGenerator() {
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
     */
    public static void setUpFileOutput(SimpleWriter fileOut, int n,
            String inputFileName, SortingMachine<String> orderedWords,
            Map<String, Integer> cutMap, int minSize, int maxSize) {
        // Heading
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println(
                "<title>Top " + n + " words in " + inputFileName + "</title>");
        fileOut.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println("</head>");
        // Body
        fileOut.println("<body>");
        fileOut.println("<h2>");
        fileOut.println("Top " + n + " words in " + inputFileName + "</h2>");
        fileOut.println("<hr>");
        fileOut.println("<div class=\"cdiv\">");
        fileOut.println("<p class=\"cbox\">");

        while (orderedWords.size() > 0) {
            String word = orderedWords.removeFirst();
            int wordSize = cutMap.value(word);
            int wordFont = (37 * (wordSize - minSize) / (maxSize - minSize))
                    + 11;
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
    public static Map<String, Integer> countWords(SimpleReader inputFile) {
        Map<String, Integer> uniques = new Map1L<>();

        while (!inputFile.atEOS()) {
            String line = inputFile.nextLine().replaceAll("\\W", " ");
            line = line.replaceAll("_", " ");
            String[] words = line.split(" ");

            for (String word : words) {
                if (!uniques.hasKey(word) && word.length() > 0) {
                    uniques.add(word, 1);
                } else if (word.length() > 0) {
                    uniques.replaceValue(word, uniques.value(word) + 1);
                }
            }
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
    public static Map.Pair<String, Integer> returnMostCommonPair(
            Map<String, Integer> wordsAndAmounts) {

        int largestSize = 0;
        String mostCommonWord = "";

        Map.Pair<String, Integer> tempPair = null;
        Map.Pair<String, Integer> finalPair = null;

        Map<String, Integer> tempMap = wordsAndAmounts.newInstance();

        while (wordsAndAmounts.size() > 0) {
            tempPair = wordsAndAmounts.removeAny();
            tempMap.add(tempPair.key(), tempPair.value());
            if (tempPair.value() > largestSize) {
                mostCommonWord = tempPair.key();
                largestSize = tempPair.value();
            }
        }

        while (tempMap.size() > 0) {
            tempPair = tempMap.removeAny();
            if (!tempPair.key().equals(mostCommonWord)) {
                wordsAndAmounts.add(tempPair.key(), tempPair.value());
            } else {
                finalPair = tempPair;
            }
        }

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

        Map<String, Integer> newMap = wordsAndAmounts.newInstance();
        Map.Pair<String, Integer> p = null;

        while (i > 0) {
            p = returnMostCommonPair(wordsAndAmounts);
            newMap.add(p.key(), p.value());
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.println("Enter input file name:");
        String inputFileName = in.nextLine();
        out.println("Enter output file name:");
        String outputFileName = in.nextLine();
        out.println("Enter desired number of words");
        int n = in.nextInteger();

        SimpleReader inputFile = new SimpleReader1L(inputFileName);
        SimpleWriter fileOut = new SimpleWriter1L(outputFileName);

        Map<String, Integer> wordsAndAmounts = countWords(inputFile);
        Map<String, Integer> cutMap = removeTopWords(wordsAndAmounts, n);

        Comparator<String> cS = new WordCompare();
        SortingMachine<String> smS = new SortingMachine1L<>(cS);

        for (Map.Pair<String, Integer> pair : cutMap) {
            smS.add(pair.key());
        }
        smS.changeToExtractionMode();

        Comparator<Integer> cN = new NumberCompare();
        SortingMachine<Integer> smN = new SortingMachine1L<>(cN);

        for (Map.Pair<String, Integer> pair : cutMap) {
            smN.add(pair.value());
        }

        smN.changeToExtractionMode();

        int maxSize = 0;
        int minSize = 0;

        maxSize = smN.removeFirst();

        while (smN.size() > 1) {

            smN.removeFirst();
        }

        minSize = smN.removeFirst();

        setUpFileOutput(fileOut, n, inputFileName, smS, cutMap, minSize,
                maxSize);

        in.close();
        out.close();
        inputFile.close();
        fileOut.close();
    }

}
