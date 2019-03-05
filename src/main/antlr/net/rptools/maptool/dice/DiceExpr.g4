grammar DiceExpr;


@header {
    package net.rptools.dice;
}

diceRolls                   : diceExprTopLevel ( ';' diceExprTopLevel)* ';'?
                            ;

diceExprTopLevel            : diceExpr
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
                            | dice                                          # diceSpec
                            | group                                         # groupExpr
                            | variable                                      # symbol
                            | integerValue                                  # integerVal
                            | doubleValue                                   # doubleVal
                            | STRING                                        # string
                            ;

group                       : '(' val=diceExpr ')'                          # parenGroup
                            | '{' val=diceExpr '}'                          # braceGroup
                            ;

dice                        : numDice? diceName diceSides diceArguments?
                            ;


numDice                     : integerValue
                            | group
                            ;


diceSides                   : integerValue
                            | group
                            ;


instruction                 : INSTRUCTION_LEADER instructionName=identifier instructionArgumentList
                            ;

instructionArgumentList     : (instructionArgument ( ',' instructionArgument))*
                            ;

instructionArgument         : identifier
                            | variable
                            | STRING
                            ;

integerValue                : INTEGER
                            ;

doubleValue                 : DOUBLE
                            ;



diceArguments               : diceArgumentList
                            ;

diceArgumentList            : '{' diceArgument ( ',' diceArgument )* '}'
                            | '(' diceArgument ( ',' diceArgument )* ')'
                            ;


diceArgument                : 'cs'   op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargCriticalSuccess
                            | 'cf'   op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargCriticalFailure
                            | 's'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal    # dargSuccess
                            | 'f'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal    # dargFail
                            /*
                            | 'add'  val=diceArgumentVal                                         # dargAdd
                            | 'sub'  val=diceArgumentVal                                         # dargSubtract
                            | 'sub0' val=diceArgumentVal                                         # dargSubtractMin0
                            | 'subn' val=diceArgumentVal                                         # dargSubtractNoMin
                            | 'r'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal    # dargReroll
                            | 'ro'   op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargRerollOnce
                            | 'k'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal    # dargKeep
                            | 'd'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal    # dargDrop
                            | 'kh'   val=diceArgumentVal                                         # dargKeepHighest
                            | 'kl'   val=diceArgumentVal                                         # dargKeepLowest
                            | 'dh'   val=diceArgumentVal                                         # dargDropHighest
                            | 'dl'   val=diceArgumentVal                                         # dargDropLowest
                            | 'e'    op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargExplode
                            | 'eo'   op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargExplodeOnce
                            | 'ce'   op=( '<' | '>' | '<=' | '>=' | '=' )? val=diceArgumentVal?    # dargCompoundExploding
                            | 'asc'                                                              # dargAscendingOrder
                            | 'desc'                                                             # dargDescendingOrde
                            | 'format' op='='? val=diceArgumentVal                               # dargFormat
                            */
                            ;


diceArgumentVal             : identifier                                        # dargIdentifier
                            | variable                                          # dargVariable
                            | STRING                                            # dargString
                            | integerValue                                      # dargInteger
                            | doubleValue                                       # dargDouble
                            ;

diceName                    : LETTER+
                            | LETTER ( integerValue | LETTER)* LETTER
                            ;

identifier                  :  LETTER ( integerValue | LETTER)*
                            ;


INTEGER                     : DIGIT+;
DOUBLE                      : DIGIT+ '.' DIGIT+;

/*DICE_NAME                   : LETTER+
                            | LETTER  ( DIGIT | LETTER)* LETTER
                            ;*/


//WORD                        : LETTER ( DIGIT | LETTER)*;

STRING                      : SINGLE_QUOTE (SINGLE_STRING_ESC | ~['\\])* SINGLE_QUOTE
                            | DOUBLE_QUOTE (DOUBLE_STRING_ESC | ~["\\])* DOUBLE_QUOTE
                            ;

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
LETTER             : [a-zA-Z];
fragment LOCAL_VAR_LEADER   : '$';
fragment GLOBAL_VAR_LEADER  : '#';
fragment PROPERTY_VAR_LEADER: '@';



fragment SINGLE_STRING_ESC  : '\\' (STRING_ESC | SINGLE_QUOTE);
fragment DOUBLE_STRING_ESC  : '\\' (STRING_ESC | DOUBLE_QUOTE);
fragment STRING_ESC         : '\\' ([\\/bfnrt] | UNICODE);
fragment UNICODE            : 'u' HEX HEX HEX HEX;
fragment HEX                : [0-9a-fA-F];


