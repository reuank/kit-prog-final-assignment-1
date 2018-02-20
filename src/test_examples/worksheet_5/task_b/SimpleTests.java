package test_examples.worksheet_5.task_b;

import org.junit.jupiter.api.Test;
import test.TestBase;
import test.TestPair;

public class SimpleTests extends TestBase {

    @Test
    void testForSpacesInName() {
        TestPair[] testPairs = new TestPair[]{
                new TestPair("add-team 42;Stüker Heroes", "OK", TestPair.Type.CHECK_EQUALS),
                new TestPair("list-team", "42 Stüker Heroes", TestPair.Type.CHECK_EQUALS),
        };

        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }

    @Test
    void testStuff() {
        TestPair[] testPairs = new TestPair[]{
                new TestPair("add-team 42;Banana-league", "OK", TestPair.Type.CHECK_EQUALS),
                new TestPair("list-team", "42 Banana-league", TestPair.Type.CHECK_EQUALS),
        };

        testUsingPairs(testPairs, () -> DummyMain.main(null));
    }
}
