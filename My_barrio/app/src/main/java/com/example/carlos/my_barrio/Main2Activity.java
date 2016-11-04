package com.example.carlos.my_barrio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    FloatingActionButton flobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        flobtn = (FloatingActionButton)findViewById(R.id.tomarft);

        flobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flobtn = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(flobtn);
            }
        });
    }


}
