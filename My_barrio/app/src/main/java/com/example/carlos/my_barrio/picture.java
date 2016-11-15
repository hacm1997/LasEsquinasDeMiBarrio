package com.example.carlos.my_barrio;

/**
 * Created by Carlos on 09/11/2016.
 */
/*Clase que se utiliza para enviar al servidor*/
public class picture {

	/*Hacemos constructor para imagen-despcripcion-nombre*/

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private String imagen;
    private String nom;
    private String desc;

    public picture(String imagen, String nom, String desc) {
        this.imagen = imagen;
        this.nom = nom;
        this.desc = desc;
    }
	
	/*Retornamos a cada una*/

    public String getId() {
        return nom;
    }

    public String getDesc() {
        return desc;
    }

    public String getImagen() {
        return imagen;
    }

}
