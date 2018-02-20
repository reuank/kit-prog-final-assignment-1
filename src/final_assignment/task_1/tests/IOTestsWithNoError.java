package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * In this test-class are io-tests that only use valid input
 */
public class IOTestsWithNoError extends Assignment1TestBase {
    /**
     * @author Jonas Zipprick
     */
    @Test
    @DisplayName("Assignment Example aka Public Test")
    public void testAssignmentExample() {
        testWithIOFile("src/final_assignment/task_1/tests/data/assignmentExample.io");
    }
}
