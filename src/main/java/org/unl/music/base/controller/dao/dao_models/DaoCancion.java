package org.unl.music.base.controller.dao.dao_models;

import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.models.Cancion;
import org.unl.music.base.models.TipoArchivoEnum;

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
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Enamorado tuyo");
        da.getObj().setId_genero(3);
        da.getObj().setUrl("https://music_enamoradotuyo");
        da.getObj().setId_album(1);
        da.getObj().setDuracion(2);
        da.getObj().setTipo(TipoArchivoEnum.valueOf("VIRTUAL"));
        if (da.save())
            System.out.println("GUARDADO");
        else
            System.out.println("Hubo un error");
    
        }
    }