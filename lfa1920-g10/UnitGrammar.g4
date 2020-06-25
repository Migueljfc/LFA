grammar UnitGrammar;

@parser::header{
import java.util.*;
}
@parser::members{
    public static HashMap<String, HashMap<String, SymbolConv>> dimensionsMap = new HashMap<>();
}

//String 1: distancia -> String 2: "metro : m" -> Integer: 1
//                                  centimetro

program: (stat)* EOF;

stat: 
    valueConvertion ';'
    | dim ';'
    ;

dim: 'dim' dimension=ID '->' unit=ID '->' symbol=ID;

valueConvertion: fullName=ID '-' dest=ID '»' value=(INT|REAL) src=ID;

WS: [ \t\r\n]+ -> skip;
ID: [a-zA-Z]+;
INT: [0-9]+;
REAL: INT ('.' INT)?;
COMMENT: '//' .*? ('\n'|EOF) -> skip;
COMMENTMULTILINE: '/*' .*? '*/' -> skip;
ERROR: .;
