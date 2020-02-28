package com.example.usuario.needinghelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    void abrirNavegacion(View v){
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }
}
