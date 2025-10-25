parser grammar GLangParser;

options { tokenVocab = GLangLexer; }

program : statement+ EOF ;

statement
    : variableDeclaration SEMI
    | assignment SEMI
    | ifStatement
    | printStatement SEMI
    ;

variableDeclaration : TYPE ID EQ expression ;

assignment : ID EQ expression ;

expression
    : ID                                #idExpression
    | INT                               #intExpression
    | BOOL                              #boolExpression
    | stringLiteral                     #stringExpression
    | LPAREN expression RPAREN          #parenthesesExpression
    | expression intMultiOp expression  #intMultiOpExpression
    | expression intAddOp expression    #intAddOpExpression
    ;

intMultiOp : STAR | SLASH | PERCENT ;
intAddOp   : PLUS | MINUS ;

ifStatement
    : IF LPAREN expression relationOp expression RPAREN LBRACE statement RBRACE
      (ELSE LBRACE statement RBRACE)?
    ;

relationOp : EQEQ | NEQ ;

printStatement : PRINT LPAREN expression RPAREN ;

stringLiteral
    : STRING_START stringPart* STRING_END
    ;

stringPart
    : STRING_TEXT
    | INTERP_START expression INTERP_END
    ;
