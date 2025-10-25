lexer grammar GLangLexer;

/* Keywords */
TYPE    : 'int' | 'bool' ;
PRINT   : 'print' ;
IF      : 'if' ;
ELSE    : 'else' ;

/* Identifiers & literals */
ID      : [a-zA-Z]+ ;
INT     : [0-9]+ ;

/** Simple string literal: supports escaped quotes and backslashes */
STRING  : '"' ( '\\' . | ~["\\] )* '"' ;

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

SEMI : ';' ;

/* Comments and whitespace */
COMMENT : ( '//' ~[\r\n]* | '/*' .*? '*/' ) -> skip ;
WS      : [ \t\r\n]+ -> skip ;
