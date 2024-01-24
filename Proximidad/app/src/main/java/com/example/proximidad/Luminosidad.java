package com.example.proximidad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import android.os.Bundle;

public class Luminosidad extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView luminosityValue;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luminosidad);

        luminosityValue = findViewById(R.id.luminosityValue);
        sendButton = findViewById(R.id.sendButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lightSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void sendMessage() {
        String luminosityMessage = "Luminosity Value: " + luminosityValue.getText().toString();

        // Ejemplo de un intent para abrir WhatsApp (el usuario tendrá que confirmar el envío manualmente)
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("jid", "73815926@s.whatsapp.net"); // Reemplaza con el número de teléfono de destino
        sendIntent.putExtra(Intent.EXTRA_TEXT, luminosityMessage);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float luminosity = event.values[0];
        luminosityValue.setText("Luminosity Value: " + luminosity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}