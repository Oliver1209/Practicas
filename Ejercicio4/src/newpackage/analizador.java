/*
 * Leer un .txt con un programa que sume 2 variables y la analiza contando los ;, quitando espacios y eliminando comentarios
 * Creado por: Sergio José Aguas Montaño
 * Fecha: 23/enero/2023
 * Modificado: 27/enero/2023
 */
package newpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergio José Aguas Montaño
 */
public class analizador {

    static File archivoLeer; //Archivo a analizar (suma de las 2 variables)
    static File archivoFinal; //Nombre del archivo a crear sin los comentarios, ni espacios
    static File archivoAnalizado; //Archivo con las palabras separadas y analizadas

    File Diccionario = new File("diccionario.txt");
    static String aux = "";
    static String[] PalabrasFichero; //Arreglo de guardado de las palabras
    static String[] datos;
    static int p = 0;
    Listas lista = Listas.getInstancia();

    /**
     * Método main donde ponemos el nombre del .txt a anaClizar primero (suma de
     * numeros con variables)
     */
    public void generarEntrada() throws IOException {
        String archivo = "Actividad2.txt";
        leerFichero(archivo); //Método principal que hace todo el despapaye}
        identificadortokens();
    }

    /**
     * Método principal que hace todo el despapaye
     *
     * @param archivo
     */
    public static void leerFichero(String archivo) {
        String renglones = null;
        String renglon = null;

        archivoLeer = new File(archivo); //Parámetro del nombre del .txt a analizar
        archivoFinal = new File("final.txt"); //Parámetro del nombre con el que se crea el .txt sin cmentarios ni espacios
        try {
            if (archivoFinal.exists()) {
                archivoFinal.delete();
                archivoFinal.createNewFile(); //Si se detecta que el archivo ya existe, lo elimina y crea uno nuevo (no sobre escribe)
                //System.out.println("Archivo creado con exito");
            } else {
                archivoFinal.createNewFile();
            }

            FileReader lector = new FileReader(archivoLeer);
            BufferedReader lectura = new BufferedReader(lector);
            int contar_puntocoma = 0;
            renglones = lectura.readLine();
            PalabrasFichero = renglones.split("\\s+");
            while (renglones != null) {
                int suich = 0;

                if (renglones.equals("") || renglones.contains(" * ") || renglones.contains("//") || renglones.equals(" */") || ((renglones.startsWith("/**") || renglones.startsWith("/*")))) { //Sin nada
                    suich = 1;
                }

                switch (suich) {
                    case 1: {
                        //System.out.println("comentario normal");
                        renglon = renglones;
                        //escribirFichero(renglon);
                        renglones = lectura.readLine();
                        PalabrasFichero = renglones.split("\\s+");
                        //compararDiccionario(palabras[i], File archivo); //Crear método que compare la palabra del arreglo con el diccionario
                        break;
                    }

                    default: {
                        // Si el ultimo caracter es un punto y coma
                        if (renglones.substring(renglones.length() - 1).equals(";")) {
                            // aumenta el contador en uno
                            contar_puntocoma++;
                        }
                        renglon = renglones;
                        escribirFichero(renglon);
                        renglones = lectura.readLine();
                        //compararDiccionario(palabras[i]); //Crear método que compare la palabra del arreglo con el diccionario
                        break;
                    }
                }
            }
            analizarFichero("final.txt");//Método de la tercera actividad

            //System.out.println("Total de ';' = " + contar_puntocoma);
        } catch (FileNotFoundException e) {
            System.out.println("Error, no se ha encontrado el archivo" + e);
        } catch (IOException e) {
            System.out.println("Error, error en la creacion/apertura del archivo" + e);
        }
    }

