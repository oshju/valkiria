package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

public class Ventana3 extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    Button botonleer;
    TextView txtdatos;
    private EditText txtnombre;
    private EditText txtapellido;
    private EditText txtemail;
    String hola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana3);
        this.botonleer=findViewById(R.id.botonleer);
        this.txtdatos=findViewById(R.id.etinombre);

        this.txtnombre=findViewById(R.id.txtnombre);
        this.txtapellido=findViewById(R.id.txtapellido);
        this.txtemail=findViewById(R.id.txtemail);
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void leerServicio(View view) {
        try {
            String url = "http://webapiamazon1-dev.eu-west-3.elasticbeanstalk.com/api/Empleados";
            new HttpAsyncTask().execute(url);
        } catch (Exception e) {
// manage exceptions
            txtdatos.setText(e.getMessage());
        }
    }

    public String recuperarContenido(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String resultado = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse respuesta = null;
        InputStream stream = null;
        try {
            respuesta = httpclient.execute(httpget);
            HttpEntity entity = respuesta.getEntity();

            if (entity != null) {
                stream = entity.getContent();
                resultado = convertirInputToString(stream);
            }
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                txtdatos.setText(e.getMessage());
            }
        }
        return resultado;
    }

    private String convertirInputToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String resultado = "";
        while ((line = bufferedReader.readLine()) != null)
            resultado += line;
        inputStream.close();
        return resultado;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return recuperarContenido(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Datos recibidos!", Toast.LENGTH_LONG).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Departamento> lista = convertirJsonDepartamentos(jsonarray);
                String datos = "";
                for (Departamento d : lista) {
                    datos += d.toString() + "\n";
                }
                txtdatos.setText(datos);
            } catch (JSONException e) {
                txtdatos.setText(e.getMessage());
            }

        }
    }

    public List<Departamento> convertirJsonDepartamentos(JSONArray jsonarray) throws JSONException {
        List<Departamento> lista = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            Departamento dept = new Departamento();
            String num, nom, loc;
            int id;
            num = jsonarray.getJSONObject(i).optString("numero").toString();
            nom = jsonarray.getJSONObject(i).optString("nombre").toString();
            loc = jsonarray.getJSONObject(i).optString("localidad").toString();
            id = jsonarray.getJSONObject(i).optInt("id");
            dept.setInombre(num);
            dept.setApellido(nom);
            dept.setEmail(loc);
            dept.setId(id);
            lista.add(dept);
        }
        return lista;
    }

    public void mostrarVentana(View v){
        Intent i=new Intent(this,Ventana5.class);
        startActivity(i);
    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String nombre = txtnombre.getText().toString().trim();
        String apellido  = txtapellido.getText().toString().trim();
        String email  = txtemail.getText().toString().trim();

        //Verificamos que las cajas de texto no estén vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(apellido)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }




        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(nombre,apellido)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(Ventana3.this,"Se ha registrado el usuario con el email: "+ txtnombre.getText(),Toast.LENGTH_LONG).show();
                        }else{

                            Toast.makeText(Ventana3.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }


}


