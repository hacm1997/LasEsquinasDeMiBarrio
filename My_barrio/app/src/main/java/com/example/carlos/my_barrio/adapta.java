package com.example.carlos.my_barrio;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prmja.http.prmja_com;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Carlos on 09/11/2016.
 */

public class adapta extends RecyclerView.Adapter<adapta.AnimeViewHolder> {
    private List<picture> ima;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        /*Campos respectivos de un item*/
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;

        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.ide);
            visitas = (TextView) v.findViewById(R.id.descrip);
        }
    }

    public adapta(){}
    public adapta(List<picture> items) {
        this.ima = items;
    }

    @Override
    public int getItemCount() {
        return ima.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        try {
            Log.i("nombre",ima.get(i).getImagen());
            viewHolder.imagen.setImageBitmap(prmja_com.Download_Image(ima.get(i).getImagen()));
            viewHolder.nombre.setText(ima.get(i).getNom());
            viewHolder.visitas.setText(ima.get(i).getDesc());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        /*guardamos las variables que nos devuelve el findview de nuestro layout*/

    }

}
    public void cargar(List<picture> list){
        if(ima!=null){
            ima.clear();
            ima.addAll(list);

        }else {
            ima=list;
        }
        notifyDataSetChanged();
    }
}
