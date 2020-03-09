package com.example.usuario.needinghelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.needinghelp.Actividades.BaseDeDatosMg;

import java.sql.SQLException;
import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {

    private Button btn;
    private TextView textView;
    private ArrayList<Peticion> list;
    BaseDeDatosMg bd;
    String texto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        btn = (Button) findViewById(R.id.opBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list = bd.getCoordenadas();
                    for(int i = 0; i<list.size();i++){
                        texto += list.get(i).getLatitud()+", "+list.get(i).getLongitud()+"\n";
                    }
                } catch (SQLException e) {}
                textView.setText(texto);
            }
        });
        textView = (TextView) findViewById(R.id.mTxt);
        bd = new BaseDeDatosMg();
    }
}
