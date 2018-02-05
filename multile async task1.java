package com.example.akhiljoymaliackal.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //private TextView text;
    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView4);
        text2 = (TextView)findViewById(R.id.textView);
        Button button=(Button)findViewById(R.id.button) ;

       // new JSONTask().execute("http://192.168.1.101:8000/checkstatus");
       // new JSONTask().execute("http://192.168.1.101:8000/checkstatus");
        new JSONTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.101:8000/checkstatus");
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View V) {
                //doIn.cancel(true);

                //new clickb().execute("http://192.168.1.101:8000/buttonpress"); //https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt
               // new clickb.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.101:8000/buttonpress");
                //FirstAsyncTask asyncTask = new FirstAsyncTask(); // First
                  new clickb().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.101:8000/buttonpress");


            }


        });
    }


public class  JSONTask extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... parms) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;

          int v;
        //final int random = new Random().nextInt(61);
        while(true) {
            final String s;
            v=new Random().nextInt(61);
            s=Integer.toString(v);
            try {
                URL url = new URL(parms[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                final StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       text1.setText(buffer.toString());
                    }

                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //random = new Random().nextInt(61);


                        text1.setText(s);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text1.setText(s);
                    }
                });
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //return null;
            //TimeUnit.MINUTES.sleep(1);
            try {
                Thread.sleep(5000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
       // text1.setText(result);
    }
}
    public class  clickb  extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... parms) {
            HttpURLConnection connection=null;
            BufferedReader reader=null;
            try{
                URL url=new URL(parms[0]);
                connection=(HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer=new StringBuffer();
                String line="";
                while ((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText("sucess");
                    }
                });
                return buffer.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText("failed");
                    }
                });
            }
            catch (IOException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text2.setText("failed");
                    }
                });
            }finally {
                if(connection!=null){
                    connection.disconnect();
                }
                try
                {   if(reader!=null) {
                    reader.close();
                }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //text2.setText(result);
        }
    }

}
