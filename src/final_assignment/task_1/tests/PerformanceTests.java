package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import final_assignment.task_1.DummyMain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.TestPair;

public class PerformanceTests extends Assignment1TestBase {
    @Test
    @DisplayName("PerformanceTest1")
    void performanceTest1() {
        enablePerformanceTest();
        String path = "src/final_assignment/task_1/tests/data/performance_test_1.io";
        TestPair[] pairs = loadTestPairsFromIOFile(path);
        String args[] = {"torus", "20", "2"};
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
        testUsingPairs(pairs, args);
    }
}
