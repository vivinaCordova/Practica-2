package org.unl.music.base.controller.data_struct.list;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class LinkedListPractica {

    public static void runLinkedList(String filePath) {
        String[] originalData = loadData(filePath);

        if (originalData.length == 0) {
            System.out.println("No se cargaron datos. Revisa el archivo.");
            return;
        }

        System.out.println("Iniciando prueba con arreglo...");

        long startArray = System.nanoTime();
        int repetidosArray = contarRepetidosConArreglo(originalData);
        long endArray = System.nanoTime();

        System.out.println("Iniciando prueba con lista enlazada...");

        long startList = System.nanoTime();
        int repetidosLista = contarRepetidosConLista(originalData);
        long endList = System.nanoTime();

        System.out.println("\nResultados:");
        System.out.println("Cantidad de elementos repetidos (Arreglo): " + repetidosArray);
        System.out.println("Tiempo con arreglo: " + (endArray - startArray) + " ns");

        System.out.println("Cantidad de elementos repetidos (Lista enlazada): " + repetidosLista);
        System.out.println("Tiempo con lista enlazada: " + (endList - startList) + " ns");
    }

    public static int contarRepetidosConArreglo(String[] datos) {
        String[] repetidos = new String[datos.length];
        int repetidosCount = 0;

        for (int i = 0; i < datos.length; i++) {
            boolean yaAgregado = false;

            for (int k = 0; k < repetidosCount; k++) {
                if (repetidos[k].equals(datos[i])) {
                    yaAgregado = true;
                    break;
                }
            }

            if (yaAgregado) continue;

            int contador = 0;
            for (int j = 0; j < datos.length; j++) {
                if (datos[j].equals(datos[i])) {
                    contador++;
                }
            }

            if (contador > 1) {
                repetidos[repetidosCount] = datos[i];
                repetidosCount++;
            }
        }
        return repetidosCount;
    }

    public static int contarRepetidosConLista(String[] datos) {
        LinkedList<String> repetidos = new LinkedList<>();
        int repetidosCount = 0;

        for (int i = 0; i < datos.length; i++) {
            boolean yaAgregado = false;

            for (int k = 0; k < repetidos.getLength(); k++) {
                if (repetidos.get(k).equals(datos[i])) {
                    yaAgregado = true;
                    break;
                }
            }
            if (yaAgregado) continue;

            int contador = 0;
            for (int j = 0; j < datos.length; j++) {
                if (datos[j].equals(datos[i])) {
                    contador++;
                }
            }

            if (contador > 1) {
                repetidos.add(datos[i]);
                repetidosCount++;
            }
        }
        return repetidosCount;
    }

    public static String[] loadData(String filePath) {
        String[] temp = new String[100000];
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (count == temp.length) {
                    String[] nuevo = new String[temp.length * 2];
                    System.arraycopy(temp, 0, nuevo, 0, temp.length);
                    temp = nuevo;
                }
                temp[count++] = line.trim();
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        String[] resultado = new String[count];
        System.arraycopy(temp, 0, resultado, 0, count);
        return resultado;
    }
}
