package com.example.usuario.needinghelp.Actividades;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.needinghelp.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

public class PeticionActivity extends AppCompatActivity {

    private EditText desc;
    private Button btnRobo, btnRecursos, btnCasa, btnTragedia, btnOtro, btnTrabajo, btnPeticion;
    private float latitud, longitud;
    private String nombre, descripcion, categoria, dir;
    private TextView dirTxt;
    private BaseDeDatosMg bd;
    private List<Address> direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        btnRobo = (Button)findViewById(R.id.roboBtn);
        btnRobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Robo");
            }
        });
        btnRecursos = (Button)findViewById(R.id.recursosBtn);
        btnRecursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Recursos");
            }
        });
        btnCasa = (Button)findViewById(R.id.casaBtn);
        btnCasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Casa");
            }
        });
        btnTragedia = (Button)findViewById(R.id.accidenteBtn);
        btnTragedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Tragedia");
            }
        });
        btnOtro = (Button)findViewById(R.id.otrosBtn);
        btnOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Otro");
            }
        });
        btnTrabajo = (Button)findViewById(R.id.trabajoBtn);
        btnTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarC("Trabajo");
            }
        });
        desc = (EditText) findViewById(R.id.descTxt);
        dirTxt = (TextView) findViewById(R.id.dirTxt);
        nombre = "camilo";
        btnPeticion = (Button) findViewById(R.id.petBtn);
        btnPeticion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPeticion();
                finish();
            }
        });
        bd = new BaseDeDatosMg();
    }

    private void asignarC(String cad){
        categoria = cad;
        Toast.makeText(getApplicationContext(),"Petición tipo" + cad + "elegida",Toast.LENGTH_SHORT).show();
        agregarCoordenadas();
    }

    public void recibirGps(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    public void agregarCoordenadas(){
        LocationManager locationManager = (LocationManager) PeticionActivity.this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener(){
            public void onLocationChanged(Location location){
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    direccion = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    dir = direccion.get(0).getAddressLine(0);
                }catch (IOException e) {}
                dirTxt.setText(dir.toString());
                latitud = (float)location.getLatitude();
                longitud = (float)location.getLongitude();
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider){}
            public void onProviderDisabled(String provider) {}
        };
        int permissionCheck = ContextCompat.checkSelfPermission(PeticionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
    }

    public void agregarPeticion(){
        descripcion = desc.getText().toString();
        try {
            PreparedStatement pet = bd.conexionBD().prepareStatement("insert into Peticiones values(?,?,?,?,?)");
            pet.setString(1,nombre);
            pet.setString(2,descripcion);
            pet.setFloat(3,latitud);
            pet.setFloat(4,longitud);
            pet.setString(5, categoria);
            pet.executeUpdate();

            Toast.makeText(getApplicationContext(),"Petición enviada exitosamente",Toast.LENGTH_SHORT).show();
            pet.close();
            //  pet.setString(7,);
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
