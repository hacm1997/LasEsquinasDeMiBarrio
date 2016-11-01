package com.example.carlos.my_barrio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileOutputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn;
    Button galery;
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
    img = (ImageView)findViewById(R.id.imagen);
    text1 = (EditText)findViewById(R.id.text1);
    text2 = (EditText)findViewById(R.id.text2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

       // getMenuInflater().inflate(R.menu.main, menu);
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
                /*abrirCam();*/
                break;
            case R.id.galery:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), SELECT_PICTURE);
                break;
        }
    }

    /*private void abrirCam(){



    }*/

    MenuItem.OnMenuItemClickListener SaveImageClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Bitmap bmp;
            OutputStream otp;
            bmp = BitmapFactory.decodeResource(getResources(),R.id.imagen);
            File flp = Environment.getExternalStorageDirectory();
            File dir = new File(flp.getAbsolutePath() + "/imagenes");
            dir.mkdirs();
            File fl = new File(dir, "prueba.jpg");
            try {
                otp = new FileOutputStream(fl);
                bmp.compress(Bitmap.CompressFormat.JPEG, 10 ,otp);
                otp.flush();
                otp.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            Toast.makeText(MainActivity.this, "se guardó en la SD", Toast.LENGTH_SHORT).show();

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
