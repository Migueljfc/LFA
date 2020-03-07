/*
 * Copyright 2019 ☠ Miguel Cabral ☠ 
 * 17.02.2020 16:34:52 WET 
 */

import java.util.*; 

public class ex1 {
	static Scanner sc = new Scanner(System.in);

	public static void main (String[] args) {
		double op1 = 0, op2 = 0;
		String operador = "";
		System.out.println("Operation (number op number):  ");
		while(sc.hasNext() ){
			try{
				op1 = sc.nextDouble();
			
			}catch(Exception e){
				System.err.println("Erro no operando 1");
				System.exit(1);
			}
			
			try{
				operador = sc.next();
			}catch(Exception e){
				System.err.println("Erro no operador");
				System.exit(1);
			}
			try{
				op2 = sc.nextDouble();
			}catch(Exception e){
				System.err.println("Erro no operando 2");
				System.exit(1);
			}
		}
		switch(operador){
			case "+":
				System.out.println(op1 + " + " + op2 +" = " + (op1 + op2));
			break;
			case "-":
				System.out.println(op1 + " - " + op2 +" = " + (op1 - op2));
			break;
			case "*":
				System.out.println(op1 + " * " + op2 +" = " + (op1 * op2));
			break;
			case "/":
				System.out.println(op1 + " / " + op2 +" = " + (op1 / op2));
			break;
			default :
				System.err.println("ERRO ");
			break;
		}
			
		
			
	}
}

