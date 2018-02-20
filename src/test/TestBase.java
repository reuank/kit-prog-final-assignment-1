package test;

import edu.kit.informatik.Terminal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.*;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//JUnit 5.0

/**
 * This class is the base for a test and implements utility functions for testing.
 * It interacts with the {@link edu.kit.informatik.Terminal} class.
 * A test should inherit from this class.
 * JUnit 5.0 is required
 *
 * @author Alexander Sommer
 * @since 23.01.2018
 */
public abstract class TestBase {
    private String testName;
    protected Logger log;
    private List<LogRecord> logData = new ArrayList<>(100);

    private boolean alwaysShowLog;
    private boolean showAllProgramOutput = false;

    private boolean isPerformanceTest;
    private long startTime;
    private static String userId;
    public static final String IO_FILE_COMMAND_LINE_ARGS_PREFIX = "! ";


    /**
     * Initializes testing
     */
    @BeforeAll
    public static void initAll() {
        Terminal.isTest = true;
    }

    private static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                byte[] mac = network.getHardwareAddress();

                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                    }
                    return sb.toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        fail("Unable to get mac address.");
        return null;
    }

    private static void checkUserId() {
        userId = getMacAddress();

        final String USER_ID_FILENAME = "UserId.id";

        try (final BufferedReader reader = new BufferedReader(new FileReader(USER_ID_FILENAME))) {
            userId = reader.readLine();
            reader.close();
        } catch (final IOException e) {
            try {
                PrintWriter of = new PrintWriter(USER_ID_FILENAME);
                of.println(userId);
                of.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Print diff block.
     */
    @BeforeEach
    protected void initTest(TestInfo testInfo) {
        alwaysShowLog = false;
        isPerformanceTest = false;
        testName = testInfo.getDisplayName();
        log = Logger.getLogger(testInfo.getClass().getName());
        log.setUseParentHandlers(false);
        log.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                logData.add(record);
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {
                logData.clear();
            }
        });
        log.info("Running test '" + getTestName(testInfo) + "' ...\n");
    }

    private String getTestName(TestInfo testInfo) {
        return testInfo.getDisplayName() + " @ " + testInfo.getTestClass().toString().substring(15).replace("]", "");
    }

    /**
     * Clears all data from previous testExamples
     * This is automatically called after every test
     */
    @AfterEach
    protected void cleanUp(TestInfo testInfo) {
        if (isPerformanceTest) {
            long endTime = System.nanoTime();
            double runTime = ((double) (endTime - startTime)) / 1000000.0;
            System.out.println("Your run time for test '" + getTestName(testInfo) + "' is " + runTime + " ms.");
            try {
                Socket socket = new Socket("vaelum.de", 12000);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.print(userId + "\n");
                out.flush();

                out.print(getTestName(testInfo) + "\n");
                out.flush();

                out.print("" + runTime + "\n");
                out.flush();

                System.out.println(in.readLine() + "\n");

                in.close();
                out.close();
                socket.close();
            } catch (IOException e2) {
                System.out.println(e2 + "\n");
            }
        }

        if (showAllProgramOutput && !Terminal.OUT_TEST.isEmpty()) {
            System.out.println("The output after that was:");

            while (!Terminal.OUT_TEST.isEmpty()) {
                System.out.println(popProgramOutput());
            }
        }

        clearData();
    }

    /**
     * Clears all test data
     */
    protected void clearData() {
        Terminal.IN_TEST.clear();
        Terminal.OUT_TEST.clear();

        if (alwaysShowLog)
            printLog();
        logData.clear();
    }


    /**
     * Prints the current log
     */
    protected void printLog() {
        for (LogRecord logRecord : logData) {
            System.out.print(logRecord.getMessage());
        }
        System.out.println();
        logData.clear();
    }

    /**
     * Lets the test fail and prints a specified message
     *
     * @param message is the message to print
     */
    protected void failAndLog(String message) {
        printLog();
        isPerformanceTest = false;
        fail(message);
    }

    /**
     * Sets alwaysShowLog to true. Notice: Before each test alwaysShowLog is set to default value false.
     */
    protected void setAlwaysShowLog() {
        alwaysShowLog = true;
    }

    protected void enablePerformanceTest() {
        isPerformanceTest = true;
        checkUserId();
        startTime = System.nanoTime();
    }

    /**
     * Gets the next output from the output-queue and removes it
     *
     * @return a string containing the output
     */
    protected String popProgramOutput() {
        return Terminal.OUT_TEST.pollFirst();
    }

    /**
     * Gets the next output from the output-queue and removes it
     * This does the same as {@code popProgramOutput}
     *
     * @return a string containing the output
     */
    protected String getNextProgramOutput() {
        return popProgramOutput();
    }

    /**
     * Pushes a line to the terminal-input of the program
     *
     * @param line is a string containing the input
     */
    protected void pushProgramInput(String line) {
        Terminal.IN_TEST.add(line);
    }

    /**
     * Tests a specified program using a given {@link TestPair}-array.
     * 'quit' is automatically added as input to the end
     * The {@link Runnable} interface is abused as a sort of function pointer.
     * <p>
     * This function is intended to be used for testing the command-line-interface of a program
     * by creating input-output-pairs that build upon each other
     *
     * @param testPairs    is an array of testPairs
     * @param testedMethod is a reference to a tested program
     */
    protected void testUsingPairs(TestPair[] testPairs, Runnable testedMethod) {
        log.info("Testing using " + testPairs.length + " pairs...\n");

        for (TestPair testPair : testPairs) {
            pushProgramInput(testPair.getInput());
        }

        pushProgramInput("quit");
        testedMethod.run();

        for (TestPair testPair : testPairs) {
            log.info("Testing " + testPair);

            String partOutput;
            StringBuilder stringBuilder = new StringBuilder();
            do {
                partOutput = popProgramOutput();
                if (partOutput == null)
                    break;
                stringBuilder.append(partOutput);
                stringBuilder.append("\n");
            } while (stringBuilder.length() < testPair.getOutput().length());

            if (stringBuilder.length() != 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            String output = stringBuilder.toString();

            switch (testPair.getType()) {
                case CHECK_EQUALS:
                    if (!output.equals(testPair.getOutput())) {
                        log.info(" Failed!\n");
                        printLog();
                        assertEquals(testPair.getOutput(), output);
                    }
                    break;
                case CHECK_STARTS_WITH:
                    if (!output.startsWith(testPair.getOutput())) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
                case CHECK_CONTAINS:
                    if (!output.contains(testPair.getOutput())) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
                case CHECK_FOR_ERROR:
                    if (!output.startsWith("Error, ")) {
                        log.info(" Failed!\n");
                        failAndLog(testPair.getFailMessage(output));
                    }
                    break;
            }
            log.info(" Passed!\n");
        }
        log.info("Test successfully completed.\n");
    }

    /**
     * Tests inputs and outputs of a program specified by an io-file.
     * For the syntax at the examples or in the wiki (It's very intuitive)
     *
     * @param path         is the path of the io-file
     * @param testedMethod is the main method of the tested program
     */
    protected void testWithIOFile(String path, Runnable testedMethod) {
        TestPair[] pairs = loadTestPairsFromIOFile(path);
        testUsingPairs(pairs, testedMethod);
    }

    /**
     * Gets the commandLineArgs from a specified io file
     *
     * @param path is the path of the io-file
     */
    protected String[] getCommandLineArgsFromIOFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(IO_FILE_COMMAND_LINE_ARGS_PREFIX)) {
                    line = line.substring(2);
                    return line.split(" ");
                }
            }
        } catch (IOException ex) {
            fail("Test '" + testName + "' failed. " + ex.getMessage());
        }

        return null;
    }

    /**
     * Loads TestPairs from inputs and outputs specified by an io-file.
     * For the syntax look at the examples or in the wiki (It's very intuitive)
     *
     * @param path is the path of the io-file
     * @return the test pairs
     */
    protected TestPair[] loadTestPairsFromIOFile(String path) {
        List<TestPair> testPairs = new ArrayList<>();

        if (!Files.exists(Paths.get(path)))
            fail("Test not working. Missing required File: " + path);

        boolean isFileStart = true;
        StringBuilder builder = new StringBuilder();
        final String inputLinePrefix = "> ";
        String input = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(inputLinePrefix)) {
                    if (isFileStart) {
                        isFileStart = false;
                    } else {
                        testPairs.add(getIOTestPair(builder, input));
                    }
                    input = line.replace(inputLinePrefix, "");
                    builder.delete(0, builder.length());
                } else {
                    builder.append(line);
                    builder.append("\n");
                }
            }

        } catch (IOException e) {
            fail("Test '" + testName + "' failed. " + e.getMessage());
        }

        if (builder.length() == 0 && input.length() != 0) {
            fail("Test '" + testName + "' failed. No output for '" + input + "' was specified");
        } else {
            testPairs.add(getIOTestPair(builder, input));
        }

        return testPairs.toArray(new TestPair[testPairs.size()]);
    }

    /**
     * In here some syntax of the io-file is defined
     */
    private TestPair getIOTestPair(StringBuilder builder, String input) {
        final String errorPrefix = "Error, ";
        final String startsWithPostfix = "...";
        String output = builder.deleteCharAt(builder.length() - 1).toString();
        if (output.contains(startsWithPostfix)) {
            if (output.startsWith(errorPrefix)) {
                return new TestPair(input, TestPair.Type.CHECK_FOR_ERROR);
            } else {
                String start = output.split(startsWithPostfix)[0];
                return new TestPair(input, start, TestPair.Type.CHECK_STARTS_WITH);
            }
        }

        return new TestPair(input, output);
    }

    protected void enableShowAllProgramOutput() {
        this.showAllProgramOutput = true;
    }
}
