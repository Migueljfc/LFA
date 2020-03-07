/*
 * Copyright 2019 ☠ Miguel Cabral ☠ 
 * 24.02.2020 16:34:52 WET 
 */

import java.io.File;
import java.io.IOException;
import java.util.*; 

public class ex3 {
	static Scanner sc = new Scanner(System.in);

	public static void main (String[] args) throws IOException {
        Hashtable<String,Integer> numbers = new Hashtable<>();
        int n = 0;
        String s = "";
        File f = new File("/home/miguel/MEGAsync/2 ano/LFA/aula1/bloco1/numbers.txt");
        Scanner scf = new Scanner(f);
        while(scf.hasNextLine()){
            Scanner scf2 = new Scanner(scf.nextLine()); 
            if(scf2.hasNextInt()){
                n = scf2.nextInt();
               
            }
            if(scf2.hasNext()){
                s = scf2.nextLine();
                s = s.split("-")[1].trim();
               

            }
            numbers.put(s, n);
        }
        
        String[] a = sc.nextLine().split(" ");
        scf.close();
        
        
        for(int i = 0;i< a.length; i++){
            if(a[i].contains("-")){
                String a2[] = a[i].split("-");
                for(int j = 0;j< a2.length;j++){
                    if(numbers.containsKey(a2[j])){
                        System.out.print(numbers.get(a2[j]) + " ");
                    }
                    else {
                        System.out.print(a2[j]+ " " );
                    }
                }
                
            }
            else{
                if(numbers.containsKey(a[i])){
                    System.out.print(numbers.get(a[i]) + " ");
                }
                else {
                    System.out.print(a[i]+ " " );
                }
            }
        }
        System.out.println();
        
    
    }
}