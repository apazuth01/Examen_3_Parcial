package com.example.examen_3_parcial.adapatadores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_3_parcial.MainActivity;
import com.example.examen_3_parcial.Models.Medicamentos;
import com.example.examen_3_parcial.R;
import com.example.examen_3_parcial.VerActivity;
import com.example.examen_3_parcial.db.DbMedicamentos;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDatosAdapter extends RecyclerView.Adapter<ListaDatosAdapter.MediViewHolder>{
    ArrayList<Medicamentos> listaMedicamentos;
    ArrayList<Medicamentos> listaOriginal;
    byte[] photo;
    Bitmap theImage;
    ImageView vimagen;
    Medicamentos medicamento;
    int id = 0;

    public ListaDatosAdapter(ArrayList<Medicamentos> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaMedicamentos);
    }


    @NonNull
    @Override
    public MediViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_medicamento, null, false);
        return new MediViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediViewHolder holder, int position) {
//        holder.viewId.setText(listaMedicamentos.get(position).getId());
       holder.viewDescripcion.setText(listaMedicamentos.get(position).getDescripcion());
       holder.viewCantidad.setText(listaMedicamentos.get(position).getCantidad());
       holder.viewPeriocidad.setText(listaMedicamentos.get(position).getPeriocidad());
      //holder.viewImagen.setText(listaMedicamentos.get(position).getImagen().toString());
       // holder.viewImagen.setText(listaMedicamentos.get(position).getImagen().toString());
       // holder.viewImagen.setText(listaMedicamentos.get(position).getImagen().toString());
     // holder.photo= listaMedicamentos.get(position).getImagen();
      // ByteArrayInputStream imageStream = new ByteArrayInputStream(holder.photo);
       //Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        //logoImage.setImageBitmap(BitmapFactory.decodeByteArray( currentAccount.accImage,
          //      0,currentAccount.accImage.length));
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaMedicamentos.clear();
            listaMedicamentos.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Medicamentos> collecion = listaMedicamentos.stream()
                        .filter(i -> i.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaMedicamentos.clear();
                listaMedicamentos.addAll(collecion);
            } else {
                for (Medicamentos c : listaOriginal) {
                    if (c.getDescripcion().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaMedicamentos.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaMedicamentos.size();
    }

    public class MediViewHolder extends RecyclerView.ViewHolder {

        TextView viewDescripcion, viewCantidad, viewTiempo, viewPeriocidad, viewImagen, viewId;
        ImageView vImagen;
        byte[] photo;
        public MediViewHolder(@NonNull View itemView) {
            super(itemView);
            viewDescripcion = itemView.findViewById(R.id.viewDescripcion);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewPeriocidad = itemView.findViewById(R.id.viewPeriocidad);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("Id", listaMedicamentos.get(getAdapterPosition()).getId());
                    intent.putExtra("Imagen", listaMedicamentos.get(getAdapterPosition()).getImagen());
                    context.startActivity(intent);
                }
            });
        }
    }

}
