lexer grammar GLangLexer;

/* Keywords */
TYPE    : 'int' | 'bool' | 'string' ;
PRINT   : 'print' ;
IF      : 'if' ;
ELSE    : 'else' ;

/* Identifiers & literals */
INT     : [0-9]+ ;
BOOL    : 'true' | 'false' ;
ID      : [a-zA-Z]+ ; //must be after bool

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
