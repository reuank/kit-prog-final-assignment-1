package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MassiveCornerCases extends Assignment1TestBase {

    @Test
    @DisplayName("ManyCases")
    void manyCases() {
        final File folder = new File("src/final_assignment/task_1/tests/data/massive_corner_cases/");
        for (final File fileEntry : folder.listFiles()) {
            log.info("\nFile: " + fileEntry.getPath());
            testWithIOFile(fileEntry.getPath());
        }
    }
}
