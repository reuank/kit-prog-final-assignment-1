package test;

public class TestPair {
    public String getFailMessage(String actualOutput) {
        return "Fail with " + this + "\nOutput was"
                + (actualOutput.contains("\n") ? "\n" : " ")
                + actualOutput;
    }

    public enum Type {
        CHECK_EQUALS,
        CHECK_STARTS_WITH,
        CHECK_FOR_ERROR,
        CHECK_CONTAINS,
        NO_CHECK,
    }

    private String input;
    private String output;
    private Type type;

    public TestPair(String input, String output, Type type) {
        this.input = input;
        this.output = output;
        this.type = type;
    }

    public TestPair(String input, String output) {
        this.input = input;
        this.output = output;
        this.type = Type.CHECK_EQUALS;
    }

    public TestPair(String input, Type type) {
        this.input = input;
        this.type = type;
        this.output = "";
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type != Type.CHECK_FOR_ERROR) {
            String outStr = "'" + output.split("\n")[0] + "\'";

            return "TestPair{" +
                    "'" + input + "\'->" +
                    outStr + (output.contains("\n") ? "..." : "") +
                    " @" + type +
                    '}';
        } else {
            return "TestPair{" +
                    "'" + input + "'" +
                    " @" + type +
                    '}';
        }
    }
}
