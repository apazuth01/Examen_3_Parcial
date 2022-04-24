package com.example.examen_3_parcial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.examen_3_parcial.Models.Medicamentos;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DbMedicamentos extends DbHelper{
    Context context;

    public DbMedicamentos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarMedicamento(String descripcion, String cantidad, String tiempo, String periocidad, byte[]  imagen) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("descripcion", descripcion);
            values.put("cantidad", cantidad);
            values.put("tiempo", tiempo);
            values.put("periocidad", periocidad);
            values.put("imagen", imagen);

            id = db.insert(TABLE_MEDICAMENTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }

    public ArrayList<Medicamentos> mostrarMedicamentos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Medicamentos> listaMedicamentos = new ArrayList<>();
        Medicamentos medicamento;
        Cursor cursorMedicamentos;

        cursorMedicamentos = db.rawQuery("SELECT * FROM " + TABLE_MEDICAMENTOS + " ORDER BY descripcion ASC", null);

        if (cursorMedicamentos.moveToFirst()) {
            do {
                medicamento = new Medicamentos();
                medicamento.setId(cursorMedicamentos.getInt(0));
                medicamento.setDescripcion(cursorMedicamentos.getString(1));
                medicamento.setCantidad(cursorMedicamentos.getString(2));
                medicamento.setTiempo(cursorMedicamentos.getString(3));
                medicamento.setPeriocidad(cursorMedicamentos.getString(4));
                medicamento.setImagen(cursorMedicamentos.getBlob(5).toString().getBytes());

                ByteArrayInputStream imageStream = new ByteArrayInputStream(cursorMedicamentos.getBlob(5));
                Bitmap theImage= BitmapFactory.decodeStream(imageStream);
                listaMedicamentos.add(medicamento);
            } while (cursorMedicamentos.moveToNext());
        }

        cursorMedicamentos.close();

        return listaMedicamentos;
    }
    public Medicamentos verMedicamento(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Medicamentos medicamento = null;
        Cursor cursorMedicamentos;

        cursorMedicamentos = db.rawQuery("SELECT * FROM " + TABLE_MEDICAMENTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorMedicamentos.moveToFirst()) {
            medicamento = new Medicamentos();
            medicamento.setId(cursorMedicamentos.getInt(0));
            medicamento.setDescripcion(cursorMedicamentos.getString(1));
            medicamento.setCantidad(cursorMedicamentos.getString(2));
            medicamento.setTiempo(cursorMedicamentos.getString(3));
            medicamento.setPeriocidad(cursorMedicamentos.getString(4));
            medicamento.setImagen(cursorMedicamentos.getBlob(5));
            //ByteArrayInputStream imageStream = new ByteArrayInputStream(cursorMedicamentos.getBlob(5));
            //Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        }

        cursorMedicamentos.close();

        return medicamento;
    }

    public boolean editarMedicamento(int id, String descripcion, String cantidad, String tiempo, String periocidad, byte[] imagen) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_MEDICAMENTOS + " SET descripcion = '" + descripcion + "', cantidad = '" + cantidad + "', tiempo = '" + tiempo +
                    "', periocidad = '" + periocidad + "', imagen = '" + imagen + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean editarMedicamento_sinImagen(int id, String descripcion, String cantidad, String tiempo, String periocidad) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_MEDICAMENTOS + " SET descripcion = '" + descripcion + "', cantidad = '" + cantidad + "', tiempo = '" + tiempo +
                    "', periocidad = '" + periocidad + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
    public boolean eliminarMedicamento(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_MEDICAMENTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
