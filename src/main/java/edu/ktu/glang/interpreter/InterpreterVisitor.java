package edu.ktu.glang.interpreter;

import edu.ktu.glang.GLangParserBaseVisitor;
import edu.ktu.glang.GLangParser;
import org.antlr.v4.runtime.Token;

public class InterpreterVisitor extends GLangParserBaseVisitor<Object> {

    private final StringBuilder SYSTEM_OUT = new StringBuilder();

    private final SymbolTable symbolTable;
    private final IfStatementVisitor ifStatementVisitor;

    public InterpreterVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.ifStatementVisitor = new IfStatementVisitor(this);
    }

    @Override
    public Object visitProgram(GLangParser.ProgramContext ctx) {
        super.visitProgram(ctx);
        return SYSTEM_OUT.toString();
    }

    @Override
    public Object visitVariableDeclaration(GLangParser.VariableDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        Object value = visit(ctx.expression());
        if (!this.symbolTable.contains(varName)) {
            this.symbolTable.put(varName, value);
        } else {
            throw new RuntimeException("Variable already exists.");
        }
        return null;
    }

    @Override
    public Object visitAssignment(GLangParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        Object value = visit(ctx.expression());
        if (this.symbolTable.contains(varName)) {
            this.symbolTable.put(varName, value);
        } else {
            throw new RuntimeException("Undeclared variable.");
        }
        return null;
    }

    @Override
    public Object visitIdExpression(GLangParser.IdExpressionContext ctx) {
        String varName = ctx.ID().getText();
        return this.symbolTable.get(varName);
    }

    @Override
    public Object visitIntExpression(GLangParser.IntExpressionContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    @Override
    public Object visitBoolExpression(GLangParser.BoolExpressionContext ctx) {
        return Boolean.parseBoolean(ctx.BOOL().getText());
    }

    @Override
    public Object visitStringExpression(GLangParser.StringExpressionContext ctx) {
        String raw = ctx.STRING().getText();
        String unquoted = raw.substring(1, raw.length() - 1);  // remove quotes
        String value = unescape(unquoted);             // decode escapes
        return value;
    }

    private static String unescape(String s) {
        return s
                .replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    @Override
    public Object visitPrintStatement(GLangParser.PrintStatementContext ctx) {
        String text = visit(ctx.expression()).toString();
        //System.out.println(text);
        SYSTEM_OUT.append(text).append("\n");
        return null;
    }

    @Override
    public Object visitParenthesesExpression(GLangParser.ParenthesesExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Object visitIntAddOpExpression(GLangParser.IntAddOpExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        Token op = ctx.intAddOp().getStart();
        int type = op.getType();

        return switch (type) {
            case GLangParser.PLUS -> (Integer) val1 + (Integer) val2;
            case GLangParser.MINUS -> (Integer) val1 - (Integer) val2;
            default -> throw new IllegalStateException("Unknown add operator token: " + type);
        };
    }

    @Override
    public Object visitIntMultiOpExpression(GLangParser.IntMultiOpExpressionContext ctx) {
        Object val1 = visit(ctx.expression(0));
        Object val2 = visit(ctx.expression(1));

        Token op = ctx.intMultiOp().getStart();
        int type = op.getType();

        return switch (type) {
            case GLangParser.STAR -> (Integer) val1 * (Integer) val2;
            case GLangParser.SLASH -> (Integer) val1 / (Integer) val2;
            case GLangParser.PERCENT -> (Integer) val1 % (Integer) val2;
            default -> throw new IllegalStateException("Unknown multiply operator token: " + type);
        };
    }

    @Override
    public Object visitIfStatement(GLangParser.IfStatementContext ctx) {
        return this.ifStatementVisitor.visitIfStatement(ctx);
    }
}
