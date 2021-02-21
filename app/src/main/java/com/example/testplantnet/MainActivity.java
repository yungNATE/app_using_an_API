package com.example.testplantnet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultiparfdtEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //Interface
    Button btn_API;
    TextView tv_nomScientifique, tv_nomsCommuns;

    //Caractéristiques de la plante courante
    String nomScientifique, famille;
    String[] nomsCommuns; // Une plante peut possèder plusieurs noms communs

    // API Related
     short int etatRecherche;  // DEBUT REQUETES
                        // 0: L'API PlantNet n'a pas encore été appellée / ne donne pas de réponse
                        // 1: L'API PlantNet a envoyé un résultat, L'API Trefle peut travailler
                        // 2: Trefle renvoye un résultat, la recherche est finie
                        // FIN REQUETES
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigner les valeur à chaque widget de l'interface
        btn_API = findViewById(R.id.button_API);
        tv_nomScientifique = findViewById(R.id.textView_API);
        tv_nomsCommuns = findViewById(R.id.textView_API2);


        // Click listener pour le bouton -> lance l'appel à l'API, puis traite les données reçues
        btn_API.setOnClickListener(new View.OnClickListener(){
            private static final String IMAGE1 = "/data/media/image_1.jpeg";
            private static final String IMAGE2 = "/data/media/image_2.jpeg";
            private static final String URL = "https://my-api.plantnet.org/v2/identify/all?api-key=2a10WnDCSTLWu10BfRb5PKHssO";

            File file1 = new File(IMAGE1);
            File file2 = new File(IMAGE2);

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("images", new FileBody(file1)).addTextBody("organs", "flower")
                    .addPart("images", new FileBody(file2)).addTextBody("organs", "leaf")
                    .build();

            HttpPost request = new HttpPost(URL);
            request.setEntity(entity);

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response;
            try {
                response = client.execute(request);
                String jsonString = EntityUtils.toString(response.getEntity());
                System.out.println(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* private void onResponse(JSONObject response) {
                try {
                    JSONObject resultatsDeLaRecherche = (JSONObject) response.getJSONArray("results").get(0); // L'api renvoie tous les résultats probables de la reconnaissance de plan
                    JSONObject resultatLePlusProbable = resultatsDeLaRecherche.getJSONObject("species"); // nom scientifique, nom commun, famille
                    nomScientifique = resultatLePlusProbable.getString("scientificNameWithoutAuthor");


                    tv_nomScientifique.setText(nomScientifique);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }*/
            /*
                // Instantiate the RequestQueue.

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                String url ="https://my-api.plantnet.org/v2/identify/all?images=" +
                            "https://media.gerbeaud.net/2014/02/640/chene-quercus-robur.jpg" +
                            "&organs=leaf&include-related-images=false&lang=en" +
                            "&api-key=2a10dhqKV1csqtYS4gUnTxZ";

                @SuppressLint("SetTextI18n") JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                                                                url,
                                                    null,
                                                                this::onResponse,
                                                                error -> {  tv_nomScientifique.setText("Zut... Marche pas..."); // CHANGER ETAT
                                                                            tv_nomsCommuns.setText("Zut... Marche pas non plus..."); }
                                                                );

                // Add the request to the RequestQueue.
                queue.add(request);
                */
                // ?
                // url = "https://trefle.io/api/v1/plants/search?token=cI3tP70z6kyRlggY_QPA3bFkDfzjz_K3ME8ggdHhm-4&q=Quercus%20robur";
        }

    });
}
/*
public class MultipartRequest extends Request { 
    private MultipartEntity entity = new MultipartEntity(); 
    private static final Ssortingng FILE_PART_NAME = "file"; 
    private static final Ssortingng STRING_PART_NAME = "text"; 
    private final Response.Listener mListener; 
    private final File mFilePart; 
    private final Ssortingng mSsortingngPart; 
    public MultipartRequest(Ssortingng url, Response.ErrorListener errorListener, 
                            Response.Listener listener, 
                            File file, 
                            Ssortingng ssortingngPart) { 
                                super(Method.POST, url, errorListener); 
                                mListener = listener; mFilePart = file; 
                                mSsortingngPart = ssortingngPart; buildMultipartEntity();
                            } 
                            private void buildMultipartEntity() { 
                                entity.addPart(FILE_PART_NAME, new FileBody(mFilePart)); 
                                try { entity.addPart(STRING_PART_NAME, new SsortingngBody(mSsortingngPart)); } 
                                catch (UnsupportedEncodingException e) { 
                                    VolleyLog.e("UnsupportedEncodingException"); } } 
                                    @Override 
                                    public Ssortingng getBodyContentType() { return entity.getContentType().getValue(); } 
                                    @Override 
                                    public byte[] getBody() throws AuthFailureError { ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
                                    try { entity.writeTo(bos); } 
                                    catch (IOException e) { VolleyLog.e("IOException writing to ByteArrayOutputStream"); } 
                                    return bos.toByteArray(); } 
                                    @Override 
                                    protected Response parseNetworkResponse(NetworkResponse response) { 
                                        return Response.success("Uploaded", getCacheEntry()); }
                                    @Override 
                                    protected void deliverResponse(Ssortingng response) { 
                                        mListener.onResponse(response); } 
}*/