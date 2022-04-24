package com.example.examen_3_parcial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.examen_3_parcial.Models.Medicamentos;
import com.example.examen_3_parcial.db.DbMedicamentos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;

public class VerActivity extends AppCompatActivity {
    EditText txtDescripcion, txtCantidad, txtPeriocidad, txtTiempo;
    ImageView imagen;
    Spinner cbTiempo;
    Button btnGuarda;
    ImageButton btnCamara;
    FloatingActionButton fabEditar, fabEliminar;

    Medicamentos medicamento;
    int id = 0;
    final DbMedicamentos dbMedicamentos = new DbMedicamentos(VerActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPeriocidad = findViewById(R.id.txtPeriocidad);
        cbTiempo = findViewById(R.id.CbTiempo);
        txtTiempo = findViewById(R.id.txtTiempo);
        imagen = findViewById(R.id.imageView);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);
        btnCamara = findViewById(R.id.btnCamara);
        btnCamara.setVisibility(View.INVISIBLE);
        cbTiempo.setVisibility(View.INVISIBLE);
        txtTiempo.setKeyListener(null);
        txtTiempo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        cbTiempo.setEnabled(false);
        txtCantidad.setEnabled(false);
        txtPeriocidad.setEnabled(false);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("Id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("Id");
        }


        medicamento = dbMedicamentos.verMedicamento(id);

        if (medicamento != null) {
            txtDescripcion.setText(medicamento.getDescripcion());
            txtCantidad.setText(medicamento.getCantidad());
            txtTiempo.setText(medicamento.getTiempo());
            txtPeriocidad.setText(medicamento.getPeriocidad());
            buttonFetch();
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este Registro?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(dbMedicamentos.eliminarMedicamento(id)){
                                    Toast.makeText(VerActivity.this, "Registro Eliminado Exitosamente!", Toast.LENGTH_SHORT).show();
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }
    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

  public void buttonFetch(){
      String stringQuery = "Select Imagen from t_medicamentos where id=" +  id;
      Cursor cursor = dbMedicamentos.getReadableDatabase().rawQuery(stringQuery, null);
      try {
          cursor.moveToFirst();
          byte[] bytesImage = cursor.getBlob(0);
          cursor.close();
          Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
          imagen.setImageBitmap(bitmapImage);
         // textViewStatus.setText("Fetch Successful");
      }
      catch (Exception e){
          //textViewStatus.setText("ERROR");
      }
  }
}
