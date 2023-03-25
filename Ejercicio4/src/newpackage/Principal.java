package newpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {

    public static void main(String[] args) throws IOException {
        MetodosArchivos mArchivos = new MetodosArchivos();
        analizador a = new analizador();
        a.generarEntrada();

        File compilador = new File("compiladorExcel.txt");
        File diccionario = new File("diccionario.txt");
        File vEntrada = new File("valoresEntrada.txt");

        ArrayList<String> pila = new ArrayList<String>();
        ArrayList<String> entrada = new ArrayList<String>();
        String accion = "";
        String fila, columna;

        String[] FilaArchivo;
        //Separamos y guardamos la entrada en un arreglo
        FilaArchivo = mArchivos.leerEntrada(vEntrada);
        entrada.add("23");
        //Guardondo la entrada en la pila
        for (int i = FilaArchivo.length - 1; i >= 0; i--) {
            entrada.add(FilaArchivo[i]);
        }
        //iniciamos nuestra pila en cero
        pila.add("0");
        imprimirPila(pila, entrada, accion);
        // entrada.imprimir();
        salir:
        while (!accion.equals("r0")) {
            fila = pila.get(pila.size() - 1);
            columna = entrada.get(entrada.size() - 1);
            accion = mArchivos.leerCompilador(compilador, fila, columna);
            //Si la acción es un desplazamiento Dn
            if (accion.equals("r0")) {
                break salir;
            } else if (accion.matches("^[d]{1}[0-9]+$")) {
                //Guardamos el valor de la entrada en la pila
                pila.add(entrada.remove(entrada.size() - 1));
                //Guardamos en la pila el valor des desplazamiento  
                pila.add((accion.length() == 2 ? accion.substring(accion.length() - 1) : accion.substring(accion.length() - 2)));
                imprimirPila(pila, entrada, accion);
            } //Si la acción es una reducción Rn
            else if (accion.toUpperCase().matches("^[R]{1}[0-9]+$")) {
                mArchivos.leerDiccionario(diccionario, pila, accion);
                fila = pila.get(pila.size() - 2);
                columna = pila.get(pila.size() - 1);
                String aux = mArchivos.leerCompilador(compilador, fila, columna);
                //le quietamos los decimales  14.0 --> 14
                pila.add((String) aux.subSequence(0, aux.length() - 2));
                imprimirPila(pila, entrada, accion);
            } else {
                //Mostrar mensaje de error en el programa y continuar
                if (mArchivos.leerDiccionario(diccionario, pila, accion) == "error") {
                    System.out.println("Error 2");
                    //Desaparece 1 y 12 en archivo "Analizado.txt"
                    //Revisar si es error de analizador.java o métodos archivos.java
                    System.exit(0);
                } else {
                    System.out.println("Error 1");
                    System.exit(0);
                }
            }
        }
        imprimirPila(pila, entrada, accion);
    }

    public static void imprimirPila(ArrayList<String> pila, ArrayList<String> entrada, String accion) {
        for (String elementos : pila) {
            System.out.print(elementos + " ");
        }
        System.out.print("\t\t");
//        for (String elementos : entrada) {
//            System.out.print(elementos + " ");
//
//        }
        for (int i = entrada.size() - 1; i >= 0; i--) {
            System.out.print(entrada.get(i) + " ");
        }
        System.out.println("\t\t" + accion);
    }
}
