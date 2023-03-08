package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangLexer;
import edu.ktu.glang.GLangParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        String fileName = args[0];
        try {
            execute(CharStreams.fromFileName(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execute(CharStream stream) {
        GLangLexer lexer = new GLangLexer(stream);
        GLangParser parser = new GLangParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);

        // Create a new instance of your error listener implementation
        GLangErrorListener errorListener = new GLangErrorListener();
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.program();

        SymbolTable symbolTable = new SymbolTable();

        // INTERPRETER
        // Generate and execute program
        InterpreterVisitor interpreter = new InterpreterVisitor(symbolTable);
        Object result = interpreter.visit(tree);

        System.out.println(">>>> PROGRAM OUTPUT:");
        // Print the result of the command
        System.out.println(result);

//        // COMPILER
//        // Perform semantic analysis
//        TypeCheckerVisitor typeChecker = new TypeCheckerVisitor(symbolTable);
//        typeChecker.visit(tree);
//
//        // Generate code for the program
//        CodeGeneratorVisitor codeGenerator = new CodeGeneratorVisitor(symbolTable);
//        String asm = codeGenerator.visit(tree);
//        FileWriter writer = new FileWriter(new File("output.asm"));
//        writer.write(asm);
//        writer.close();
    }
}
