A DAL é uma linguagem compilada cuja linguagem destino é Java. A análise
sintáctica e semântica é feita em antlr4 .

Para compilar é necessário correr os seguintes comandos a partir do terminal:
java -ea MainGrammarMain Output progTest/DimensoesCONFIG.txt
"programa.txt" em que o "programa.txt"é o programa que queremos correr,
seguidamente é só fazer javac Output.java para compilar o ficheiro em java e
finalmente correr o programam com java Output.
