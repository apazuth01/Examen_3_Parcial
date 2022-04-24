package com.example.examen_3_parcial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.examen_3_parcial.Models.Medicamentos;
import com.example.examen_3_parcial.adapatadores.ListaDatosAdapter;
import com.example.examen_3_parcial.db.DbMedicamentos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView txtBuscar;
    RecyclerView listaMedicamentos;
    ArrayList<Medicamentos> listaArrayMedicamentos;
    Button fabNuevo;
    ListaDatosAdapter adapter;
    Medicamentos medicamento;
    int id = 0;
    final DbMedicamentos dbMedicamentos = new DbMedicamentos(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);
         listaMedicamentos = findViewById(R.id.listaMedicamentos);
        fabNuevo = findViewById(R.id.bnuevo);
       listaMedicamentos.setLayoutManager(new LinearLayoutManager(this));

        DbMedicamentos dbMedicamentos = new DbMedicamentos(MainActivity.this);

        listaArrayMedicamentos = new ArrayList<>();
        adapter = new ListaDatosAdapter(dbMedicamentos.mostrarMedicamentos());
        listaMedicamentos.setAdapter(adapter);

      fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });
        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }



}