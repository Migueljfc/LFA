grammar MainGrammar;

@parser::header {
import java.util.*;
}

@parser::members {
static protected Map<String,Symbol> symbolTable = new HashMap<>();
}

program: (stat)* EOF;

stat:
		if_clause
  | loop
  | instruction ';'
  ;

block: '{' stat* '}';

if_clause:'if' '(' expression ')' blockIf = block ('else' blockElse = block) ?;

loop:  
    'for' '(' declarations  ';' expression ';' expression ')' block  #forLoop
  | 'while' '(' expression ')' block											                          #whileLoop
  | 'do' block 'while' '(' expression ')' ';'										                    #doWhileLoop 
  ;

instruction: 
	  declarations
	| prints
  | assignments
  | expression
  ;
 
declarations: type ID '=' expression;

assignments: ID '=' expression;

prints: 'print' '(' expression ')';

expression returns[Type e_type, String var]:
	'(' e=expression ')' 		                        #parenExpr
  | e1=expression op=('*' | '/') e2=expression    #multDivExpr
  | e1=expression op=('+' | '-') e2=expression    #addSubExpr
  | e1=expression op=OP_COND e2=expression        #conditionalExpr
  | e=ID op=('++' | '--')                         #incrementsExpr
  | e=expression unit=ID                          #unitVarExpr 
  | INT                                           #intExpr
  | REAL                                          #realExpr
  | BOOLEAN                                       #booleanExpr
  | ID                                            #idExpr
  ;
        
type returns[Type t_type]:
    'int'       #IntType
  | 'real'      #RealType
  | 'unit'      #UnitType
  | 'boolean'   #BooleanType
  ;

OP_COND: '>' | '<' | '!=' | '==' | '>=' | '<=';
BOOLEAN : 'true' | 'false';
WS: [ \t\r\n]+ -> skip;
ID: [a-zA-Z]+;
INT: [0-9]+;
REAL: INT ('.' INT)?;
COMMENT: '//'.*? ('\n'|EOF) -> skip;
COMMENTMULTILINE: '/*' .*? '*/' -> skip;
ERROR: .;