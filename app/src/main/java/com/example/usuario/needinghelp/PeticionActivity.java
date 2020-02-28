package com.example.usuario.needinghelp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PeticionActivity extends AppCompatActivity {

    Spinner listView;
    EditText desc, nomb, lat, lon;
    Button btnGPS, petBttn;
    MapsActivity mapsActivity;
    Peticion peticion;
    float latitud, longitud;
    String nombre, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion);
        mapsActivity = new MapsActivity();
        listView = (Spinner) findViewById(R.id.spinnerSp);
        petBttn = (Button) findViewById(R.id.petBtn);
        petBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitud = Float.parseFloat(lat.getText().toString());
                longitud = Float.parseFloat(lon.getText().toString());
                nombre = nomb.getText().toString();
                descripcion = desc.getText().toString();

            }
        });
        btnGPS = (Button) findViewById(R.id.ubAcbttn);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) PeticionActivity.this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener(){
                    public void onLocationChanged(Location location){
                        lat.setText(""+location.getLatitude());
                        lon.setText(""+location.getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider){}

                    @Override
                    public void onProviderDisabled(String provider) {}
                };
                int permissionCheck = ContextCompat.checkSelfPermission(PeticionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
            }
        });
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        desc = (EditText) findViewById(R.id.descTxt);
        nomb = (EditText) findViewById(R.id.nomTxt);
        lat = (EditText) findViewById(R.id.latTxt);
        lon = (EditText) findViewById(R.id.lonTxt);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
        listView.setAdapter(adapter);
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

    public Connection conexionBD(){
        Connection connection =null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://0.0.0.0;databaseName=NeedingHelp;user=ss;password=123");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return connection;
    }

    public void agregarPeticion(){
        try {
            PreparedStatement pet = conexionBD().prepareStatement("insert into Peticiones values(?,?,?,?,?,?)");
            pet.setString(2,nombre);
            pet.setString(3,descripcion);
            pet.setFloat(4,latitud);
            pet.setFloat(5,longitud);
            pet.setString(6, listView.toString());
            pet.executeUpdate();
            Toast.makeText(getApplicationContext(),"Petición enviada exitosamente",Toast.LENGTH_SHORT).show();

            //  pet.setString(7,);
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
