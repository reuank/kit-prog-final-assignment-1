package final_assignment.task_1.tests;

import final_assignment.task_1.Assignment1TestBase;
import org.junit.jupiter.api.Test;

public class ArgsTests extends Assignment1TestBase {

    @Test
    void errorTestsArgs1() {
        String args[] ={"standard","20","4"};
        testArgs(args,true);
    }

    @Test
    void errorTestsArgs2() {
        String args[] ={"standard","20","2"};
        testArgs(args,true);
    }

    @Test
    void errorTestsArgs3() {
        String args[] ={"torus","18","3"};
        testArgs(args,true);
    }

    @Test
    void errorTestsArgs4() {
        String args[] ={"standard","20 ","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs5() {
        String args[] ={"standard","20","5"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs6() {
        String args[] ={"standard","19","2"};
        testArgs(args,false);
    }


    @Test
    void errorTestsArgs7() {
        String args[] ={"torus","17","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs8() {
        String args[] ={"torus","21","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs9() {
        String args[] ={"standard ","20","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs10() {
        String args[] ={" standard","20","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs11() {
        String args[] ={"torus ","20","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs12() {
        String args[] ={"standard","20 ","4"};
        testArgs(args,false);
    }

    @Test
    void errorTestsArgs13() {
        String args[] ={"standard","20"," 4"};
        testArgs(args,false);
    }
}
