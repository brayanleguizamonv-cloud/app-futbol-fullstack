package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder> {

    private List<Partido> listaPartidos;

    public PartidoAdapter(List<Partido> listaPartidos) {
        this.listaPartidos = listaPartidos;
    }

    @NonNull
    @Override
    public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido, parent, false);
        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {
        Partido partido = listaPartidos.get(position);

        holder.tvLocal.setText(partido.getLocal());
        holder.tvVisitante.setText(partido.getVisitante());
        // Ahora inyectamos directamente el texto procesado
        holder.tvMarcador.setText(partido.getMarcador());

        // Inyectamos la fecha formateada en la franja superior gris
        holder.tvFecha.setText(partido.getFechaUtc());

        // Inyectamos los escudos
        Glide.with(holder.itemView.getContext()).load(partido.getEscudoLocal()).into(holder.ivLocal);
        Glide.with(holder.itemView.getContext()).load(partido.getEscudoVisitante()).into(holder.ivVisitante);
        // Si es el primer partido de la lista, o si su jornada es distinta a la del partido de arriba...
        if (position == 0 || !partido.getJornada().equals(listaPartidos.get(position - 1).getJornada())) {
            holder.tvJornadaHeader.setVisibility(View.VISIBLE); // Mostramos el título

            if (partido.getJornada().equals("0") || partido.getJornada().equals("null")) {
                holder.tvJornadaHeader.setText("Eliminatorias"); // Para la Champions
            } else {
                holder.tvJornadaHeader.setText("Jornada " + partido.getJornada());
            }
        } else {
            holder.tvJornadaHeader.setVisibility(View.GONE); // Lo ocultamos para que no se repita
        }
    }

    @Override
    public int getItemCount() {
        return listaPartidos.size();
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocal, tvVisitante, tvMarcador, tvFecha, tvJornadaHeader;
        ImageView ivLocal, ivVisitante;

        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocal = itemView.findViewById(R.id.tvLocal);
            tvVisitante = itemView.findViewById(R.id.tvVisitante);
            tvMarcador = itemView.findViewById(R.id.tvMarcador);
            ivLocal = itemView.findViewById(R.id.ivLocal);
            ivVisitante = itemView.findViewById(R.id.ivVisitante);
            tvFecha = itemView.findViewById(R.id.tvFecha);

            // Aquí enlazamos el título de la jornada
            tvJornadaHeader = itemView.findViewById(R.id.tvJornadaHeader);
        }
    }
    }
