package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintTest {

    @Test
    void print_integer() {
        String program = """
                         print(5);
                         """;

        String expected = """
                          5
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void print_string() {
        String program = """
                         print("test");
                         """;

        String expected = """
                          test
                          """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }
}