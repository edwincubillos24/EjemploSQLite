package com.example.ejemplosqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    DataBaseManager Manager;
    ListView lista;
    EditText Ednombre;
    Button btnbuscar;
    SimpleCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager = new DataBaseManager(this);

        Manager.insertar("Edwin","5795520");
        Manager.insertar("Andres","3103907886");

        lista = (ListView) findViewById(android.R.id.list);
        Ednombre = (EditText) findViewById(R.id.EdText1);
        btnbuscar = (Button) findViewById(R.id.btn1);
        btnbuscar.setOnClickListener(this);

        String[] from = new String[]{Manager.CN_NAME, Manager.CN_PHONE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        cursor = Manager.cargarCursorContactos();
        adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor, from, to, 0);
        lista.setAdapter(adapter);
    }

    public void onClick(View v){
        if (v.getId()==R.id.btn1){
            new BuscarTask().execute();
        }
    }

    private class BuscarTask extends AsyncTask<Void, Void,Void>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            cursor = Manager.buscarContacto(Ednombre.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Finalizado", Toast.LENGTH_SHORT).show();
            adapter.changeCursor(cursor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
