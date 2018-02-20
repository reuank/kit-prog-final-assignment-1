package final_assignment.task_1;

import edu.kit.informatik.Terminal;
import test.TestBase;
import test.TestPair;

/**
 * This class links the DummyMain of this task and the TestBase
 */
public class Assignment1TestBase extends TestBase {
    /**
     * Tests the program linked with the DummyMain with the specified io-file
     *
     * @param path is the path of the io-file
     */
    protected void testWithIOFile(String path) {
        testWithIOFile(path, () -> DummyMain.main(getCommandLineArgsFromIOFile(path)));
    }

    /**
     * Tests the program linked with the DummyMain using the specified pairs
     *
     * @param testPairs are the pairs to test on
     * @param args      are the command-line-args
     */
    protected void testUsingPairs(TestPair[] testPairs, String[] args) {
        testUsingPairs(testPairs, () -> DummyMain.main(args));
    }

    /**
     * Gets the place command for specified coordinates
     *
     * @param x1 is the column of the first coordinate
     * @param y1 is the row of the first coordinate
     * @param x2 is the column of the second coordinate
     * @param y2 is the row of the second coordinate
     * @return a {@link String} containing the command
     */
    protected String getPlaceCommand(int x1, int y1, int x2, int y2) {
        return "place " + y1 + ";" + x1 + ";" + y2 + ";" + x2;
    }

    protected void testArgs(String args[], boolean acceptable) {
        Terminal.IN_TEST.add("quit");
        StringBuilder strb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == 0)
                strb.append(args[i]);
            else
                strb.append(" " + args[i]);
        }
        log.info("args[]: '"+strb.toString()+"'");
        DummyMain.main(args);
        if (acceptable) {
            if (Terminal.OUT_TEST.poll() != null) {
                failAndLog("test should have succeeded");
            }
        } else {
            String output = Terminal.OUT_TEST.poll();
            if (output == null || !output.startsWith("Error")) {
                failAndLog("test should have failed");
            }
        }
    }
}
