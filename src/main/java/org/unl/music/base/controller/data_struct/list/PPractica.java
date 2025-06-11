package org.unl.music.base.controller.data_struct.list;

import java.io.BufferedReader;
import java.io.FileReader;

public class PPractica {
    private Integer[] matriz;
    private LinkedList<Integer> lista;

    public void cargar() {
        lista = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line = reader.readLine();
            while (line != null) {
                lista.add(Integer.parseInt(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
        }
    }

   /*private Boolean verificar_numero_arreglo(Integer a) {
        int cont = 0;
        Boolean band = false;
        for (int i = 0; i < matriz.length; i++) {
            if (a.intValue() == matriz[i].intValue())
                cont++;
            if (cont >= 2) {
                band = true;
                break;
            }
        }
        return band;
    }

    public String[] verificar_arreglo() {
        StringBuilder resp = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            if (verificar_numero_arreglo(matriz[i]))
                resp.append(matriz[i].toString()).append("-");
        }
        return resp.toString().split("-");
    }

    private Boolean verificar_numero_lista(Integer a) {
        int cont = 0;
        Boolean band = false;

        for (int i = 0; i < lista.getLength(); i++) {
            if (a.intValue() == lista.get(i).intValue())
                cont++;
            if (cont >= 2) {
                band = true;
                break;
            }
        }
        return band;
    }

    public LinkedList<Integer> verificar_lista() {
        LinkedList<Integer> resp = new LinkedList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            if (verificar_numero_lista(lista.get(i).intValue()))
                resp.add(lista.get(i));
        }
        return resp;
    }*/
    private int partition(Integer arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }
    private void quickSort(Integer arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }
    public void shell_sort(Integer arrayToSort[]) {
        int n = arrayToSort.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arrayToSort[i];
                int j = i;
                while (j >= gap && arrayToSort[j - gap] > key) {
                    arrayToSort[j] = arrayToSort[j - gap];
                    j -= gap;
                }
                arrayToSort[j] = key;
            }
        }
    }

    public void q_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            quickSort(arr, 0, arr.length - 1);
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado quicksort " + endTime + "y conto" + cont);
            lista.toList(arr);
        }
    }

    public void s_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            shell_sort(arr);
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado shell " + endTime + "y conto" + cont);
            lista.toList(arr);
        }
    }


    public static void main(String[] args) {
        PPractica p = new PPractica();
        p.cargar();
        
        System.out.println("Quicksort");
        p.q_order();
        System.out.println("Shellsort");
        p.s_order();
    }
}