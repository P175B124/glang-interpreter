lexer grammar GLangLexer;

tokens { INTERP_END }

@members {
  private int interpBraceDepth = 0;
}

/* Keywords */
TYPE    : 'int' | 'bool' | 'string' ;
PRINT   : 'print' ;
IF      : 'if' ;
ELSE    : 'else' ;

/* Identifiers & literals */
INT     : [0-9]+ ;
BOOL    : 'true' | 'false' ;
ID      : [a-zA-Z]+ ; //must be after bool

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

/* ===== String entry: switch to STRING mode on opening quote ===== */
STRING_START : '"' -> pushMode(STRING) ;

/* ================= STRING mode ================= */
mode STRING;

STRING_END    : '"' -> popMode ;
STRING_TEXT   : ~["\\$]+ ;
ESC           : '\\' . -> type(STRING_TEXT) ;
INTERP_START  : '${' { interpBraceDepth = 1; } -> pushMode(INTERP) ;
DOLLAR_AS_TEXT: '$' -> type(STRING_TEXT) ;

/* ================= INTERP (expr-only) mode ================= */
mode INTERP;

/* Nested '{' inside ${ ... } */
INTERP_LBRACE
  : '{' { interpBraceDepth++; } -> skip
  ;

/* The '}' that CLOSES the current ${ ... }:
   - when depth==1, emit INTERP_END and pop back to STRING mode */
INTERP_RBRACE_CLOSE
  : '}' { interpBraceDepth == 1 }?
    -> type(INTERP_END), popMode
  ;

/* Inner '}' (depth>1): just decrease depth and skip */
INTERP_RBRACE
  : '}' { interpBraceDepth > 1 }?
    { interpBraceDepth--; }
    -> skip
  ;

/* inside ${ ... } */
INTERP_WS       : [ \t\r\n]+ -> skip ;
INTERP_LINECOMM : '//' ~[\r\n]* -> skip ;
INTERP_BLOCKCOMM: '/*' .*? '*/' -> skip ;

/* Reuse code tokens inside interpolation */
INTERP_INT      : [0-9]+             -> type(INT) ;
INTERP_BOOL     : ('true' | 'false') -> type(BOOL) ;
INTERP_ID       : [a-zA-Z]+          -> type(ID) ;

INTERP_LPAREN   : '(' -> type(LPAREN) ;
INTERP_RPAREN   : ')' -> type(RPAREN) ;
INTERP_PLUS     : '+' -> type(PLUS) ;
INTERP_MINUS    : '-' -> type(MINUS) ;
INTERP_STAR     : '*' -> type(STAR) ;
INTERP_SLASH    : '/' -> type(SLASH) ;
INTERP_PERCENT  : '%' -> type(PERCENT) ;
INTERP_EQEQ     : '==' -> type(EQEQ) ;
INTERP_NEQ      : '!=' -> type(NEQ) ;

/* Allow nested strings inside ${ ... } */
INTERP_STRING_START : '"' -> type(STRING_START), pushMode(STRING) ;
