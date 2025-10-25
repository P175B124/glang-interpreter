package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InterpolationTests {

    @Test
    void simple_identifier_interpolation() {
        String program = """
                         string name = "World";
                         print("Hello, ${name}!");
                         """;
        String expected = """
                          Hello, World!
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void arithmetic_inside_interpolation() {
        String program = """
                         print("Answer: ${1 + 2 * 3}");
                         """;
        String expected = """
                          Answer: 7
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void multiple_interpolations_and_plain_text() {
        String program = """
                         int a = 2;
                         int b = 5;
                         print("a=${a}, b=${b}, a+b=${a + b}");
                         """;
        String expected = """
                          a=2, b=5, a+b=7
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void nested_braces_in_expression() {
        String program = """
                         print("calc=${(1 + (2 * (3 + 4)))}");
                         """;
        String expected = """
                          calc=15
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void boolean_literals_in_interpolation() {
        String program = """
                         print("bool=${true} and ${false}");
                         """;
        String expected = """
                          bool=true and false
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void nested_string_inside_interpolation() {
        String program = """
                         print("inner=${"xyz"}.");
                         """;
        String expected = """
                          inner=xyz.
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void escapes_and_interpolation_mixed() {
        String program = """
                         int n = 2;
                         print("line1\\nline2=${n}\\tend");
                         """;
        String expected = """
                          line1
                          line2=2\tend
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void literal_dollar_sign_is_text() {
        String program = """
                         print("Price: $5");
                         """;
        String expected = """
                          Price: $5
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void interpolation_next_to_text_chunks() {
        String program = """
                         int x = 10;
                         print("A${x}B${x + 1}C");
                         """;
        String expected = """
                          A10B11C
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void whitespace_inside_interpolation_is_ignored() {
        String program = """
                         int a = 3;
                         int b = 4;
                         print("sum=${   a   +   b   }");
                         """;
        String expected = """
                          sum=7
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void complex_expression_with_parentheses_and_modulo() {
        String program = """
                         int a = 8;
                         int b = 3;
                         print("v=${(a + 10 / 2) * (b + 1) % 7}");
                         """;
        // (8 + 5) * 4 % 7 = 13 * 4 % 7 = 52 % 7 = 3
        String expected = """
                          v=3
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    @Test
    void if_statement_with_interpolated_string_value_used_later() {
        String program = """
                         string s = "x${1+1}";
                         print(s);
                         if (2 + 2 == 4) { print("ok ${s}"); } else { print("bad"); }
                         """;
        String expected = """
                          x2
                          ok x2
                          """;
        String actual = GLangInterpreter.execute(program);
        assertEquals(expected, actual);
    }

    // unterminated interpolation should throw (parser/lexer error).
    @Test
    void unterminated_interpolation_should_fail() {
        String program = """
                         print("oops ${1+2");
                         """;
        assertThrows(RuntimeException.class, () -> GLangInterpreter.execute(program));
    }
}
