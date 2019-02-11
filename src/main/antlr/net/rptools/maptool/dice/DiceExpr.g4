grammar DiceExpr;


@header {
    package net.rptools.maptool.dice;
}

diceRolls                   :   diceExpr ( ';' diceExpr)* ';'?
                            ;


diceExpr                    : assignment
                            | expr
                            ;

assignment                  : variable '=' right=expr
                            ;

variable                    : localVariable
                            | globalVariable
                            | propertyVariable
                            ;

localVariable               : LOCAL_VARIABLE
                            ;

globalVariable              : GLOBAL_VARIABLE
                            ;

propertyVariable            :PROPERTY_VARIABLE
                            ;


expr                        : left=expr op='^' right=expr                   # binaryExpr
                            | left=expr op=('*' | '/') right=expr           # binaryExpr
                            | left=expr op=('+' | '-') right=expr           # binaryExpr
                            | op='-' right=expr                             # unaryExpr
                            | variable                                      # symbol
                            | dice                                          # diceSpec
                            | integerValue                                  # integerVal
                            | doubleValue                                   # doubleVal
                            | STRING                                        # string
                            //| '(' val=diceExpr ')'                          # parenExpr
                            | group                                         # groupExpr
                            ;

group                       : '(' val=diceExpr ')'
                            | '{' val=diceExpr '}'
                            ;

dice                        : numDice? diceName=WORD diceSides diceArgumentList?
                            ;


numDice                     : integerValue
                            | group
                            ;


diceSides                   : integerValue
                            | group
                            ;


integerValue                : INTEGER
                            ;

doubleValue                 : DOUBLE
                            ;



diceArgumentList            : '{' diceArgument ( ',' diceArgument )* '}'
                            | '(' diceArgument ( ',' diceArgument )* ')'
                            ;

diceArgument                : name=WORD ( op=( '<' | '>' | '<=' | '>=' | '=' ) val=INTEGER | val=INTEGER )?
                            ;




INTEGER                     : DIGIT+;
DOUBLE                      : DIGIT+ '.' DIGIT+;

WORD                        : LETTER+;

STRING                      : SINGLE_QUOTE (SINGLE_STRING_ESC | ~['\\])* SINGLE_QUOTE
                            | DOUBLE_QUOTE (DOUBLE_STRING_ESC | ~["\\])* DOUBLE_QUOTE ;



LOCAL_VARIABLE              : LOCAL_VAR_LEADER LETTER ( DIGIT | LETTER )*;
GLOBAL_VARIABLE             : GLOBAL_VAR_LEADER LETTER ( DIGIT | LETTER )*;

PROPERTY_VARIABLE           : PROPERTY_VAR_LEADER LETTER ( DIGIT | LETTER )*
                            | PROPERTY_VAR_LEADER STRING
                            ;


WS                          : [ \t\r\n] -> skip;


fragment DIGIT              : [0-9];
fragment SINGLE_QUOTE       : '\'';
fragment DOUBLE_QUOTE       : '"';
fragment LETTER             : [a-zA-Z];
fragment LOCAL_VAR_LEADER   : '$';
fragment GLOBAL_VAR_LEADER  : '%';
fragment PROPERTY_VAR_LEADER: '@';

fragment SINGLE_STRING_ESC  : '\\' (STRING_ESC | SINGLE_QUOTE);
fragment DOUBLE_STRING_ESC  : '\\' (STRING_ESC | DOUBLE_QUOTE);
fragment STRING_ESC         : '\\' ([\\/bfnrt] | UNICODE);
fragment UNICODE            : 'u' HEX HEX HEX HEX;
fragment HEX                : [0-9a-fA-F];


