package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.TestPair;

/**
 * These tests should test all possible win patterns
 *
 * @author Alexander Sommer
 * @since 11.02.2018
 */
@SuppressWarnings("Duplicates")
public class AllWinPatterns extends Assignment1TestBase {
    //Tests in normal mode
    /**
     * This tests all possible horizontal win patterns in standard mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("horizontal-standard")
    void testHorizontalStandard() {
        testHorizontal("standard", 18, 0, -5);
        testHorizontal("standard", 20, 0, -5);
    }

    /**
     * This tests all possible vertical win patterns in standard mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("vertical-standard")
    void testVerticalStandard() {
        testVertical("standard", 18, 0, -5);
        testVertical("standard", 20, 0, -5);
    }

    /**
     * This tests all possible left-to-right-diagonal win patterns in standard mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("╲-diagonal-standard")
    void testLRDiagonalStandard() {
        testLRDiagonal("standard", 18, 0, -5);
        testLRDiagonal("standard", 20, 0, -5);
    }

    /**
     * This tests all possible right-to-left-diagonal win patterns in standard mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("╱-diagonal-standard")
    void testRLDiagonalStandard() {
        testRLDiagonal("standard", 18, 0, -5);
        testRLDiagonal("standard", 20, 0, -5);
    }

    //Tests in torus mode
    /**
     * This tests all possible horizontal win patterns in torus mode (in that Z/nZ-ring)
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("horizontal-torus")
    void testHorizontalTorus() {
        testHorizontal("torus", 18, -6, 6);
        testHorizontal("torus", 20, -6, 6);
    }

    /**
     * This tests all possible vertical win patterns in torus mode (in that Z/nZ-ring)
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("vertical-torus")
    void testVerticalTorus() {
        testVertical("torus", 18, -6, 6);
        testVertical("torus", 20, -6, 6);
    }

    /**
     * This tests all possible right-to-left-diagonal win patterns in torus mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("╱-diagonal-torus")
    void testRLDiagonalTorus() {
        testRLDiagonal("torus", 18, -6, 0);
        testRLDiagonal("torus", 20, -6, 0);
    }

    /**
     * This tests all possible left-to-right-diagonal win patterns in torus mode
     *
     * @author Alexander Sommer
     */
    @Test
    @DisplayName("╲-diagonal-torus")
    void testLRDiagonalTorus() {
        testLRDiagonal("torus", 18, -6, 0);
        testLRDiagonal("torus", 20, -6, 0);
    }

    //Implementation
    private void testHorizontal(String gameMode, int gameSize, int startOffset, int stopOffset) {
        String[] args = new String[]{gameMode, String.valueOf(gameSize), "2"};
        TestPair[] testPairs = new TestPair[6];
        testPairs[5] = new TestPair("print", "", TestPair.Type.NO_CHECK);
        enableShowAllProgramOutput();

        for (int y = 0; y < gameSize; y++)
            for (int x = startOffset; x < gameSize + stopOffset; x++) {
                for (int i = 0; i < 3; i++) {
                    int posX = x + i;
                    testPairs[2 * i] = new TestPair(getPlaceCommand(posX, y, posX + 3, y)
                            , i == 2 ? "P1" : "OK", TestPair.Type.CHECK_STARTS_WITH);
                }
                int posY = y + 1;
                if (posY >= gameSize) {
                    posY = 0;
                }
                for (int i = 0; i < 2; i++) {
                    int posX = x + i;
                    testPairs[2 * i + 1] = new TestPair(getPlaceCommand(posX, posY, posX + 3, posY)
                            , "OK");
                }
                testUsingPairs(testPairs, args);
                clearData();
            }
    }

    private void testVertical(String gameMode, int gameSize, int startOffset, int stopOffset) {
        String[] args = new String[]{gameMode, String.valueOf(gameSize), "2"};
        TestPair[] testPairs = new TestPair[6];
        testPairs[5] = new TestPair("print", "", TestPair.Type.NO_CHECK);
        enableShowAllProgramOutput();

        for (int x = 0; x < gameSize; x++)
            for (int y = startOffset; y < gameSize + stopOffset; y++) {
                for (int i = 0; i < 3; i++) {
                    int posY = y + i;
                    testPairs[2 * i] = new TestPair(getPlaceCommand(x, posY, x, posY + 3)
                            , i == 2 ? "P1" : "OK", TestPair.Type.CHECK_STARTS_WITH);
                }
                int posX = x + 1;
                if (posX >= gameSize) {
                    posX = 0;
                }
                for (int i = 0; i < 2; i++) {
                    int posY = y + i;
                    testPairs[2 * i + 1] = new TestPair(getPlaceCommand(posX, posY, posX, posY + 3)
                            , "OK");
                }
                testUsingPairs(testPairs, args);
                clearData();
            }
    }

    private void testRLDiagonal(String gameMode, int gameSize, int startOffset, int stopOffset) {
        String[] args = new String[]{gameMode, String.valueOf(gameSize), "2"};
        TestPair[] testPairs = new TestPair[6];
        testPairs[5] = new TestPair("print", "", TestPair.Type.NO_CHECK);
        enableShowAllProgramOutput();

        for (int x = 5; x < gameSize; x++)
            for (int y = startOffset; y < gameSize + stopOffset; y++) {
                for (int i = 0; i < 3; i++) {
                    int posX = x - i;
                    int posY = y + i;
                    testPairs[2 * i] = new TestPair(getPlaceCommand(posX, posY, posX - 3, posY + 3)
                            , i == 2 ? "P1" : "OK", TestPair.Type.CHECK_STARTS_WITH);

                    if (i != 2) {
                        posX -= 3;
                        if (++posX >= gameSize) {
                            posX = 0;
                        }
                        testPairs[2 * i + 1] = new TestPair(getPlaceCommand(posX, posY, posX, posY + 3)
                                , "OK");
                    }
                }

                testUsingPairs(testPairs, args);
                clearData();
            }
    }

    private void testLRDiagonal(String gameMode, int gameSize, int startOffset, int stopOffset) {
        String[] args = new String[]{gameMode, String.valueOf(gameSize), "2"};
        TestPair[] testPairs = new TestPair[6];
        testPairs[5] = new TestPair("print", "", TestPair.Type.NO_CHECK);
        enableShowAllProgramOutput();

        for (int x = startOffset; x < gameSize + stopOffset; x++)
            for (int y = startOffset; y < gameSize + stopOffset; y++) {
                for (int i = 0; i < 3; i++) {
                    int posY = y + i;
                    int posX = x + i;
                    testPairs[2 * i] = new TestPair(getPlaceCommand(posX, posY, posX + 3, posY + 3)
                            , i == 2 ? "P1" : "OK", TestPair.Type.CHECK_STARTS_WITH);

                    if (i != 2) {
                        if (++posX >= gameSize) {
                            posX -= gameSize;
                        }
                        testPairs[2 * i + 1] = new TestPair(getPlaceCommand(posX, posY, posX + 3, posY + 3)
                                , "OK");
                    }
                }

                testUsingPairs(testPairs, args);
                clearData();
            }
    }
}
