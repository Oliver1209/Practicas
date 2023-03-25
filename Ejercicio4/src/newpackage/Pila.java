
package newpackage;

public class Pila {
    private Nodo cima;
    private int tamano;

    public Pila() {
        this.cima = null;
        this.tamano = 0;
    }

    public boolean isEmpty() {
        return cima == null;
    }

    public int getTamano() {
        return tamano;
    }

    public void push(String dato) {
        Nodo nodo = new Nodo(dato);
        nodo.siguiente = cima;
        cima = nodo;
        tamano++;
    }

    public String pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }else{
        String dato = cima.dato;
        cima = cima.siguiente;
        tamano--;
        return dato;
        }
    }

    public String peek() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        return cima.dato;
    }

    public void vaciar() {
        while (!isEmpty()) {
            pop();
        }
    }

    public void imprimir() {
        if (isEmpty()) {
            System.out.println("La pila está vacía");
        } else {
            Nodo actual = cima;
            while (actual != null) {
                System.out.print(actual.dato + " ");
                actual = actual.siguiente;
            }
            System.out.println();
        }
    }
}