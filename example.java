package com.example.beast.ddmpatient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by beast on 1/2/18.
 */

public class report_table extends AppCompatActivity {

    TextView val1,val2,val3,val4,val5,val6;
    TextView pval1,pval2,pval3,pval4,pval5,pval6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result);
        Intent n = getIntent();
        Bundle b = n.getBundleExtra("ptab");
        final String height = b.getString("height");
        final String weight = b.getString("weight");
        final String pulse = b.getString("pulse");
        final String temp = b.getString("temp");
        final String bp = b.getString("bp");
        final String glu = b.getString("glu");



       val1=(TextView)findViewById(R.id.gluccur);
        val1.setText(glu);
        val2=(TextView)findViewById(R.id.tempcur);
        val2.setText(temp);
        val3=(TextView)findViewById(R.id.pulscurr);
        val3.setText(pulse);
        val4=(TextView)findViewById(R.id.bpcur);
        val4.setText(bp);
        val5=(TextView)findViewById(R.id.wcur);
        val5.setText(weight);
        val6=(TextView)findViewById(R.id.hcr);
        val6.setText(height);
        new pvalues().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://192.168.1.101:8000/prevreport");


    }

    public class  pvalues extends AsyncTask<String,String,String> {

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
                return buffer.toString();
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
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
            String str1=result;
            List<String> spdata= Arrays.asList(str1.split(","));
            pval1=(TextView)findViewById(R.id.guprv);
            pval1.setText(spdata.get(0));
            pval2=(TextView)findViewById(R.id.tprv);
            pval2.setText(spdata.get(1));
            pval3=(TextView)findViewById(R.id.pprv);
            pval3.setText(spdata.get(2));
            pval4=(TextView)findViewById(R.id.bpprv);
            pval4.setText(spdata.get(3));
            pval5=(TextView)findViewById(R.id.wprv);
            pval5.setText(spdata.get(4));
            pval6=(TextView)findViewById(R.id.hprv);
            pval6.setText(spdata.get(5));

        }
    }
}


