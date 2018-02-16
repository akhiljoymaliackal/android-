package com.google.android.DemoKit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Akhil Joy Maliackal on 2/16/2018.
 */
public class trial extends DemoKitActivity{
    @Override


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texthome);

     Button   b1=(Button)findViewById(R.id.button);
        Button   b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] buffer = new byte[1];
                    buffer[0]=(byte)'a'; // button says on, light is off
                if (mOutputStream != null) {
                    try {
                        mOutputStream.write(buffer);
                    } catch (IOException e) {
                        Log.e(TAG, "write failed", e);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] buffer = new byte[1];
                buffer[0]=(byte)0; // button says on, light is off
                if (mOutputStream != null) {
                    try {
                        mOutputStream.write(buffer);
                    } catch (IOException e) {
                        Log.e(TAG, "write failed", e);
                    }
                }
            }
        });



    }
    }

