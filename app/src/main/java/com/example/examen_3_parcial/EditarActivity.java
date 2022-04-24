package com.example.examen_3_parcial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examen_3_parcial.Models.Medicamentos;
import com.example.examen_3_parcial.db.DbMedicamentos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class EditarActivity extends AppCompatActivity {
    EditText txtDescripcion,  txtCantidad, txtPeriocidad, txtTiempo;
    ImageView shotview;
    Spinner cbTiempo,s1;
    TextView Tv, viewTiempo;
    Button btnGuarda;
    ImageButton btnCamara;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Medicamentos medicamento;
    Bitmap photo;
    byte[] img, img2;
    int id = 0;

    String[] Items = {
            "Seleccione",
            "Horas",
            "Dia",
            "Semana",};
    private static final int REQUEST_PERMISSION_CAMERA=101;
    private static final int REQUEST_IMAGE_CAMERA=101;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPeriocidad = findViewById(R.id.txtPeriocidad);
        txtTiempo = findViewById(R.id.txtTiempo);
        cbTiempo = findViewById(R.id.CbTiempo);
        shotview = findViewById(R.id.imageView);
        fabEditar = findViewById(R.id.fabEditar);
        viewTiempo = findViewById(R.id.viewTiempo);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setText("Actualizar Datos");
        Tv = findViewById(R.id.Tv);
        txtTiempo.setKeyListener(null);
        btnCamara = findViewById(R.id.btnCamara);
        cbTiempo.setVisibility(View.INVISIBLE);
        //viewTiempo.setVisibility(View.INVISIBLE);
        s1 = (Spinner) findViewById(R.id.CbTiempo);
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
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Items);

        s1.setAdapter(adapter1);

        DbMedicamentos dbMedicamentos = new DbMedicamentos(EditarActivity.this);
        medicamento = dbMedicamentos.verMedicamento(id);

        if (medicamento != null) {
            txtDescripcion.setText(medicamento.getDescripcion());
            txtCantidad.setText(medicamento.getCantidad());
            txtPeriocidad.setText(medicamento.getPeriocidad());
            txtTiempo.setText(medicamento.getTiempo());
             //txtPuesto.setText(empleado.getPuesto());
           buttonFetch();
        }
        btnGuarda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    //txtTiempo.getText().equals(cbTiempo.getSelectedItem().toString());
                    txtTiempo.setText(cbTiempo.getSelectedItem().toString());
                    String resu = "";
                    //comprobamos que se ha seleccionado alguna de las operaciones a realizar

                        if (txtDescripcion.getText().toString().isEmpty()) {
                            resu = "Ingrese la Descripcion del Medicamento";
                            txtDescripcion.setFocusable(true);
                            Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                        } else {
                            if (txtCantidad.getText().toString().isEmpty()) {
                                resu = "Ingrese la Cantidad";
                                Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                                txtCantidad.setFocusable(true);
                            } else {
                                if (txtPeriocidad.getText().toString().isEmpty() && cbTiempo.getSelectedItemPosition() == 1) {
                                        resu = "Ingrese la Periocidad!";
                                        Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                                    txtPeriocidad.setFocusable(true);
                                    } else {

                                    if (cbTiempo.getSelectedItemPosition() != 1){
                                        txtPeriocidad.setText("");
                                    }

                                            DbMedicamentos dbMedicamentos = new DbMedicamentos(EditarActivity.this);
                                           correcto = dbMedicamentos.editarMedicamento_sinImagen(id, txtDescripcion.getText().toString(), txtCantidad.getText().toString(),txtTiempo.getText().toString()
                                                    ,txtPeriocidad.getText().toString());
                                            if(correcto){
                                                Toast.makeText(EditarActivity.this, "Registro Actualizado Exitosamente", Toast.LENGTH_LONG).show();
                                                verRegistro();lista();
                                            } else {
                                                Toast.makeText(EditarActivity.this, "Error al Actualizar Registro", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                }




               } catch (Exception e) {
                    //en caso de error se muestra la exception
                  System.out.println("Error!! Exception: " + e);
               }

            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(EditarActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                          abrirCamara();
                    } else{
                        ActivityCompat.requestPermissions(EditarActivity.this, new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                }else{
                        abrirCamara();
                }
            }
        });

        txtTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbTiempo.setVisibility(View.VISIBLE);
                txtTiempo.setVisibility(View.INVISIBLE);
            }
        });


        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {


                    case 0:
                        int indzex = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                        //txtPeriocidad.setText("");
                        break;
                    case 1:
                        int index = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(true);
                        txtPeriocidad.setFocusable(true);
                        break;
                    case 2:
                        int indzex1 = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                        //txtPeriocidad.setText("");
                        break;
                    case 3:
                        int indzex2 = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                        //txtPeriocidad.setText("");
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("Id", id);
        startActivity(intent);
    }
    private void limpiar() {
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPeriocidad.setText("");
        txtTiempo.setText("");
        cbTiempo.setSelection(0);
        Tv.setText("");
    }
    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void buttonFetch(){
        DbMedicamentos dbMedicamentos = new DbMedicamentos(EditarActivity.this);
        String stringQuery = "Select Imagen from t_medicamentos where id=" +  id;
        Cursor cursor = dbMedicamentos.getReadableDatabase().rawQuery(stringQuery, null);
        try {
            cursor.moveToFirst();
            byte[] bytesImage = cursor.getBlob(0);
            cursor.close();
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            shotview.setImageBitmap(bitmapImage);
            // textViewStatus.setText("Fetch Successful");
        }
        catch (Exception e){
            //textViewStatus.setText("ERROR");
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            shotview.setImageBitmap(imgBitmap);
            Tv.setText("2");
            //Toast.makeText(EditarActivity.this, "Es dos", Toast.LENGTH_LONG).show();
        }
    }
    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }
}
