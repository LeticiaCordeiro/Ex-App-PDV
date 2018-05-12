package com.example.aluno.prova;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button add;
    Button Venda;
    EditText IdVend;
    TextView NomeVend;
    EditText IdClie;
    TextView NomeClie;

    EditText CodPro;
    TextView Prod;

    RadioGroup rg;
    RadioButton vista;
    RadioButton cheque;
    RadioButton prazo;

    EditText total;
    EditText desconto;
    EditText TotalGeral;

    int totalint;
    int descontoint;
    int porc;
    int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pegar forma de pagamento
        rg = (RadioGroup)findViewById(R.id.rg);
        vista = (RadioButton)findViewById(R.id.vista);
        cheque = (RadioButton)findViewById(R.id.cheque);
        prazo = (RadioButton)findViewById(R.id.prazo);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int ForPag = radioGroup.getCheckedRadioButtonId();
            }
        });

        // pegar id vendedor
        final EditText IdVend = (EditText) findViewById(R.id.IdVend);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://pdv-api.herokuapp.com/rest/products/022300";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        IdVend.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                IdVend.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        IdVend.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {



                }
            }
        });

        //pegar id cliente
        EditText IdClie = (EditText) findViewById(R.id.IdClie);
        IdClie.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                }
            }
        });

        //pegar id produto
        EditText CodPro = (EditText) findViewById(R.id.CodPro);
        CodPro.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                }
            }
        });

        //calcular total geral
        EditText TotalGeral = (EditText) findViewById(R.id.TotalGeral);
        TotalGeral.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    total = (EditText)findViewById(R.id.total);
                    desconto = (EditText)findViewById(R.id.desconto);
                    totalint = Integer.parseInt(total.getText().toString());
                    descontoint = Integer.parseInt(desconto.getText().toString());
                    //verificar função para calculo de %
                    // porc = (descontoint * 0.01);
                    res = (totalint * porc);
                }
            }
        });



        //mudar para pagina de confirmação
//        Venda.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//
//                Intent intencao = new Intent(MainActivity.this,Main2Activity.class);
//                startActivity(intencao);
//            }
//
//        });

    }
}
