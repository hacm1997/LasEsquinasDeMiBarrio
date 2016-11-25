package com.example.carlos.my_barrio;

/**
 * Created by Carlos on 09/11/2016.
 */
/*Clase que se utiliza para enviar al servidor*/
public class picture {

	/*Hacemos constructor para imagen-despcripcion-nombre*/

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    String imagen;
     String nom;

    public picture(){}
    public picture(String imagen, String id, String desc, String nom) {
        this.imagen = imagen;
        this.id = id;
        this.desc = desc;
        this.nom = nom;
    }

    String desc;
    String id;


}
