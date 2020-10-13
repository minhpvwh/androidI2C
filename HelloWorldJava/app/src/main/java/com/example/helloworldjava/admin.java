package com.example.helloworldjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class admin extends AppCompatActivity {

    Button _btnTurnOn, _btnTurnOff, _btnFlicker;
    DataOutputStream os = null;
    BufferedReader br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        try {
            Process mProcess = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(mProcess.getOutputStream());
//            os.writeBytes("echo 47 > /sys/class/gpio/export\n");
//            os.writeBytes("echo out > /sys/class/gpio/gpio47/direction\n");
            InputStream is = mProcess.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        _btnTurnOn = (Button) findViewById(R.id.btnTurnOn);
        _btnTurnOff = (Button) findViewById(R.id.btnTurnOff);
        _btnFlicker = (Button) findViewById(R.id.btnFlicker);

        _btnTurnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                echo 47 > /sys/class/gpio/export
//                echo out > /sys/class/gpio/gpio47/direction
//                echo 1 >  /sys/class/gpio/gpio47/value
//                echo 0 >  /sys/class/gpio/gpio47/value
//                echo in > /sys/class/gpio/gpio47/direction
//                cat  /sys/class/gpio/gpio47/value
                try {
                    os.writeBytes("echo 1 >  /sys/class/gpio/gpio47/value\n");
                    os.flush();
                    Toast.makeText(admin.this, "turn on", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        _btnTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    os.writeBytes("echo 0 >  /sys/class/gpio/gpio47/value\n");
                    os.flush();
                    Toast.makeText(admin.this, "turn on", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(admin.this, "turn off", Toast.LENGTH_LONG).show();
            }
        });

        _btnFlicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
//                    Runtime runtime = Runtime.getRuntime();
                    Process process = Runtime.getRuntime().exec(new String[] {"pwd"});
//                    Process process = Runtime.getRuntime().exec("i2cget -f -y 3 0x33 0x0d");
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    process.waitFor();
                    Toast.makeText(admin.this, "Value: "+br.readLine(), Toast.LENGTH_LONG).show();
//                    String line ;
//                    while (null != (line = br.readLine())) {
////                        return Integer.parseInt(line.trim());
//                        Toast.makeText(admin.this, "00000x"+ Integer.parseInt(line.trim()), Toast.LENGTH_LONG).show();
//                    }
//                    os.writeBytes("i2cget -f -y 3 0x22 0x0d\n");
//                    os.flush();
//                    Toast.makeText(admin.this, br.readLine(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                PeripheralManager manager = PeripheralManager.getInstance();
//                System.out.println("duc khi");
//                List<String> deviceList = manager.getI2cBusList();
//                if (deviceList.isEmpty()) {
//                    Log.i(TAG, "No I2C bus available on this device.");
//                } else {
//                    Log.i(TAG, "List of available devices: " + deviceList);
//                }
            }
        });
    }
}