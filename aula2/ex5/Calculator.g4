grammar  Calculator;

program:  stat * EOF;

stat : expr ? NEWLINE           #Expression
     |assignment ? NEWLINE      #Variable
     ;

assignment : ID '=' expr;
expr : op=('-'|'+') expr        #ExprIntegerSignal
    | expr op=('*'|'/'|'%') expr #ExprMultDivMod
    | expr op=('+'|'-') expr      #ExprAddSub
    | Integer                     #ExprInteger
    | '(' expr ')'                #ExprParent
    | ID                          #ExprID
    ;
Integer: [0-9]+; //implement with long integers
NEWLINE: '\r'? '\n';
ID: [a-zA-Z]+ ;
WS: [ \t]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;
