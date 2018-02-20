package final_assignment.task_1.creators;

import edu.kit.informatik.Terminal;
import final_assignment.task_1.DummyMain;
import test.TestPair;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

public class CaseCreator {

    private static final StringBuilder testPairfile = new StringBuilder();

    private static final int gameSize = 20;
    private static final String numberOfPlayers = "4";
    private static final String gameMode = "torus";

    private static final Random random = new Random();
    private static final long rgenseed = System.currentTimeMillis();


    public static void main(String args[]) {
        Terminal.isTest = true;
        random.setSeed(rgenseed);

        Vector<TestPair> pairs = new Vector<TestPair>();
        testPairfile.append("! " + gameMode + " " + gameSize + " " + numberOfPlayers + "\n");
        boolean[][] field = new boolean[gameSize][gameSize];

        for (int i = 0; i < gameSize * gameSize / 2; i++) {
            int r1, r2, c1, c2;
            do {
                r1 = random.nextInt(20);
                c1 = random.nextInt(20);
            } while (field[r1][c1]);
            field[r1][c1] = true;
            do {
                r2 = random.nextInt(20);
                c2 = random.nextInt(20);
            } while (field[r2][c2]);

            field[r1][c1] = true;
            field[r2][c2] = true;

            pairs.add(new TestPair("place " + r1 + ";" + c1 + ";" + r2 + ";" + c2, "OK"));
        }
        String tempArgs[] = {gameMode, "" + gameSize, numberOfPlayers};
        testUsingPairs(pairs.toArray(new TestPair[pairs.size()]), () -> DummyMain.main(tempArgs));
    }

    private static void testUsingPairs(TestPair[] testPairs, Runnable testedMethod) {
        for (TestPair testPair : testPairs) {
            Terminal.IN_TEST.add(testPair.getInput());
        }

        Terminal.IN_TEST.add("quit");
        testedMethod.run();

        for (TestPair testPair : testPairs) {
            testPairfile.append("> " + testPair.getInput() + "\n");

            String partOutput;
            StringBuilder stringBuilder = new StringBuilder();
            do {
                partOutput = Terminal.OUT_TEST.pollFirst();
                if (partOutput == null)
                    break;
                stringBuilder.append(partOutput);
                stringBuilder.append("\n");
            } while (stringBuilder.length() < testPair.getOutput().length());

            if (stringBuilder.length() != 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            String output = stringBuilder.toString();
            testPairfile.append(output + "\n");


            if (!output.equals(testPair.getOutput())) {
                saveInputOutputPairs();
                break;
            }
        }
    }

    public static void saveInputOutputPairs() {
        String path = "testPairs_" + new java.util.Date().getTime() + ".io";

        try (PrintWriter out = new PrintWriter(path)) {
            out.print("Automatically created test with seed " + rgenseed + "\n" + testPairfile.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file " + path + " for saving test pairs.");
        }
    }
}
