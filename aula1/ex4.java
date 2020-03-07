/*
 * Copyright 2019 ☠ Miguel Cabral ☠ 
 * 29.02.2020 16:34:52 WET 
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ex4 {
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
        scf.close();
        Scanner sc = new Scanner(System.in);
        String[] a = sc.nextLine().split(" ");
        List<Integer> lista = new ArrayList<>();
        for(int i = 0;i< a.length; i++){
            if(a[i].contains("-")){
                String a2[] = a[i].split("-");
                for(int j = 0;j< a2.length;j++){
                    if(numbers.containsKey(a2[j])){
                        lista.add(numbers.get(a2[j]));
                    }
                    else {
                        continue;
                    }
                }
                
            }
            else{
                if(numbers.containsKey(a[i])){
                    lista.add(numbers.get(a[i]));
                }
                else {
                    continue;
                }
            }
        }
        int result = 0;
        int total = 0;
        System.out.println(lista);
        for(int i = 0;i< lista.size();i++){
            for(int j = 1;j < lista.size();j++){
                if(lista.get(j) > lista.get(i)){
                    result = lista.get(i);
                    result *= lista.get(j);
                }
                else{
                    total += result;
                    result = 0;
                }
                System.out.println("r :" + result);
                System.out.println("t :" + total);
                
            }
        }
        System.out.println(total);
        sc.close();

    }
}