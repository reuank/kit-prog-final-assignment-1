package edu.kit.informatik;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * This class is a drop in replacement for the KIT Terminal class.
 * It exactly functions as before but can also be used by a test.
 * The comments are left as they were originally to avoid confusion by not so experienced programmers.
 *
 * @author Alexander Sommer
 * @since 23.01.2018
 */
public final class Terminal {
    /**
     * This field determines whether the current execution of the program is a test
     */
    public static boolean isTest = false;

    /**
     * If enabled Terminal creates a file with TestPairs of the current run
     */
    public static boolean isTestCreation = false;

    private static StringBuilder testPairFile = new StringBuilder();
    /**
     * This field represents the input from a test to a tested program
     */
    public static final LinkedList<String> IN_TEST = new LinkedList<>();
    /**
     * This field represents the output from a tested program to a test
     */
    public static final LinkedList<String> OUT_TEST = new LinkedList<>();

    /**
     * Reads text from the "standard" input stream, buffering characters so as to provide for the efficient reading
     * of characters, arrays, and lines. This stream is already open and ready to supply input data and corresponds
     * to keyboard input.
     */
    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Private constructor to avoid object generation.
     *
     * @deprecated Utility-class constructor.
     */
    @Deprecated
    private Terminal() {
        throw new AssertionError("Utility class constructor.");
    }

    /**
     * Prints the given error-{@code message} with the prefix "{@code Error, }".
     * <p>
     * <p>More specific, this method behaves exactly as if the following code got executed:
     * <blockquote><pre>
     * Terminal.printLine("Error, " + message);</pre>
     * </blockquote>
     *
     * @param message the error message to be printed
     */
    public static void printError(final String message) {
        if (isTest) {
            OUT_TEST.add("Error, " + message);
        } else if (isTestCreation) {
            System.err.println("Error, " + message);
            testPairFile.append("Error, ...\n");
        } else {
            System.err.println("Error, " + message);
        }
    }

    /**
     * Prints the string representation of an {@code Object} and then terminate the line.
     * <p>
     * <p>If the argument is {@code null}, then the string {@code "null"} is printed, otherwise the object's string
     * value {@code obj.toString()} is printed.
     *
     * @param object the {@code Object} to be printed
     * @see String#valueOf(Object) String#valueOf(Object)
     */
    public static void printLine(final Object object) {
        if (isTest) {
            OUT_TEST.add(String.valueOf(object));
        } else if (isTestCreation) {
            System.out.println(object);
            testPairFile.append(String.valueOf(object)).append("\n");
        } else {
            System.out.println(object);
        }
    }

    /**
     * Prints an array of characters and then terminates the line.
     * <p>
     * <p>If the argument is {@code null}, then a {@code NullPointerException} is thrown, otherwise the value of {@code
     * new String(charArray)}* is printed.
     *
     * @param charArray an array of chars to be printed
     * @see String#valueOf(char[]) String#valueOf(char[])
     */
    public static void printLine(final char[] charArray) {
        /*
         * Note: This method's sole purpose is to ensure that the Terminal-class behaves exactly as
         * System.out regarding output. (System.out.println(char[]) calls String.valueOf(char[])
         * which itself returns 'new String(char[])' and is therefore the only method that behaves
         * differently when passing the provided parameter to the System.out.println(Object)
         * method.)
         */
        printLine(charArray);
    }

    /**
     * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return
     * ('\r'), or a carriage return followed immediately by a linefeed.
     *
     * @return a {@code String} containing the contents of the line, not including any line-termination characters, or {@code null} if the end of the stream has been reached
     */
    public static String readLine() {
        if (!isTest)
            try {
                String in = IN.readLine();
                if (isTestCreation && !in.equals("quit")) {
                    testPairFile.append("> ").append(in).append("\n");
                }
                return in;
            } catch (final IOException e) {
                /*
                 * The IOException will not occur during testExamples executed by the praktomat, therefore the
                 * following RuntimeException does not have to get handled.
                 */
                throw new RuntimeException(e);
            }
        else {
            assert !IN_TEST.isEmpty()
                    : "Either the executed test doesn't has quit as last input\n"
                    + "or your quitting mechanism doesn't work properly.\n"
                    + "Expect the second option or check the test!";

            return IN_TEST.poll();
        }
    }

    /**
     * Reads the file with the specified path and returns its content stored in a {@code String} array, whereas the
     * first array field contains the file's first line, the second field contains the second line, and so on.
     *
     * @param path the path of the file to be read
     * @return the content of the file stored in a {@code String} array
     */
    public static String[] readFile(final String path) {
        assert !isTest : "Read file isn't supported by a test yet :(";

        try (final BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().toArray(String[]::new);
        } catch (final IOException e) {
            /*
             * You can expect that the praktomat exclusively provides valid file-paths. Therefore
             * there will no IOException occur while reading in files during the testExamples, the
             * following RuntimeException does not have to get handled.
             */
            throw new RuntimeException(e);
        }
    }

    /**
     * Save test pairs to file.
     *
     * @param path the path of file
     */
    public static void saveInputOutputPairs(String path) {
        if (isTest) {
            System.err.println("Your program is still in test-creation-mode!");
            return;
        }

        try (PrintWriter out = new PrintWriter(path)) {
            testPairFile.deleteCharAt(testPairFile.length() - 1);
            out.print("Automatically created test\n" + testPairFile.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + path + "' for saving test pairs.");
        }

        System.out.println("Successfully created '" + path + "'");
    }

    /**
     * Save test paris to timestamped file.
     */
    public static void saveInputOutputPairs() {
        String path = "testPairs_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss'.io'").format(new Date());

        saveInputOutputPairs(path);
    }

    /**
     * This automatically adds the specified command line args to the test creation
     *
     * @param args are the args to add
     */
    public static void startTestCreation(String[] args) {
        isTestCreation = true;
        if (args != null) {
            testPairFile.append(test.TestBase.IO_FILE_COMMAND_LINE_ARGS_PREFIX);
            for (String arg : args) {
                testPairFile.append(arg);
                testPairFile.append(" ");
            }
            testPairFile.deleteCharAt(testPairFile.length() - 1);
            testPairFile.append("\n");
        }
    }
}
