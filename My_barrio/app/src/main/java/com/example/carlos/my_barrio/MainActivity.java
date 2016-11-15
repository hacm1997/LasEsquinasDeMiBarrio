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
		/*Se llama al método login*/
        login();
        init();
    }

    public void init(   ){
	/*llamada de los id de la activity*/
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
		/*configuramos y asignamos los parametros para nuestra Id*/
         uid= Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
       String []parametros = {"tipo_query","2","id",uid};
        try{
			/*hacemos Post indicando la url del servidor y agregamos los parametros*/
           String t = prmja_com.Post("http://comidasutb.gzpot.com/esquina/api/usuarios.php",parametros);
            Log.d("login",t);
          if(t.length()<2){
			  /*iniciamos la clase login para agregar una Id*/
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
		/*Se añade un boton guardar en el menú*/
        menu.add("Guardar").setOnMenuItemClickListener(this.SaveImageClickListener).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public void onClick(View view) {
		/*Un id para implementar con un swith de 4 casos*/
    int id;
        id = view.getId();
        switch (id)
        {
			/*el Primer caso para capturar una imagen*/
            case R.id.btcap:
                Bitmap bmp;
                OutputStream otp;
                c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(c, constante);
				/*Con el intent (c), se inicia la actividad q es tomar la foto*/
                File fl = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
                fl.mkdirs();
                bmp = BitmapFactory.decodeResource(getResources(),R.id.imagen);
                try {
					/*Añadimos la img al directorio*/
                    otp = new FileOutputStream(fl);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100 ,otp);
					/*Le damos un formato y tamaño a la img - luegos cerramos*/
                    otp.flush();
                    otp.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
				/*Le damos un sufijo a la img a traves de un numero random*/
                for (int h = 0; h < 100; h++) {
                    int numero = (int) (Math.random() * 100);
                    iden.setText(String.valueOf(numero));
                    iden.setEnabled(false);
                }

                break;
				/*el segundo caso para seleccionar una img de nuestra galeria*/
            case R.id.galery:
                /*un intent para abrir la galeria*/
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
				/*seleccionar la img*/
                startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                for (int h = 0; h < 100; h++) {
                    int numero = (int) (Math.random() * 100);
                    iden.setText(String.valueOf(numero));
                    iden.setEnabled(false);

                }
                break;

				/*tercer caso para subir la foto al servidor*/
            case R.id.share:
				/*Un mensaje de progreso para informarnos si está hecho*/
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Enviando...");
                progressDialog.show();
                progressDialog.dismiss();
				/*cerramos el mensaje*/
				/*llamamos al metodo share*/
                share();
				/*limpiamos todos los item para que no haya duplicados*/
                img.clearAnimation();
                iden.clearAnimation();
                descrip.clearAnimation();
                break;

				/*el cuarto caso para ver las últimas fotos en el servidor*/
            case R.id.ver:
				/*llamamos a la clase Main2Activity para iniciarla*/
                Intent publica = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(publica);

        }
    }

    private String guardarImagen (Context context, Bitmap imagen) {
		/*instanciamos un ContextWrapper*/
        ContextWrapper cw = new ContextWrapper(context);
        for (int h = 0; h < 100; h++) {
            int numero = (int) (Math.random() * 100);
			/*leemos el directorio (SD), se crea una carpeta (imagenes) y la guardamos en el directorio (dir), 
			con un nombre (imagen) y un numero creado al azar (random) y su respectivo formato jpg*/
            File flp = Environment.getExternalStorageDirectory();
            File dir = new File(flp.getAbsolutePath() + "/imagenes");
            File myPath = new File(dir, "imagen" + numero + ".jpg");

            FileOutputStream fos = null;
            try {
				/*le damos formato a la img para guardad bien*/
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

	/*accionamos el boton GUARDAR del menú*/
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
	/*método para comprimir nuestra imagen a base64 para poder enviarla al servidor*/
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
	
	/*Métodos para enviar al servidor*/
    public void share(){
        String uid= Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
		/*damos formato*/
        String img64 = encodeToBase64(bmp, Bitmap.CompressFormat.JPEG, 100);
        String des = descrip.getText().toString();
        String id = iden.getText().toString();
		/*asignamos los parametros para poder enviar correctamente al servidor*/
        String []parametros = {"tipo_query","2","id_u",uid,"descri",des,"imagen",img64.toString()};
        try{
			/*con la variable result hacemos Post indicando la url del servidor y añadimos los parametros*/
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

	/*Método para resultados de las actividades realizadas*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,requestCode,data);

            switch (requestCode){
                case constante:
				/*si todo sale bien insertamos la foto tomada en nuestro ImageView*/
            if (resultCode == RESULT_OK)
            {
                Bundle ext = data.  getExtras();
                bmp = (Bitmap) ext.get("data");
                img.setImageBitmap(bmp);
            }break;
				/*si todo sale bien insertamos la foto seleccionada de la galeria en nuestro ImageView*/
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

    }

}
