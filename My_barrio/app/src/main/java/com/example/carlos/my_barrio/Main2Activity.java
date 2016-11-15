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


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    ArrayList<picture> ima = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*list = (ListView)findViewById(R.id.lista);
        downloadImg();*/
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        muestra();

// Crear un nuevo adaptador
        adapter = new adapta(ima);
        recycler.setAdapter(adapter);

    }

    /*private void downloadImg(){
        title.clear();
        descrip.clear();
        ima.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://comidasutb.gzpot.com/esquina/api/fotos.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for(int i=0;i<jsonArray.length();i++){
                            title.add(jsonArray.getJSONObject(i).getString("nombre del usuario"));
                            descrip.add(jsonArray.getJSONObject(i).getString("descripcion"));
                            ima.add(jsonArray.getJSONObject(i).getString("imagen"));

                        }
                        list.setAdapter(new ImagenAdapter(getApplicationContext()));
                    }catch (JSONException h){
                        h.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    private class ImagenAdapter extends BaseAdapter{
        Context ctx;
        LayoutInflater layo;
        SmartImageView smt;
        TextView titulo,tvdes;
        public ImagenAdapter(Context applicationContext) {
            this.ctx=applicationContext;
            layo=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return ima.size();
        }

        @Override
        public Object getItem(int posi) {
            return posi;
        }

        @Override
        public long getItemId(int posi) {
            return posi;
        }

        @Override
        public View getView(int posi, View view, ViewGroup parent) {
            ViewGroup vwg=(ViewGroup)layo.inflate(R.layout.activity_main2_item, null);
            smt=(SmartImageView)vwg.findViewById(R.id.img1);
            titulo = (TextView)vwg.findViewById(R.id.titulo);
            tvdes = (TextView)vwg.findViewById(R.id.tvdes);
            String urlfin = "http://comidasutb.gzpot.com/esquina/api/fotos.php"+ima.get(posi).toString();
            Rect rct = new Rect(smt.getLeft(), smt.getTop(), smt.getRight(),smt.getBottom());
            smt.setImageUrl(urlfin,rct);
            titulo.setText(title.get(posi).toString());
            tvdes.setText(descrip.get(posi).toString());
            return vwg;
        }
    }*/
    public void muestra(){
        String []parametros = {"tipo_query","1"};
        try{
           String result = prmja_com.Post("http://comidasutb.gzpot.com/esquina/api/fotos.php",parametros);

            if (result.length()>2){
                Log.d("JOSN",result);
                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("fotos");

                for (int h=0; h<jsonArray.length(); h++){

                    ima.add(new picture("http://comidasutb.gzpot.com/esquina/fotos_publicaciones/"+jsonArray.getJSONObject(h).getString("imagen"),jsonArray.getJSONObject(h).getString("nombre"),jsonArray.getJSONObject(h).getString("descripcion")));
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
