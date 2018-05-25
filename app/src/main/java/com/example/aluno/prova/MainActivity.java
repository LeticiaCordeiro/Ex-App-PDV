package com.example.aluno.prova;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    private RequestQueue mQueueCl;
    Button btnadd;
    Button btnVenda;

    EditText IdClie;
    TextView NomeCliente;

    EditText CodPro;
    TextView Prod;
    EditText QtdProd;
    private TextView NomeProd;
    private EditText precoprod;
    ListView listviewProd;
    String[] itens;
    ArrayAdapter<String> itensAdapter;
    private int ok =0 ;

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
        btnVenda = (Button)findViewById(R.id.btnvenda);
        btnadd = (Button)findViewById(R.id.btnadd);
        Prod = (TextView)findViewById(R.id.Prod);
        CodPro = (EditText) findViewById(R.id.CodPro);
        NomeProd = (TextView)findViewById (R.id.nomeproduto);
        precoprod = (EditText)findViewById (R.id.valorprod);
        QtdProd = (EditText)findViewById (R.id.qtdprod);
        IdClie = (EditText)findViewById (R.id.IdCliente);
        NomeCliente = (TextView)findViewById (R.id.NomeCliente);
        listviewProd = findViewById(R.id.listaprod);


        mQueue = Volley.newRequestQueue (this);
        mQueueCl = Volley.newRequestQueue (this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int ForPag = radioGroup.getCheckedRadioButtonId();
            }
        });

        //pegar id cliente
        final EditText IdClie = (EditText) findViewById(R.id.IdCliente);
        IdClie.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String Cliente = IdClie.getText().toString();
                    ProcurarCliente(Cliente);
                }
            }
        });

        //pegar id produto
        CodPro.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // code to execute when EditText loses focus
                    String IdProduto = CodPro.getText().toString();
                    baixarJson(IdProduto);
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
                    porc = (int) (descontoint * 0.01);
                    res = (totalint * porc);
                }
            }
        });
            //add no listview
            btnadd.setOnClickListener (new Button.OnClickListener () {
                @Override
                public void onClick(View view) {
                    String IdProduto = CodPro.getText().toString();
                    String Prod = NomeProd.getText().toString();
                    String Preco = precoprod.getText().toString();
                    String Qtd = QtdProd.getText().toString();

                    itens = new String[]{IdProduto, Prod, Preco, Qtd};

                    ArrayAdapter<String> itensAdapter = new ArrayAdapter<String> (MainActivity.this,android.R.layout.simple_list_item_1,itens);
                    listviewProd.setAdapter (itensAdapter);
                }
            });

    }

    public void RVenda(View view) {
        //mudar para pagina de confirmação
        btnVenda.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                Intent intencao = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intencao);
            }

        });
    }

    /*public void AddProd(View view) {

        btnadd.setOnClickListener (new Button.OnClickListener () {
            @Override
            public void onClick(View view) {

                String IdProduto = CodPro.getText().toString();
                String Prod = NomeProd.getText().toString();
                String Preco = precoprod.getText().toString();
                String Qtd = QtdProd.getText().toString();

                String[] itens ={IdProduto,Prod,Preco,Qtd};

                //ArrayAdapter<String> itensAdapter = new ArrayAdapter<String> (MainActivity.this,android.R.layout.simple_list_item_1,itens);
                listviewProd.setAdapter (itensAdapter);

            }
        });
    }*/

    //Buscando o Produto
    private  void baixarJson(String Codigo) {

        String url = "http://pdv-api.herokuapp.com/rest/products/"+Codigo;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jsonObj = response.getJSONObject ("product");

                            NomeProd.setText(jsonObj.getString ("description"));
                            precoprod.setText(jsonObj.getString ("price"));
                            QtdProd.setText ("1");


                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }

                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ();
            }
        });

        mQueue.add(request);
    }

    //Buscar o Cliente

    private  void ProcurarCliente(String Codigo) {

        String url = "http://pdv-api.herokuapp.com/rest/customers/"+Codigo;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObj = response.getJSONObject ("customer");
                            NomeCliente.setText(jsonObj.getString ("name"));

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }
                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace ();
            }
        });

        mQueueCl.add(request);
    }
}

