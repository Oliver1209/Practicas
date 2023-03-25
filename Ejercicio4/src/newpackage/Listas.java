package newpackage;

import java.util.ArrayList;

public class Listas {
    public static ArrayList<String> Palabras = new ArrayList<String>();

    //Declaración
    private static Listas instancia;

    public Listas() {

    }

    //Para obtener la instancia unicamente por este metodo
    //Notese la palabra reservada "static" hace posible el acceso mediante Clase.metodo
    public static Listas getInstancia() {
        //Si no se a creado el objeto instancia
        if (instancia == null) {
            //Lo creamos
            instancia = new Listas();
        }
        //Retormamos el objeto instancia
        return instancia;
    }

    public ArrayList<String> getPalabras() {
        return Palabras;
    }

}
