package newpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MetodosArchivos {

    /*Nota: El dato de tipo File ya debe estar inicializado
            File archivo = new archivo("archivo.txt")*/
    public static String leerCompilador(File archivo, String nFila, String nColumna) {
        String cadena = "";
        int contadorFila = 0;

        int fila = Integer.parseInt(nFila);
        int columna = Integer.parseInt(nColumna);

        columna += 1;
        fila += 2;

        String[] vfila;

        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader lectura = new BufferedReader(lector);
            //Mientras la cadena no este vacia
            while (cadena != null) {
                //Seguimos leyendo todas las lineas de nuestro archivo           

                cadena = lectura.readLine();
                if (cadena != null) {

                    vfila = cadena.split("\\|+");
                    if (contadorFila == fila) {

                        return vfila[columna];
                    }
                    contadorFila++;
                }

            }

        } catch (Exception ex) {
            System.out.println("Error, " + ex);
        }
        return "null";
    }

    public static String leerDiccionario(File archivo, ArrayList<String> pila, String accion) {
        String cadena = "";
        String[] acciones;

        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader lectura = new BufferedReader(lector);
            //Mientras la cadena no este vacia
            while (cadena != null) {
                //Seguimos leyendo todas las lineas de nuestro archivo           
                cadena = lectura.readLine();
                if (cadena != null) {
                    acciones = cadena.split("\\s+");
                    if (acciones[0].equals(accion.toUpperCase())) {
                        //Sin reducción en pila
                        if (acciones[0] == "R2"
                                | acciones[0] == "R7"
                                | acciones[0] == "R10"
                                | acciones[0] == "R12"
                                | acciones[0] == "R15"
                                | acciones[0] == "R19"
                                | acciones[0] == "R26"
                                | acciones[0] == "R29"
                                | acciones[0] == "R31"
                                | acciones[0] == "R33") {

                            pila.add(acciones[2]);
                            //Con reducción en pila
                        } else if (acciones[0].matches("^[R]{1}[0-9]+$")) {
                            int contador = 0;
                            for (int i = 0; i < Integer.parseInt(acciones[1]); i++) {
                                contador++;
                                pila.remove(pila.size() - 1);
                            }
                            pila.add(acciones[2]);
                        }
                    }
                } else {
                    return "error";
                }
            }
        } catch (Exception ex) {
            System.out.println("Error, " + ex);
        }
        return "null";
    }

    public static String[] leerEntrada(File archivo) {
        String cadena = "";

        String[] vfila = {};

        try {
            FileReader lector = new FileReader(archivo);
            BufferedReader lectura = new BufferedReader(lector);
            //Mientras la cadena no este vacia

            //Seguimos leyendo todas las lineas de nuestro archivo           
            cadena = lectura.readLine();
            if (cadena != null) {
                vfila = cadena.split("\\,+");
                return vfila;
            }

        } catch (Exception ex) {
            System.out.println("Error, " + ex);
        }
        return vfila;
    }

}