    /**
     * Método que lee el fichero a analizar para separar las letras
     *
     * @param fichero Parámetro para pasar el nombre del .txt a analizar
     */
    public static void analizarFichero(String fichero) {
        String renglones = null;
        String renglon = null;
        archivoLeer = new File(fichero);
        archivoAnalizado = new File("Analizado.txt"); //Nombre del .txt final del trabajo 3

        try {
            if (archivoAnalizado.exists()) {
                archivoAnalizado.delete();
                archivoAnalizado.createNewFile();
                //System.out.println("Archivo creado con exito");
            } else {
                archivoAnalizado.createNewFile();
            }
            String numeritos = "";
            FileReader lector = new FileReader(archivoLeer);
            BufferedReader lectura = new BufferedReader(lector);
            while (lectura != null) {
                renglones = lectura.readLine();
                renglon = renglones;
                char letrita;
                String palabra = "";
                //Separar las palabras de renglones (String que guarda la línea actual leída del .txt a analizar)
                if (renglon != null) {
                    for (int i = 0; i < renglon.length(); i++) {
                        letrita = renglon.charAt(i);
                        int suich = 0;
                        if (letrita == ' ') {
                            if (renglon.charAt(i + 1) == ' ') {
                                i++;
                            } else if (palabra == "") {
                                
                            } else if (renglon.charAt(i + 1) != ' ') {
                                suich = 1;
                            } else {
                                suich = 1;
                            }
                        } else if (letrita == ';') {
                            suich = 2;
                        } else if (letrita == '(') {
                            suich = 5;
                        } else if (letrita == ')') {
                            suich = 6;
                        } else if (letrita == '.') {
                            suich = 3;
                        } else if (letrita == '{') {
                            suich = 7;
                        } else if (letrita == '}') {
                            suich = 8;
                        } else if (letrita == ',') {
                            suich = 4;
                        } else if (letrita == '[') {
                            suich = 9;
                        } else if (letrita == ']') {
                            suich = 10;
                        } else if (letrita == '=') {
                            suich = 11;
                        } else if (letrita == '>') {
                            suich = 11;
                        } else if (letrita == '<') {
                            suich = 12;
                            //ARREGLAR INDICE FUERA DEL RANGO AL LEER EL 3 *****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************++++++++
                        }else if ((letrita == '1' || letrita == '2' || letrita == '3' || letrita == '4' || letrita == '5' || letrita == '6' || letrita == '7' || letrita == '8' || letrita == '9' || letrita == '0') && (i < renglon.length())) {
                            if ((renglon.charAt(i + 1) == '1' || renglon.charAt(i + 1) == '2' || renglon.charAt(i + 1) == '3' || renglon.charAt(i + 1) == '4' || renglon.charAt(i + 1) == '5' || renglon.charAt(i + 1) == '6' || renglon.charAt(i + 1) == '7' || renglon.charAt(i + 1) == '8' || renglon.charAt(i + 1) == '9' || renglon.charAt(i + 1) == '0') && (i < renglon.length())) {
                                numeritos = numeritos.concat(numeritos + letrita);
                            } else if (renglon.charAt(i + 1) == ' ') {
                                suich = 13;
                            } else {
                                suich = 13;
                            }
                        }
                        //ARREGLAR INDICE FUERA DEL RANGO AL LEER EL 3 *****************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************++++++++
                        
                        switch (suich) {
                            case 1: { //Escritura normal en el caso de encontrar el final de una palabra o por un espacio " "

                                escribirFicheroAnalizado(palabra.trim());
                                palabra = String.valueOf(letrita);
                                palabra = "";
                                break;
                            }
                            case 2: { //Caso de encontrar un punto y coma

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 3: { //Caso de encontrar un punto normal

                                escribirFicheroAnalizado(palabra.trim());
                                palabra = String.valueOf(letrita);
                                escribirFicheroAnalizado(palabra.trim());
                                palabra = "";
                                break;
                            }
                            case 4: { //Caso de encontrar una coma

                                escribirFicheroAnalizado(palabra.trim());
                                palabra = String.valueOf(letrita);
                                escribirFicheroAnalizado(palabra.trim());
                                palabra = "";
                                break;
                            }
                            case 5: { //Caso de encontrar un parentesis de apertuta "("

                                escribirFicheroAnalizado(palabra.trim());
                                palabra = String.valueOf(letrita);
                                escribirFicheroAnalizado(palabra.trim());
                                palabra = "";
                                break;
                            }
                            case 6: { //Caso de encontrar un parentesis de cerradura ")"

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 7: { //Caso de llave de apertura "{"

                                palabra = String.valueOf(letrita);
                                escribirFicheroAnalizado(palabra.trim());
                                palabra = "";
                                break;
                            }
                            case 8: { //Caso de llave de cerraudra "}"

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 9: {

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 10: {

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 11: {

                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }

                                break;
                            }
                            case 12: {
                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                } else {
                                    palabra = String.valueOf(letrita);
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                }
                                break;
                            }
                            case 13: {
                                //Guarda la palabra concatenada de números para agregarla al archivo
                                if (palabra.trim() != "") {
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = numeritos;
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                    numeritos = "";
                                } else{
                                    palabra = numeritos;
                                    escribirFicheroAnalizado(palabra.trim());
                                    palabra = "";
                                    numeritos = "";
                                }
                                break;
                            }
                            default: {
                                palabra = palabra + letrita;
                                System.out.println(palabra);
                                break;
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            /*
            StringTokenizer separador = new StringTokenizer(renglones,";. ",true); //Elimina la primera palabra y deja la segunda despyes del punto
            System.out.println("Hay un total de: " + separador.countTokens() + " tokens.");
            while(separador.hasMoreTokens()){
                    escribirFicheroAnalizado(separador.nextToken());
            }
            //aux += renglon+"\n";
             */
        } catch (FileNotFoundException e) {
            System.out.println("Error, no se ha encontrado el archivo" + e);
        } catch (IOException e) {
            System.out.println("Error, error en la creacion/apertura del archivo" + e);
        }

    }//Fin método analizarFichero

    /**
     * Método de guardado en el archivo del trabajo 2 (no mover, asi sirve bien)
     *
     * @param renglon String por donde se pasa el renglon a escribir
     */
    public static void escribirFichero(String renglon) {
        try {
            FileWriter escribir = new FileWriter(archivoFinal, true);
            escribir.write(renglon + "\n");
            escribir.close();
            aux += renglon + "\n";
        } catch (IOException e) {
            System.out.println("Error, no se pudo escribir sobre el archivo");
        }
    }//Fin método escribirFichero

    /**
     * Método de guardado en el archivo del trabajo 3
     *
     * @param renglon
     */
    public static void escribirFicheroAnalizado(String renglon) {
        try {
            FileWriter escribir = new FileWriter(archivoAnalizado, true);
            escribir.write(renglon + "\n");
            escribir.close();
        } catch (IOException e) {
            System.out.println("Error, no se pudo escribir sobre el archivo");
        }
    }//Fin método escribirFichero

    /**
     * Método para retornar la cantidad de lineas de un txt
     *
     * @txt especificar el nombre del txt a verificar, junto con su
     * extension(.txt)
     * @return
     */
    public static int tamañoTxt(String txt) {
        Scanner sc = new Scanner(System.in);
        String texto = "";
        int tam = 0;
        try {
            sc = new Scanner(new FileReader(txt));//lee el archivo txt
            while (sc.hasNext()) {
                texto = sc.nextLine();
                tam++;
            }
        } catch (IOException e) {
            System.out.println("Error, no se pudo escribir sobre el archivo");
        }
        return tam;
    }//Fin método escribirFichero

    /**
     * Método para identificar los tokens usando expresiones regulares
     */
    public static void identificadortokens() throws IOException {
        String palabra;
        String signop = "[;:,.()!¡{}]";
        String corchete_abierto = "[", corchete_cerrado = "]";
        String oparitmeticas = "([+]|[-]|[\\/]|[*])";
        String identificadores = "[a-zA-Z_$][a-zA-Z0-9_$]*";
        String numeros = "[0-9]+";
        String oplogicos = "(&&|\\|\\||!|\\^)";
        //  String literales="\"[^\"\\\\]*(\\\\.[^\"\\\\]*)*\"|'[^'\\\\]*(\\\\.[^'\\\\]*)*'";
        String opcomparacion = "==|!=|<=|>=|<|>|=";

        String enteros = "[0-9]+";
        String decimales = "^-?\\d+(\\.\\d+)?$";
        String booleanos = "^true|false$";
        String cadenas = "^[a-zA-Z0-9_]";

        String reservadas = "\\b(abs(tract)?|by(te)?|ca(se)?|c(at(ch)?|on(st|ti(nue)?))|de(fault|lete)?|o(uble)?|else|en(um)?|ex(tends)?|fin(al(ly)?)?|loat|go(to)?|im(ple(ments|ment)|port)?|in(stanceof)?|l(ong)?|na(tive)?|ne(w)?|nu(ll)?|p(ackage|rivate|rotected|ublic)?|re(turn)?|sh(ort)?|st(atic|rictfp)?|su(per)?|sw(itch)?|sy(nchronized)?|th(is)?|thr(ow(s)?)?|tr(ansient)?|try|vo(id|latile)?|wh(ile)?|if)\\b";

        //String reservadas=("\\b(abstract|System|println|main|args|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b");
        Scanner sc = new Scanner(System.in);
        PrintWriter practica2;
        FileWriter txt;
        txt = new FileWriter(new File("Identificados.txt"));//crea el txt donde se guardara los tokens junto con su tipo
        practica2 = new PrintWriter(txt);

        try {
            sc = new Scanner(new FileReader("Analizado.txt"));//lee el archivo txt
            datos = new String[tamañoTxt("Analizado.txt")];//arreglo donde se guardará el tipo de cada token para leerlo en el txt y sacar sus valores

            while (sc.hasNext()) {//lee todas las lineas del archivo 
                palabra = sc.nextLine();//se guarda la linea donde se encuentra el scanner en un string 

                Pattern patron1 = Pattern.compile(signop);//expresion regular
                Matcher m1 = patron1.matcher(palabra);

                Pattern patron2 = Pattern.compile(oparitmeticas);//expresion regular
                Matcher m2 = patron2.matcher(palabra);

                Pattern patron3 = Pattern.compile(numeros);//expresion regular
                Matcher m3 = patron3.matcher(palabra);

                Pattern patron4 = Pattern.compile(identificadores);//expresion regular
                Matcher m4 = patron4.matcher(palabra);

                Pattern patron5 = Pattern.compile(reservadas);//expresion regular
                Matcher m5 = patron5.matcher(palabra);

                Pattern patron6 = Pattern.compile(oplogicos);//expresion regular
                Matcher m6 = patron6.matcher(palabra);

                Pattern patron7 = Pattern.compile(opcomparacion);//expresion regular
                Matcher m7 = patron7.matcher(palabra);

                Pattern patron8 = Pattern.compile(enteros);//expresion regular
                Matcher m8 = patron8.matcher(palabra);

                Pattern patron9 = Pattern.compile(decimales);//expresion regular
                Matcher m9 = patron9.matcher(palabra);

                Pattern patron10 = Pattern.compile(booleanos);//expresion regular
                Matcher m10 = patron10.matcher(palabra);

                Pattern patron11 = Pattern.compile(cadenas);//expresion regular
                Matcher m11 = patron11.matcher(palabra);

                //CREAR 2 PATRONES MÁS PARA "<"  ">"

                /* Pattern patron8 = Pattern.compile(literales);//expresion regular
                Matcher m8 = patron7.matcher(palabra);*/
 /*En el siguiente fragmento de código se va analizar la palabra y lo va a clasificar según las expresiones regulares
               , además de que se almacenará en el arreglo "datos" los tipos de los tokens para sacar los valores del txt para la tabla del excel*/
                if (m1.matches() || palabra.equals(corchete_abierto) || palabra.equals(corchete_cerrado)) {

                    practica2.print(palabra + "\t" + "Signo de puntuación" + "\n");
                    datos[p] = palabra;
                    p++;
                } else if (m7.matches()) {
                    if (palabra.equals("=")) {
                        datos[p] = palabra;
                        p++;
                    } else {
                        practica2.print(palabra + "\t" + "Operador comparación" + "\n");
                        datos[p] = "opRelac";
                        p++;
                    }
                }/*else if (m8.matches()) {
                    practica2.print(palabra +"\t" + "literales" + "\n");
                }*/ else if (m2.matches()) {
                    practica2.print(palabra + "\t" + "Operación aritmética" + "\n");
                    if (palabra.equals("+")) {
                        datos[p] = "opSuma";
                        p++;
                    } else if (palabra.equals("*")) {
                        datos[p] = "opMul";
                        p++;
                    } else {
                        datos[p] = palabra;
                        p++;
                    }
                } /*else if (m3.matches()) {
                    practica2.print(palabra +"\t" + "Números" + "\n");
                }*/ else if (m5.matches()) {
                    practica2.print(palabra + "\t" + "ES PALABRA RESERVADA" + "\n");
                    datos[p] = palabra;
                    p++;
                } else if (m6.matches()) {
                    practica2.print(palabra + "\t" + "Operador lógico" + "\n");
                    if (palabra == "==") {
                        datos[p] = "opIgualdad";
                        p++;
                    } else if (palabra == "||") {
                        datos[p] = "opOr";
                        p++;
                    } else if (palabra == "&&") {
                        datos[p] = "opAnd";
                        p++;
                    } else if (palabra == "!=") {
                        datos[p] = "opNot";
                        p++;
                    }
                } else if (m4.matches()) {
                    if (palabra.equals("int") || palabra.equals("boolean") || palabra.equals("String") || palabra.equals("char") || palabra.equals("float") || palabra.equals("double")) {
                        practica2.print(palabra + "\t" + "tipo" + "\n");
                        datos[p] = "tipo";
                        p++;
                    } else {
                        datos[p] = "identificador";
                        p++;
                    }
                } else if (m8.matches()) {
                    practica2.print(palabra + "\t" + "Entero" + "\n");
                    datos[p] = "entero";
                    p++;
                } else if (m9.matches()) {
                    practica2.print(palabra + "\t" + "Decimal" + "\n");
                    datos[p] = "real";
                    p++;
                } else if (m10.matches()) {
                    practica2.print(palabra + "\t" + "booleano" + "\n");
                    datos[p] = "opRelac";
                    p++;
                } else if (m11.matches()) {
                    practica2.print(palabra + "\t" + "Cadena" + "\n");
                    datos[p] = "cadena";
                    p++;
                } else {
                    practica2.print(palabra + "\n");
                    datos[p] = "";
                    p++;
                }
            }//fin de while
        } finally {
            if (sc != null) {
                sc.close();
            }
        }//fin de finally
        practica2.close();
        //PARTE 2 
        //      System.out.println("ARREGLO CON TOKENS");
        for (int i = 0; i < datos.length; i++) {
            //       System.out.println(datos[i]);
        }
        valoresEntrada(datos);//manda a llamar método para obtener los valores y escribirlos en un txt
    }//FIN MÉTODO IDENTIFICAR TOKENS

    /**
     * Método para asignar los valores de acuerdo al tipo del token, para la
     * entrada del analizador sintáctico
     *
     * @throws IOException
     */
    public static void valoresEntrada(String[] tipo) throws IOException {
        int contador = 0; //contador para guardar lo del txt en el arreglo "valores"
        String texto; //almacena cada linea del txt de donde sacaremos los valores de las entradas
        String[] valores = new String[48]; //arreglo para guardar los valores y tipos de 
        Scanner sc = new Scanner(System.in);
        PrintWriter compilador;
        FileWriter txt;
        txt = new FileWriter(new File("valoresEntrada.txt"));
        compilador = new PrintWriter(txt);
        //   analizarFichero("final.txt");// el txt a analizar y separar tokens
        try {
            sc = new Scanner(new FileReader("compilador.txt"));//lee el archivo txt
            for (int i = 0; i < 24; i++) {//for para recorrer las lineas que se necesitan para almacenar los valores de las entradas junto con sus tipos
                String linea = sc.nextLine();
                String valor[] = linea.split("\\s");//guarda los datos en diferente posicion separado por espacios en blanco
                for (int j = 0; j < valor.length; j++) {//guarda los valores en el arreglo "valores"
                    valores[contador] = valor[j];
                    contador++;
                }
            }
            //for anidados, el primero para recorrer los tokens que fueron tomados, y el que se encuentra dentro para el arreglo con los valores del compilador
            for (int i = 0; i < tipo.length; i++) {
                for (int j = 0; j < valores.length; j++) {
                    if (tipo[i] != null) {
                        if (tipo[i].equals(valores[j])) {
                            if (i == tipo.length - 1) {
                                compilador.print(valores[j + 1] + "");
                            } else {
                                compilador.print(valores[j + 1] + ",");
                                // System.out.println(valores[j + 1]);
                            }
                        }
                    }
                }
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }//fin de finally
        compilador.close();
    }//fin método entradaValores
}
