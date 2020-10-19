package com.kk.app.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import io.flutter.embedding.android.FlutterActivity;

/**
 * @author kk
 */
public class MainFlutterAvy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_flutter_avy);
    }

    public void flutterClick(View view) {
        startActivity(FlutterActivity.createDefaultIntent(this));
//        Intent intent = FlutterActivity.withNewEngine().initialRoute("/route1")
//                .build(this);
//        startActivity(intent);
    }
}