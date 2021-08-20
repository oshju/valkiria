package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

public class Ventana5 extends AppCompatActivity {
    String apiKey = "3028328cf7734aeb7217b2843daa5f0";
    private TextView txtdatos;

    RequestQueue requestQueue;
    private EditText etinombre2;
    String hola;
    String hola1;
    boolean cart=false;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url2 = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
    String url = "https://pokeapi.co/api/v2/pokemon/ditto";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana5);
        this.txtdatos=findViewById(R.id.txtdatos);
        this.etinombre2=findViewById(R.id.etinombre2);
        requestQueue = Volley.newRequestQueue(this);  // This setups up a new request queue which we will need to make HTTP requests.
        sendAndRequestResponse();
    }

    private void clearRepoList() {
        // This will clear the repo list (set it as a blank string).
        this.txtdatos.setText("");
    }

    private void addToRepoList(String repoName, String lastUpdated, List<Card> cards) {
        // This will add a new repo to our list.
        // It combines the repoName and lastUpdated strings together.
        // And then adds them followed by a new line (\n\n make two new lines).
        String strRow = repoName + " / " + lastUpdated;
        String currentText = txtdatos.getText().toString();
        this.txtdatos.setText(currentText + "\n\n" + strRow);
        String s1=String.valueOf(cart);
        this.txtdatos.setText(s1);
    }

    private void setRepoListText(String str) {
        // This is used for setting the text of our repo list box to a specific string.
        // We will use this to write a "No repos found" message if the user doens't have any.
        this.txtdatos.setText(str);
    }
    private void getRepoList(String username) {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).


        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Check the length of our response (to see if the user has any repos)
                        if (response.length() > 0) {
                            // The user does have repos, so let's loop through them all.
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    // For each repo, add a new line to our repo list.

                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String repoName = jsonObj.get("name").toString();
                                    String lastUpdated = jsonObj.get("updated_at").toString();
                                    List<Card> cards = CardAPI.getAllCards();
                                    for (int j = 0; j < response.length(); j++) {
                                        cards.add((Card) jsonObj.get("name"));

                                    }
                                    boolean carta1= cards.add((Card) jsonObj.get("name"));
                                    addToRepoList(repoName, lastUpdated,cards);





                                } catch (JSONException e) {
                                    // If there is an error then output this to the logs.
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            // The user didn't have any repos.
                            setRepoListText("No repos found.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list.
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);


    }
    public void getReposClicked(View v) {
        // Clear the repo list (so we have a fresh screen to add to)
        clearRepoList();
        // Call our getRepoList() function that is defined above and pass in the
        // text which has been entered into the etGitHubUser text input field.
        getRepoList(etinombre2.getText().toString());
    }
    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                txtdatos.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public void mostrarFormulario1(View view) {
        Intent i = new Intent(this,Pruebaapikey.class );
        startActivity(i);
    }

    public void mostrarFormulario(View view) {
        Intent i = new Intent(this, ventana6.class );
        startActivity(i);
    }
}






