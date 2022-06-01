package com.example.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;

public class MyActivity extends AppCompatActivity implements SensorEventListener {
    // センサを管理するマネージャ
    private SensorManager manager;
    // アプリ開始時の処理
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// マネージャを取得
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }
    public void onResume() {
        super.onResume();
// 明るさセンサ(TYPE_LIGHT)のリストを取得
        List<Sensor> sensors =
                manager.getSensorList(Sensor.TYPE_LIGHT);
// ひとつ以上見つかったら、最初のセンサを取得してリスナーに登録
        if (sensors.size() != 0) {
            Sensor sensor = sensors.get(0);
            manager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    protected void onPause() {
        super.onPause();
// 一時停止の際にリスナー登録を解除
        manager.unregisterListener(this);
    }
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }
    public void onSensorChanged(SensorEvent arg0) {
// 明るさセンサが変化したとき
        if (arg0.sensor.getType() == Sensor.TYPE_LIGHT) {
// 明るさの値（単位ルクス）を取得
            float intensity = arg0.values[0];
// 結果をテキストとして表示
            String str = Float.toString(intensity) + "ルクス";
            TextView textview = (TextView) findViewById(R.id.status_text);
            textview.setText(str);
        }
    }
}