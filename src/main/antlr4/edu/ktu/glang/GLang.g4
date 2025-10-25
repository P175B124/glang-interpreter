grammar GLang;

program : statement+ EOF ;

statement
    : variableDeclaration ';'
    | assignment ';'
    | ifStatement
    | printStatement ';'
    ;

variableDeclaration : TYPE ID EQ expression ;

assignment : ID EQ expression ;

expression
    : INT                               #intExpression
    | ID                                #idExpression
    | STRING                            #stringExpression
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

/* ============== LEXER ============== */

/* Keywords */
TYPE    : 'int' | 'bool' ;
PRINT   : 'print' ;
IF      : 'if' ;
ELSE    : 'else' ;

ID      : [a-zA-Z]+ ;
INT     : [0-9]+ ;

/** supports escaped quotes and backslashes */
STRING
    : '"' ( '\\' . | ~["\\] )* '"'
    ;

/* Operators and punctuation */
LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACE  : '{' ;
RBRACE  : '}' ;
EQ      : '=' ;
PLUS    : '+' ;
MINUS   : '-' ;
STAR    : '*' ;
SLASH   : '/' ;
PERCENT : '%' ;
EQEQ    : '==' ;
NEQ     : '!=' ;

/* Comments and whitespace */
COMMENT : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS      : [ \t\r\n]+ -> skip ;