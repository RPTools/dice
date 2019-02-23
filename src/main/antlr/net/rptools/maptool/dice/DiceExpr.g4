grammar DiceExpr;


@header {
    package net.rptools.maptool.dice;
}

diceRolls                   : diceExpr ( ';' diceExpr)* ';'?
                            ;


diceExpr                    : assignment
                            | expr
                            | instruction
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
                            | group                                         # groupExpr
                            ;

group                       : '(' val=diceExpr ')'                          # parenGroup
                            | '{' val=diceExpr '}'                          # braceGroup
                            ;

dice                        : numDice? diceName=WORD diceSides diceArgumentList?
                            ;


numDice                     : integerValue
                            | group
                            ;


diceSides                   : integerValue
                            | group
                            ;


instruction                 : INSTRUCTION_LEADER instructionName=WORD instructionArgumentList
                            ;

instructionArgumentList     : (instructionArgument ( ',' instructionArgument))*
                            ;

instructionArgument         : WORD
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

INSTRUCTION_LEADER          : '%';


LOCAL_VARIABLE              : LOCAL_VAR_LEADER LETTER ( DIGIT | LETTER )*;
GLOBAL_VARIABLE             : GLOBAL_VAR_LEADER LETTER ( DIGIT | LETTER )*;

PROPERTY_VARIABLE           : PROPERTY_VAR_LEADER LETTER ( DIGIT | LETTER )*
                            | PROPERTY_VAR_LEADER STRING
                            ;

MULTIlINE_COMMENT           : '/*' .*? '*/' -> skip;

WS                          : [ \t\r\n] -> skip;


fragment DIGIT              : [0-9];
fragment SINGLE_QUOTE       : '\'';
fragment DOUBLE_QUOTE       : '"';
fragment LETTER             : [a-zA-Z];
fragment LOCAL_VAR_LEADER   : '$';
fragment GLOBAL_VAR_LEADER  : '#';
fragment PROPERTY_VAR_LEADER: '@';



fragment SINGLE_STRING_ESC  : '\\' (STRING_ESC | SINGLE_QUOTE);
fragment DOUBLE_STRING_ESC  : '\\' (STRING_ESC | DOUBLE_QUOTE);
fragment STRING_ESC         : '\\' ([\\/bfnrt] | UNICODE);
fragment UNICODE            : 'u' HEX HEX HEX HEX;
fragment HEX                : [0-9a-fA-F];


