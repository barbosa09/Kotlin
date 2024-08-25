package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView garrafa;
    private Random random = new Random();
    private int ultimaDirecao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

garrafa=findViewById(R.id.garrafa)

        garrafa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int novaDirecao = random.nextInt( 1800);
                float eixoX = garrafa.getWidth() /2;
                float eixoY = garrafa.getHeight() /2;

                Animation girar = new RotateAnimation(ultimaDirecao,novaDirecao)
                girar.setDuration(3000);
                girar.setFillAfter(true);

                ultimaDirecao = novaDirecao;
                garrafa.startAnimation(girar);

            }
        });


    }
}