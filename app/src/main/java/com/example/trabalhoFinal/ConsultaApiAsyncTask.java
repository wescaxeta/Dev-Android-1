package com.example.trabalhoFinal;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaApiAsyncTask extends AsyncTask<String, Void, String> {

    private ListenerConsultaFilme listener;

    public ConsultaApiAsyncTask(ListenerConsultaFilme listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        String API_URL = "https://www.omdbapi.com/?apikey=4a0461c2&i=tt1333125";

        try {

            InputStream is = null;

            try {

                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream stream = conn.getInputStream();

                java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
                String data = s.hasNext() ? s.next() : "";

                try {
                    return readInfo(data);
                } catch (JSONException e) {
                    Log.d("", "", e);
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
                return e.getMessage();
            } catch (Exception e) {
                Log.d("", "", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.d("", "", e);
                    }
                }
            }
            return "";

        } catch (Exception e) {

        }

        return "";
    }

    private String readInfo(String data) throws JSONException {
        JSONObject reader = new JSONObject(data);

        StringBuilder sb = new StringBuilder();

        sb.append("Título: " + reader.getString("Title") + "\n");
        sb.append("Ano: " + reader.getString("Year") + "\n" + "\n");
        sb.append("Poster: " + reader.getString("Poster"));

        return sb.toString();
    }

    protected void onPostExecute(String result) {
        listener.onConcludeConsultaFilme(result);
    }

    public interface ListenerConsultaFilme {
        void onConcludeConsultaFilme(String result);
    }
}