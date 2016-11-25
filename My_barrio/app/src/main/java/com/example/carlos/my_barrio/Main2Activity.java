package com.example.carlos.my_barrio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.prmja.http.prmja_com;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {

	/*variables del RecyclerView*/
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

	/*un arraylist donde se mostrarán las img*/
    ArrayList<picture> ima = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
		/*llamos a nuestro RecyclerView*/
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

		/*Usar un administrador para LinearLayout*/
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        muestra();

		/*Crear un nuevo adaptador*/
        adapter = new adapta(ima);
        recycler.setAdapter(adapter);

    }

	/*Metodo para mostrar fotos desde el servidor*/
    public void muestra(){
        // Write a message to the database
        DatabaseReference ruta = FirebaseDatabase.getInstance().getReference().getRoot();

        ruta.child("fotos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ima.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    picture picture1=dataSnapshot1.getValue(picture.class);
                    picture1.setImagen("https://myservidor.000webhostapp.com/fotos_publicaciones/"+picture1.getImagen());
                    ima.add(picture1);
                }
                new adapta().cargar(ima);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*String []parametros = {"tipo_query","1"};
        try{
<<<<<<< HEAD
			hacemos Post indicando la url del servidor y añadimos los parametros
=======
			/*hacemos Post indicando la url del servidor y añadimos los parametros*/
>>>>>>> origin/master
           String result = prmja_com.Post("https://myservidor.000webhostapp.com/api/fotos.php",parametros);

            if (result.length()>2){
                Log.d("JOSN",result);
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("fotos");
				/*For para mostrar mas datos del servidor
                for (int h=0; h<jsonArray.length(); h++){

<<<<<<< HEAD
                    ima.add(new picture("https://myservidor.000webhostapp.com/fotos_publicaciones/"+jsonArray.getJSONObject(h).getString("imagen"),jsonArray.getJSONObject(h).getString("nombre"),jsonArray.getJSONObject(h).getString("descripcion")));
=======
                    ima.add(new picture("https://myservidor.000webhostapp.com/api/fotos_publicaciones"+JSONObject(h).getString("imagen"),jsonArray.getJSONObject(h).getString("nombre"),jsonArray.getJSONObject(h).getString("descripcion")));
>>>>>>> origin/master
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }



}
