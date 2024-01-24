package com.example.lumino;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView luminosityValue;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        luminosityValue = findViewById(R.id.luminosityValue);
        sendButton = findViewById(R.id.sendButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL); // Registro del sensor aquí
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
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void sendMessage() {
        String luminosityMessage = "Luminosity Value: " + luminosityValue.getText().toString();

        if (!luminosityMessage.equals("Luminosity Value: ")) {

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra("jid", "123445@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, luminosityMessage);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } else {
            Toast.makeText(this, "Aún no se ha capturado ningún valor de luminosidad", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        luminosityValue.setText("Valor del sensor: " + sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}