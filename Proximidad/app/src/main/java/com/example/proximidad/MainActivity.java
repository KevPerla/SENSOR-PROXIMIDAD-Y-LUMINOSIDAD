package com.example.proximidad;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Sensor miSensor;
    SensorManager administradorDeSensores;
    SensorEventListener disparadorEventoSensor;
    TextView lblValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblValor = (TextView) findViewById(R.id.lblValorProximidad);

        administradorDeSensores = (SensorManager)
                getSystemService(SENSOR_SERVICE);
        miSensor =
                administradorDeSensores.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(miSensor==null){
            Toast.makeText(this, "Su dispositivo no tiene el sensor de proximidad", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(this, "Sensor de Proximidad detectado",
                    Toast.LENGTH_LONG).show();
        }
        disparadorEventoSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                lblValor.setText("Valor del sensor: "+
                        sensorEvent.values[0]);
                if(sensorEvent.values[0] < miSensor.getMaximumRange()){

                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);
                    lblValor.setBackgroundColor(Color.RED);
                    lblValor.setTextColor(Color.WHITE);
                }else{
                   
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    lblValor.setBackgroundColor(Color.GREEN);
                    lblValor.setTextColor(Color.BLUE);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        iniciarSensor();
    }
    public void iniciarSensor(){
        administradorDeSensores.registerListener(disparadorEventoSensor,miSensor,(2000*1000));;
    }
    public void detenerSensor(){
        administradorDeSensores.unregisterListener(disparadorEventoSensor);
    }
    @Override
    protected void onPause() {
        super.onPause();
        detenerSensor();
    }
    @Override
    protected void onResume() {
        iniciarSensor();
        super.onResume();
    }
}
