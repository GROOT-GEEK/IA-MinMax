/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot4;

import java.util.ArrayList;

/**
 *
 * @author José María Serrano
 * @version 1.7 Departamento de Informática. Universidad de Jáen 
 * Última revisión: 2023-03-30
 * Inteligencia Artificial. 2º Curso. Grado en Ingeniería Informática
 * Clase MiniMaxPlayer para representar al jugador CPU que usa una técnica de IA
 * Esta clase es en la que tenemos que implementar y completar el algoritmo
 * MiniMax
 *
 */
class Pair<U, V>
{
    public  U first;       // el primer campo de un par
    public V second;      // el segundo campo de un par

    // Construye un nuevo par con valores especificados
    private Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }

    public void nuevo(U first, V second){
        this.first = first;
        this.second = second;
    }

    @Override
    // Verifica que el objeto especificado sea "igual a" el objeto actual o no
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) o;

        // llamar al método `equals()` de los objetos subyacentes
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second);
    }

    @Override
    // Calcula el código hash de un objeto para admitir tablas hash
    public int hashCode()
    {
        // usa códigos hash de los objetos subyacentes
        return 31 * first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    // Método de fábrica para crear una instancia inmutable de par con tipo
    public static <U, V> Pair <U, V> of(U a, V b)
    {
        // llama al constructor privado
        return new Pair<>(a, b);
    }
}



public class MiniMaxPlayer extends Player {


    private int nextJugador(int jugador) {
        if (jugador == 1) {
            return -1;
        } else {
            return 1;
        }
    }

    private boolean full(Grid tablero) {
        boolean retorno = true;
        for (int i = 0; i < tablero.getColumnas(); i++) {
            retorno &= tablero.fullColumn(i);
        }
        return retorno;
    }

    private void utilidad(Grid tablero, int jugador) {

        ArrayList<Pair<Grid, Integer>> tableroHijos = new ArrayList<>();

        for (int j = 0; j < tablero.getColumnas(); j++) {
            if (!tablero.fullColumn(j)) {
                Grid tableroCopia = new Grid(tablero);
                Pair<Grid, Integer> tableroMod = Pair.of(tableroCopia, 0);

                for (int i = tablero.getFilas() - 1; i > 0; i--) {
                    if (tableroMod.first.copyGrid()[i][j] == 0) {
                        if (jugador == 1) {
                            tableroMod.first.set(j, 1);
                        } else {
                            tableroMod.first.set(j, -1);
                        }
                        tableroHijos.add(tableroMod);
                        break;
                    }
                }
            }
        }

        for (Pair<Grid, Integer> tab : tableroHijos) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(tab.first.copyGrid()[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

        }
        for (Pair<Grid, Integer> tab : tableroHijos) {
            if (tablero.checkWin() != 0 || full(tablero))
                utilidad(tab.first, nextJugador(jugador));
        }
    }


    /**
     * @param tablero Tablero de juego
     * @param conecta Número de fichas consecutivas adyacentes necesarias para
     *                ganar
     * @return Devuelve si ha ganado algun jugador
     * @brief funcion que determina donde colocar la ficha este turno
     */
    @Override
    public int turno(Grid tablero, int conecta) {

        int posicion = getRandomColumn(tablero);

        utilidad(tablero, -1);

        return posicion;

    }
}