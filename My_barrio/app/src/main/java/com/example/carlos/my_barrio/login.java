package com.example.carlos.my_barrio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prmja.http.prmja_com;

import java.util.concurrent.ExecutionException;

public class login extends AppCompatActivity {

    private Button button;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button= (Button) findViewById(R.id.button_login);
        editText= (EditText) findViewById(R.id.editText_login_nombre);
        final String uid= Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				/*indicamos los parametros que se añadiran al servidor*/
<<<<<<< HEAD
                SharedPreferences editor=getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1=editor.edit();
                editor1.putString("Nombre",editText.getText().toString());
                editor1.commit();
                finish();
=======
                String []parametro = {"tipo_query","2","nombre",editText.getText().toString(),"id",uid,"foto",""};

				/*hacemos Post indicando la url del servidor y añadimos los parametros*/
                try {
                    String t1 = prmja_com.Post("https://myservidor.000webhostapp.com/api/usuarios.php",parametro);
                    Toast.makeText(login.this, t1, Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
>>>>>>> origin/master

            }
        });
    }


}
