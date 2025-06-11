package org.unl.music.base.controller.dao.dao_models;

import java.util.HashMap;

import org.unl.music.base.controller.utiles;
import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.controller.data_struct.list.LinkedList;
import org.unl.music.base.models.Cancion;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;

    public DaoCancion() {
        super(Cancion.class);
        // TODO Auto-generated constructor stub
    }

    public Cancion getObj() {
        if (obj == null)
            this.obj = new Cancion();
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean listar() {
        try {
            this.listAll();
            for (int i = 0; i > this.listAll().getLength(); i++) {
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public LinkedList<HashMap<String, Object>> all() throws Exception {
        LinkedList<HashMap<String, Object>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i], i));
            }
        }
        return lista;
    }

    private HashMap<String, Object> toDict(Cancion arreglo, Integer i) {
        DaoAlbum da = new DaoAlbum();
        DaoGenero db = new DaoGenero();
        HashMap<String, Object> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("duracion", arreglo.getDuracion());
        aux.put("nombre", arreglo.getNombre());
        aux.put("url", arreglo.getUrl());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("genero", db.listAll().get(arreglo.getId_genero() - 1).getNombre());
        aux.put("album", da.listAll().get(arreglo.getId_album() - 1).getNombre());
        return aux;
    }

    public LinkedList<HashMap<String, Object>> orderByCancion(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, Object>> lista = new LinkedList<>();
        if (!all().isEmpty()) {
            HashMap<String, Object> arr[] = all().toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }

    private int partition(HashMap<String, Object> arr[], int begin, int end, Integer type, String attribute) {
        HashMap<String, Object> pivot = arr[end];
        int i = (begin - 1);
        if (type == utiles.ASCENDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().compareTo(pivot.get(attribute).toString()) < 0) {
                    i++;
                    HashMap<String, Object> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().compareTo(pivot.get(attribute).toString()) < 0) {
                    i++;
                    HashMap<String, Object> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, Object> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;
        return i + 1;

    }

    public LinkedList<HashMap<String, Object>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, Object>> lista = all();
        LinkedList<HashMap<String, Object>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
            lista = orderByCancion(utiles.ASCENDENTE, attribute);
            HashMap<String, Object>[] arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute, text);
            switch (type) {
                case 1:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }

                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;

                case 2:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {

                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;

                default:
                    System.out.println(attribute + " " + text + " TRES " + n);
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;
            }
        }
        return resp;
    }

    private Integer bynaryLineal(HashMap<String, Object>[] array, String attribute, String text)
            throws Exception {
        Integer half = 0;
        if (!(array.length == 0) && !text.isEmpty()) {
            half = array.length / 2;
            int aux = 0;
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = 1;
            else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = -1;

            half = half * aux;

        }
        return half;
    }

    public void quickSort(HashMap<String, Object> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);
            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }

    public static void main(String[] args) {
        try {
            DaoCancion da = new DaoCancion();
            LinkedList<HashMap<String, Object>> resultados = da.search("nombre", "E", 1);
    
            // Imprimir resultados
            HashMap<String, Object>[] arr = resultados.toArray();
    
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}