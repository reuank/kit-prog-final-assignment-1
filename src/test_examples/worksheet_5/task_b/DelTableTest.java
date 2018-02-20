package test_examples.worksheet_5.task_b;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.TestBase;
import test.TestPair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static test.TestPair.Type.CHECK_EQUALS;


/**
 * The type Del table test.
 */
public class DelTableTest extends TestBase {

    @Test
    void test1() {
        testUsingPairs(generatePairs(0, 20, 10000, readFile("src/test_examples/worksheet_5/task_b/data/solutionString1"))
                , () -> DummyMain.main(null));
    }

    @Test
    @DisplayName("test 2")
    void test2() {
        enablePerformanceTest();
        testUsingPairs(generatePairs(0, 50, 100000, readFile("src/test_examples/worksheet_5/task_b/data/solutionString2"))
                , () -> DummyMain.main(null));
    }

    /**
     * Generates TestPairs based on seeded random engine.
     *
     * @param seed     the seed
     * @param teams    the teams
     * @param matches  the matches
     * @param solution the solution
     * @return the test pair [ ]
     */
    private TestPair[] generatePairs(int seed, int teams, int matches, String solution) {
        Random rand = new Random();
        rand.setSeed(seed);

        TestPair[] pairs = new TestPair[teams + matches + 1];

        for (int i = 0; i < teams; i++) {
            pairs[i] = new TestPair("add-team " + (i + 1) + ";" + "team" + i, "OK", CHECK_EQUALS);
        }

        for (int i = teams; i < matches + teams; i++) {
            int team1 = rand.nextInt(teams) + 1;
            int team2;
            do {
                team2 = rand.nextInt(teams) + 1;
            } while (team2 == team1);

            int goalsTeam1 = rand.nextInt(10);
            int goalsTeam2;

            do {
                goalsTeam2 = rand.nextInt(10);
            } while (goalsTeam1 == goalsTeam2);

            int duration;
            if (rand.nextInt(2) == 1) {
                duration = 60;
            } else {
                duration = 70;
            }

            pairs[i] = new TestPair("add-ice-hockey-match " + team1 + ";" + goalsTeam1 + ";" + team2 + ";" +
                    goalsTeam2 + ";" + duration, "OK", CHECK_EQUALS);
        }

        pairs[teams + matches] = new TestPair("print-del-standings",solution, CHECK_EQUALS);
        return pairs;
    }


    /**
     * Reads file to String.
     *
     * @param path the path
     * @return the string
     */
    private String readFile(String path) {

        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }

        } catch (IOException e) {
            return null;
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

}
