package com.example.carlos.my_barrio;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.prmja.http.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn;
    Button galery;
    Button publi;
    Button vi;
    ImageView img;
    EditText iden;
    EditText descrip;
    Intent c;
    Bitmap bmp;
    /*
Declarar instancias globales
*/


    final static int constante = 0;
    private final int SELECT_PICTURE = 200;

    private String APP_DIRECTORY = "Foto";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
        init();
    }

    public void init(   ){

    btn = (Button)findViewById(R.id.btcap);
    btn.setOnClickListener(this);
    galery = (Button)findViewById(R.id.galery);
    galery.setOnClickListener(this);
    publi = (Button)findViewById(R.id.share);
    publi.setOnClickListener(this);
    vi = (Button)findViewById(R.id.ver);
    vi.setOnClickListener(this);
    iden = (EditText)findViewById(R.id.text2);
    descrip = (EditText)findViewById(R.id.text4);
    img = (ImageView)findViewById(R.id.imagen);
        // Obtener el Recycler



    }
String uid;
    String nombre;
    public void login(){
         uid= Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       String []parametros = {"tipo_query","2","id",uid};
        try{
           String t = prmja_com.Post("http://comidasutb.gzpot.com/esquina/api/usuarios.php",parametros);
            Log.d("login",t);
          if(t.length()<2){
            Intent intent =new Intent(MainActivity.this,login.class);
              startActivity(intent);
          }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        menu.add("Guardar").setOnMenuItemClickListener(this.SaveImageClickListener).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public void onClick(View view) {
    int id;
        id = view.getId();
        switch (id)
        {
            case R.id.btcap:
                Bitmap bmp;
                OutputStream otp;
                c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(c, constante);
                File fl = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
                fl.mkdirs();
                bmp = BitmapFactory.decodeResource(getResources(),R.id.imagen);
                try {
                    otp = new FileOutputStream(fl);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100 ,otp);
                    otp.flush();
                    otp.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                for (int h = 0; h < 100; h++) {
                    int numero = (int) (Math.random() * 100);
                    iden.setText(String.valueOf(numero));
                    iden.setEnabled(false);
                }

                break;
            case R.id.galery:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                for (int h = 0; h < 100; h++) {
                    int numero = (int) (Math.random() * 100);
                    iden.setText(String.valueOf(numero));
                    iden.setEnabled(false);

                }
                break;

            case R.id.share:
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Enviando...");
                progressDialog.show();
                progressDialog.dismiss();
                share();
                img.clearAnimation();
                iden.clearAnimation();
                descrip.clearAnimation();
                break;

            case R.id.ver:
                Intent publica = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(publica);

        }
    }

    private String guardarImagen (Context context, Bitmap imagen) {
        ContextWrapper cw = new ContextWrapper(context);
        for (int h = 0; h < 100; h++) {
            int numero = (int) (Math.random() * 100);
            File flp = Environment.getExternalStorageDirectory();
            File dir = new File(flp.getAbsolutePath() + "/imagenes");
            File myPath = new File(dir, "imagen" + numero + ".jpg");

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(myPath);
                imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return myPath.getAbsolutePath();

        }
        return null;
    }

    MenuItem.OnMenuItemClickListener SaveImageClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            Bitmap imagen = ((BitmapDrawable)img.getDrawable()).getBitmap();

            String ruta = guardarImagen(getApplicationContext(), imagen);

            Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_LONG).show();

            Toast.makeText(MainActivity.this, "se guardó en la SD", Toast.LENGTH_SHORT).show();


            return false;
        }
    };

    public static String encodeToBase64(Bitmap imag, Bitmap.CompressFormat compressFormat, int quality){
        if (imag == null){
            return " ";
        }else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imag.compress(compressFormat, quality, baos);
            return Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        }
    }
    String result = "";
    public void share(){
        String uid= Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String img64 = encodeToBase64(bmp, Bitmap.CompressFormat.JPEG, 100);
        String des = descrip.getText().toString();
        String id = iden.getText().toString();
        String []parametros = {"tipo_query","2","id_u",uid,"descri",des,"imagen",img64.toString()};
        try{
            result = prmja_com.Post("http://comidasutb.gzpot.com/esquina/api/fotos.php",parametros);

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            iden.setText("");
            descrip.setText("");
            img.setImageBitmap(null);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

   /* private boolean onInsert(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://comidasutb.gzpot.com/esquina/api/fotos.php");
        nameValuePairs = new ArrayList<NameValuePair>(1);
        boolean imagen = nameValuePairs.add(new BasicNameValuePair("imagen", iden.getText().toString().trim() + ".jpg" + "Descripción" + descrip));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,requestCode,data);

            switch (requestCode){
                case constante:
            if (resultCode == RESULT_OK)
            {
                Bundle ext = data.  getExtras();
                bmp = (Bitmap) ext.get("data");
                img.setImageBitmap(bmp);
            }break;

                case SELECT_PICTURE:
                if (resultCode == RESULT_OK){
                    Uri path = data.getData();
                    try {
                        bmp=MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                    } catch (IOException e) {

                    }
                    img.setImageURI(path);
                }break;

            }

        //guardar imagen
       /* guardar savefile = new guardar();

        savefile.SaveImage(context, bmp);*/
    }

}
