/*
 * Copyright 2019 ☠ Miguel Cabral ☠ 
 * 17.02.2020 17:08:19 WET 
 */

import java.util.*; 

public class ex2 {
	static Scanner sc = new Scanner(System.in);

	public static void main (String[] args) {
		
		Stack<Double> pilha = new Stack<Double>();
		System.out.println("operation(number number op / number number number op1 op2 ):");
		while(sc.hasNextLine()){
			if(sc.hasNextDouble()){
				pilha.push(sc.nextDouble());
			}
			else{
				String operador = sc.next();
				if(pilha.size() < 2){
					System.err.println("Invalid input operation");
					System.exit(1);
				}
				double op1 = pilha.pop();
				double op2 = pilha.pop();
				assert(pilha.size() <= 1):"Too many operands on the stack";
				double resultado = 0.0;
				switch(operador){
					case "+":
                        resultado = op1 + op2;
                        break;
                    case "-":
                        resultado = op1 - op2;
                        break;
                    case "*":
                        resultado = op1 * op2;
                        break;
                    case "/":
                        resultado = op1 / op2;
						break;
					default:
						System.err.println("Invalid operator");
						System.exit(2);
				}
				pilha.push(resultado);

				
				
			}
			
			System.out.print("Stack: " + pilha);
			System.out.println();
			
			
			
		}
	}
}
