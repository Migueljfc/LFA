grammar Calculator;

program: stat* EOF;

stat: expr ';'           #Expression
    | assignement ';'     #Variable
    | print ';'           #PrintFrac
    | reduce               #ReduceExpr
    ;
assignement : expr '->' ID;

print: 'print' expr 
      | 'print' reduce;  

reduce: 'reduce' expr;       

expr: op=('+'|'-') expr     #ExprIntegerSinal
    | Integer '/' Integer   #ExprFrac
    | expr op = ('*'| ':' | '%') expr #ExprMultDiv
    | expr op = ('+'| '-') expr   #ExprAddSub
    | expr op = '^' expr  #ExprElev
    | Integer               #ExprIntege
    |'(' expr ')'           #ExprParent
    | ID                    #ExprID 
    | 'read' '"' expr.? '"'      #ExprRead
    ;

Integer:[0-9]+;
WS: [\t\r\n ]+ -> skip;
ID: [a-zA-Z0-9]+;
COMMENT: '//' .*? '\n' -> skip;

