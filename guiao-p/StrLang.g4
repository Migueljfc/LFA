grammar StrLang;

program: stat* EOF;

stat :  print      
      | defineVar   
      ;
print: 'print' expr;

defineVar : VAR ':' expr;

expr: 'input' '(' expr ')'           #InputExpr
    | e1=expr '+' e2=expr             #ConcExpr
    | '(' e1 = expr '/' e2 = expr '/' e3 = expr ')' #SubExpr
    | STRING                         #StringExpr
    | VAR                            #varExpr
    ;



STRING: '"' .*? '"';
WS: [ \t\n\r]+ -> skip;
VAR: [a-zA-Z][a-zA-Z0-9]*;
COMMENT: '//' .*? '\n' -> skip;
COMMENTMULTILINE:'/*' .*? '*/' -> skip;
ERROR: .;
