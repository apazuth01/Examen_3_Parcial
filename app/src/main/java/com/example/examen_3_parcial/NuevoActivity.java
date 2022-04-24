package com.example.examen_3_parcial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examen_3_parcial.db.DbMedicamentos;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NuevoActivity extends AppCompatActivity {
    EditText txtDescripcion, txtCantidad, txtPeriocidad, txtTiempo;
    ImageView shotview;
    Spinner cbTiempo, s1;
    Button btnGuarda;
    ImageButton btnCamara;
    TextView Tv;
    Bitmap photo;
    byte[] img;
    private static final int REQUEST_PERMISSION_CAMERA = 101;
    private static final int REQUEST_IMAGE_CAMERA = 101;
    String[] Items = {
            "Seleccione",
            "Horas",
            "Dia",
            "Semana",};

    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPeriocidad = findViewById(R.id.txtPeriocidad);
        cbTiempo = findViewById(R.id.CbTiempo);
        shotview = findViewById(R.id.imageView);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnCamara = findViewById(R.id.btnCamara);
        txtTiempo = findViewById(R.id.txtTiempo);
        txtTiempo.setVisibility(View.INVISIBLE);
        Tv = findViewById(R.id.Tfoto);
        s1 = (Spinner) findViewById(R.id.CbTiempo);
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String resu = "";
                    //comprobamos que se ha seleccionado alguna de las operaciones a realizar

                    if (Tv.getText().toString().isEmpty()) {
                        resu = "Seleccione una Imagen de Medicamento";
                        Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                    } else {
                        if (txtDescripcion.getText().toString().isEmpty()) {
                            resu = "Ingrese la Descripcion del Medicamento";
                            Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                        } else {
                            if (txtCantidad.getText().toString().isEmpty()) {
                                resu = "Ingrese la Cantidad";
                                Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                            } else {
                                if (cbTiempo.getSelectedItemPosition() == 0 || cbTiempo.getSelectedItem() == "Seleccione") {
                                    resu = "Seleccione el Tiempo";
                                    Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                                } else {
                                    if (txtPeriocidad.getText().toString().isEmpty() && cbTiempo.getSelectedItemPosition() == 1) {
                                        resu = "Ingrese la Periocidad!";
                                        Toast.makeText(getApplicationContext(), resu, Toast.LENGTH_LONG).show();
                                    } else {
                                        Bitmap bitmap = ((BitmapDrawable) shotview.getDrawable()).getBitmap();
                                        if (bitmap != null) {
                                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArray);
                                            img = byteArray.toByteArray();
                                        }

                                        DbMedicamentos dbMedicamentos = new DbMedicamentos(NuevoActivity.this);
                                        long id = dbMedicamentos.insertarMedicamento(txtDescripcion.getText().toString(), txtCantidad.getText().toString(), cbTiempo.getSelectedItem().toString()
                                                , txtPeriocidad.getText().toString(), img);

                                        if (id > 0) {
                                            Toast.makeText(NuevoActivity.this, "Registro Agreado Exitosamente!", Toast.LENGTH_LONG).show();
                                            limpiar();
                                            lista();
                                            //dbEmpleados.mostrarEmpleados();
                                        } else {
                                            Toast.makeText(NuevoActivity.this, "Error al Guardar Registro", Toast.LENGTH_LONG).show();
                                        }
                                    }
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
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(NuevoActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //goToCamera();
                        abrirCamara();
                    } else {
                        ActivityCompat.requestPermissions(NuevoActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    // goToCamera();
                    abrirCamara();
                }
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Items);

        s1.setAdapter(adapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {


                    case 0:
                        int indzex = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                       break;
                    case 1:
                        int index = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(true);
                        txtPeriocidad.setFocusable(true);
                        break;
                    case 2:
                        int indzex1 = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                        break;
                    case 3:
                        int indzex2 = s1.getSelectedItemPosition();
                        txtPeriocidad.setEnabled(false);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }






    /*if(campo.getSelectedItemPosition()==0){
        TextView errorText=(TextView)campo.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.BLUE);
        errorText.setText("Seleccione un pais");
    }*/



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            shotview.setImageBitmap(imgBitmap);
            Tv.setText("1");
           // Uri imageUri;
            //imageUri = data.getData();
            //shotview.setImageURI(imageUri);
        }
    }
    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }
    private void limpiar() {
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPeriocidad.setText("");
        cbTiempo.setSelection(0);
        Tv.setText("");
    }
    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    }
