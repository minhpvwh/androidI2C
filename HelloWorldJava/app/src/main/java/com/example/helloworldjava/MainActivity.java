package com.example.helloworldjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.things.pio.I2cDevice;

import com.google.android.things.pio.PeripheralManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    EditText _txtUser, _txtPass;
    Button _btnLogin;

    // I2C Device Name
    private static final String I2C_DEVICE_NAME = "kvim3";
    // I2C Worker Address
    private static final int I2C_ADDRESS = 0x0f;

    private static final String TAG = "MyActivity";

    private I2cDevice mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textResult = findViewById(R.id.textResult);
        try {
//            truy cap voi quyen sudo
            Process process = Runtime.getRuntime().exec("su");
//            hung dau ra
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
//            lay frame
            InputStream is = process.getInputStream();
//            doc frame
            InputStreamReader isr = new InputStreamReader(is);
//            luu tru frame doc duoc
            BufferedReader br = new BufferedReader(isr);

//            thoi gian bat dau
            long s = SystemClock.uptimeMillis();

//            luu tru string cmd
            StringBuffer cmd = new StringBuffer();

//            tao mot chuoi cmd
            String[] array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            String[] array1 = {"0", "1", "2", "3", "4", "5", "6", "7"};
            for (int i = 0; i < 767; i++) {
                cmd.append("i2cget -f -y 3 0x33 0x4d w &&\n");
            }
            cmd.append("i2cget -f -y 3 0x33 0x0d");
            Log.d("arrray", String.valueOf(cmd));
            os.writeBytes(cmd.toString());
            os.flush();
            os.close();

            StringBuffer sb = new StringBuffer();
//            sb.append(br.readLine()).append(";");
            for (int i = 0; i < 768; i++) {
                sb.append(br.readLine()).append(";");
            }
            // thoi gian ket thuc
            long d = SystemClock.uptimeMillis() - s;
            textResult.setText("Result: " + sb.toString() + " using " + d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}