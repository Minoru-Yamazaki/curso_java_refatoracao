package br.com.alura.services.util;

import java.util.Scanner;

public class Formulario {

    public static String getString(String titulo) {
        System.out.println(titulo);
        Scanner scanner = new Scanner(System.in);
        return scanner.hasNextLine() ? scanner.nextLine() : "";
    }
}
