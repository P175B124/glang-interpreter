package edu.ktu.glang.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoreTests {

    @Test
    void print_parentheses_precedence() {
        String program = """
                print((1+2)*3);
                """;
        String expected = """
                9
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void print_modulo_expression() {
        String program = """
                print(10%4);
                """;
        String expected = """
                2
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void multiple_statements_order_and_newlines() {
        String program = """
                print(1+1);
                print(2*3);
                print(8/2*(2+2));
                """;
        String expected = """
                2
                6
                16
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void variable_declaration_and_use() {
        String program = """
                int x = 4;
                print(x);
                """;
        String expected = """
                4
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void variable_reassignment() {
        String program = """
                int x = 7;
                x = 3;
                print(x);
                """;
        String expected = """
                3
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void variable_used_in_expression() {
        String program = """
                int a = 2;
                int b = 5;
                print(a*b + (b - a));
                """;
        String expected = "13\n"; // 2*5 + (5-2) = 10 + 3
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void redeclaration_should_fail() {
        String program = """
                int x = 1;
                int x = 2;
                """;
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> GLangInterpreter.execute(program));
        assertTrue(ex.getMessage().contains("Variable already exists"));
    }

    @Test
    void assignment_to_undeclared_should_fail() {
        String program = """
                x = 10;
                """;
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> GLangInterpreter.execute(program));
        assertTrue(ex.getMessage().contains("Undeclared variable"));
    }

    @Test
    void print_string_literal() {
        String program = """
                print("hello");
                """;
        String expected = """
                hello
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void print_string_with_escapes() {
        String program = """
                print("line1\\nline2");
                print("quote: \\\" and backslash: \\\\");
                """;
        String expected = """
                line1
                line2
                quote: " and backslash: \\
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void if_true_branch() {
        String program = """
                if (2+2 == 4) { print(1); }
                """;
        String expected = """
                1
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void if_false_branch_with_else() {
        String program = """
                if (3*3 == 8) { print(1); } else { print(2); }
                """;
        String expected = """
                2
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void nested_if() {
        String program = """
                int x = 5;
                if (x == 5) {
                    if (1+1 == 2) { print(10); } 
                } else { 
                    print(20); 
                }
                """;
        String expected = """
                10
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void comments_are_ignored_line_and_block() {
        String program = """
                // line comment
                /* block
                   comment */
                print(1+2); // after stmt
                /* inline */ print(3*4);
                """;
        String expected = """
                3
                12
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void whitespace_robustness() {
        String program = "   \n\tprint(  (1 + 2) * (  3\t)   );   \n";
        String expected = """
                9
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }

    @Test
    void complex_mixed_ops_precedence() {
        String program = """
                print(1 + 2 * 3 - 4 + 10 / 2 + 9 % 4);
                """;
        String expected = """
                9
                """;
        assertEquals(expected, GLangInterpreter.execute(program));
    }
}
