package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ErrorTests extends Assignment1TestBase {

    @Test
    void errorTestsStandard() {
        testWithIOFile("src/final_assignment/task_1/tests/data/error_tests_standard.io");
    }

    @Test
    void errorTestsTorus() {
        testWithIOFile("src/final_assignment/task_1/tests/data/error_tests_torus.io");
    }
}