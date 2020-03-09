package com.example.usuario.needinghelp.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.usuario.needinghelp.Peticion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaseDeDatosMg extends AppCompatActivity {

    public BaseDeDatosMg(){

    }

    public int getTamano(){
        int tamano=0;
        try{
            Statement stm = conexionBD().createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT count(*) FROM Peticiones");
            tamano = resultSet.getInt(1);
            stm.close();
        }catch (Exception e){}
        return tamano;
    }

    public Connection conexionBD(){
        Connection connection =null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.10;port=1433;databaseName=NeedingHelp;user=cam;password=123");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return connection;
    }

    public ArrayList<Peticion> getCoordenadas() throws SQLException {
        ArrayList<Peticion> lista = new ArrayList<Peticion>();
        Connection conexion = this.conexionBD();
        try {
            PreparedStatement consulta1 = conexion.prepareStatement("SELECT Id,Nombre,Descripcion,Latitud,Longitud,Categoria FROM Peticiones");
            ResultSet result1 = consulta1.executeQuery();
            while(result1.next()){
                int id = result1.getInt("Id");
                String nombre = result1.getString("Nombre");
                String desc = result1.getString("Descripcion");
                float lat = result1.getFloat("Latitud");
                float lon = result1.getFloat("Longitud");
                String cat = result1.getString("Categoria");
                Peticion k = new Peticion(id, nombre, desc, lat, lon, cat);
                lista.add(k);
            }
        } catch (SQLException e) {}
        conexion.close();
        return lista;
        }
    }
