package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariablesDeclarationTest {

    @Test
    void declare_and_print_int_variable() {
        String program = """
                int a = 5;
                print(a);
                """;

        String expected = """
                5
                """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void declare_and_print_bool_variable() {
        String program = """
                bool a = true;
                print(a);
                """;

        String expected = """
                true
                """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void declare_and_print_string_variable() {
        String program = """
                string a = "text";
                print(a);
                """;

        String expected = """
                text
                """;

        String actual = GLangInterpreter.execute(program);

        assertEquals(expected, actual);
    }

    @Test
    void undeclared_variable_throws_exception() {
        String program = """
                a = 5;
                print(a);
                """;

        assertThrows(RuntimeException.class,
                () -> {
                    GLangInterpreter.execute(program);
                });
    }
}
