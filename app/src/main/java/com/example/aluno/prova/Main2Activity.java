package com.example.aluno.prova;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Button botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        botao = (Button)findViewById(R.id.button);

        botao.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                Intent intencao = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intencao);
            }

        });
    }
}
