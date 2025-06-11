package org.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unl.music.base.controller.dao.dao_models.DaoAlbum;
import org.unl.music.base.controller.dao.dao_models.DaoCancion;
import org.unl.music.base.controller.dao.dao_models.DaoGenero;
import org.unl.music.base.controller.data_struct.list.LinkedList;
import org.unl.music.base.models.Album;
import org.unl.music.base.models.Cancion;
import org.unl.music.base.models.Genero;
import org.unl.music.base.models.TipoArchivoEnum;

import jakarta.validation.constraints.NotEmpty;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class CancionService {
    private DaoCancion db;
    // private String atributo;

    public CancionService() {
        db = new DaoCancion();
    }
    public List<HashMap> listAll() throws Exception{
        return Arrays.asList(db.all().toArray());
    }
    public List<Cancion> listAlla(){
        return (List<Cancion>)Arrays.asList(db.listAll().toArray());
    }
    public List<HashMap> order(String attribute, Integer type ) throws Exception {
        return Arrays.asList(db.orderByCancion(type, attribute).toArray());
    }
    public List<HashMap> search(String attribute, String text ,Integer type ) throws Exception {
        LinkedList<HashMap<String, Object>> lista = db.search(attribute, text, type);
        if(!lista.isEmpty())
           return Arrays.asList(lista.toArray());
        else 
            return new ArrayList<>();   
    
    }
    public void createCancion(@NotEmpty String nombre, Integer id_genero, Integer duracion, @NotEmpty String url,
            @NotEmpty String tipo, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && url.toString().length() > 0 && tipo.toString().length() > 0 && id_genero > 0
                && duracion > 0 && id_album > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            db.getObj().setId_genero(id_genero);
            db.getObj().setId_album(id_album);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la Cancion");
        }
    }

    public List<HashMap> listAlbumGombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if (!db.listAll().isEmpty()) {
            Album[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }

        }
        return lista;

    }

    public List<HashMap> listAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if (!db.listAll().isEmpty()) {
            Genero[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);

            }

        }
        return lista;

    }
    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivoEnum r : TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }

        return lista;
    }
}
