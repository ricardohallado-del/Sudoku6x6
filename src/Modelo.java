public class Modelo {

    package com.example.sudoku6x6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

    public class Modelo {

        public enum Validacion {
            VALIDO,
            ERROR_FILA,
            ERROR_COLUMNA,
            ERROR_BLOQUE
        }

        public static final int TAMANO = 6;
        public static final int MAX_PISTAS = 6;

        private int[][] solucion = new int[TAMANO][TAMANO];
        private int pistasRestantes;

        public boolean generarNuevoTablero() {
            this.pistasRestantes = MAX_PISTAS;
            this.solucion = new int[TAMANO][TAMANO];
            return resolverTablero(this.solucion);
        }

        private boolean resolverTablero(int[][] valores) {
            for (int fila = 0; fila < TAMANO; fila++) {
                for (int col = 0; col < TAMANO; col++) {
                    if (valores[fila][col] == 0) {
                        List<Integer> numeros = IntStream.rangeClosed(1, TAMANO)
                                .boxed().collect(Collectors.toList());
                        Collections.shuffle(numeros);
                        for (int valor : numeros) {
                            if (esValidoEnSolucion(valores, fila, col, valor)) {
                                valores[fila][col] = valor;
                                if (resolverTablero(valores)) return true;
                                valores[fila][col] = 0;
                            }
                        }
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean esValidoEnSolucion(int[][] valores, int fila, int col, int valor) {
            for (int i = 0; i < TAMANO; i++) {
                if (valores[fila][i] == valor) return false;
                if (valores[i][col] == valor) return false;
            }
            int inicioFila = fila - fila % 2;
            int inicioCol = col - col % 3;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (valores[inicioFila + i][inicioCol + j] == valor) return false;
                }
            }
            return true;
        }

        public Modelo.Modelo.Validacion validarCelda(int[][] tablero, int indice, int valor) {
            int fila = indice / TAMANO;
            int col = indice % TAMANO;

            for (int i = 0; i < TAMANO; i++) {
                if (i != col && tablero[fila][i] == valor) return Modelo.Modelo.Validacion.ERROR_FILA;
            }

            for (int i = 0; i < TAMANO; i++) {
                if (i != fila && tablero[i][col] == valor) return Modelo.Modelo.Validacion.ERROR_COLUMNA;
            }

            List<Integer> bloque = identificarSubcuadricula(indice);
            for (int idx : bloque) {
                if (idx != indice) {
                    int filaIdx = idx / TAMANO;
                    int colIdx = idx % TAMANO;
                    if (tablero[filaIdx][colIdx] == valor) return Modelo.Modelo.Validacion.ERROR_BLOQUE;
                }
            }

            return Modelo.Modelo.Validacion.VALIDO;
        }

        public List<Integer> identificarSubcuadricula(int indice) {
            List<List<Integer>> bloques = List.of(
                    List.of(0, 1, 2, 6, 7, 8),
                    List.of(3, 4, 5, 9, 10, 11),
                    List.of(12, 13, 14, 18, 19, 20),
                    List.of(15, 16, 17, 21, 22, 23),
                    List.of(24, 25, 26, 30, 31, 32),
                    List.of(27, 28, 29, 33, 34, 35)
            );
            for (List<Integer> bloque : bloques) {
                if (bloque.contains(indice)) return bloque;
            }
            return new ArrayList<>();
        }

        public int usarPista(List<Integer> celdasVacias) {
            if (pistasRestantes <= 0 || celdasVacias.isEmpty()) return -1;
            List<Integer> copia = new ArrayList<>(celdasVacias);
            Collections.shuffle(copia);
            pistasRestantes--;
            return copia.get(0);
        }

        public int[][] getSolucion() { return solucion; }

        public int getPistasRestantes() { return pistasRestantes; }

        public int getValorSolucion(int indice) {
            return solucion[indice / TAMANO][indice % TAMANO];
        }
    }
}
