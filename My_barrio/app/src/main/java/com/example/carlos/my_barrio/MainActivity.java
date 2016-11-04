package com.example.carlos.my_barrio;

import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn;
    Button galery;
    Button publi;
    ImageView img;
    EditText text1;
    EditText text2;
    Intent c;
    Bitmap bmp;
    final static int constante = 0;
    private final int SELECT_PICTURE = 200;

    private String APP_DIRECTORY = "Foto";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(   ){

    btn = (Button)findViewById(R.id.btcap);
    btn.setOnClickListener(this);
    galery = (Button)findViewById(R.id.galery);
    galery.setOnClickListener(this);
    publi = (Button)findViewById(R.id.share);
    publi.setOnClickListener(this);

    img = (ImageView)findViewById(R.id.imagen);
    text1 = (EditText)findViewById(R.id.text1);
    text2 = (EditText)findViewById(R.id.text2);
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
                    bmp.compress(Bitmap.CompressFormat.JPEG, 10 ,otp);
                    otp.flush();
                    otp.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case R.id.galery:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                break;

            case R.id.share:
                Intent publica = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(publica);
                break;
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
                imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
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

            /*Bitmap bmp;
            OutputStream otp;
            bmp = BitmapFactory.decodeResource(getResources(),R.id.imagen);
            int numero = (int) (Math.random());
            File flp = Environment.getExternalStorageDirectory();
            File dir = new File(flp.getAbsolutePath() + "/imagenes");
            dir.mkdirs();
            File fl = new File(dir, "img" + numero + ".jpg");
            try {
                otp = new FileOutputStream(fl);
                bmp.compress(Bitmap.CompressFormat.JPEG, 10, otp);
                otp.flush();
                otp.close();
            }catch (Exception e){
                e.printStackTrace();
            }*/

            return false;
        }
    };

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
                    img.setImageURI(path);
                }break;

            }

        //guardar imagen
       /* guardar savefile = new guardar();

        savefile.SaveImage(context, bmp);*/
    }

}
