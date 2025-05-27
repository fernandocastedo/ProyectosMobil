package com.example.proyectocurrencyexchangepersonal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocurrencyexchangepersonal.R;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionConMonedasYUsuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransaccionMonedaAdapter extends RecyclerView.Adapter<TransaccionMonedaAdapter.TransaccionViewHolder> {

    private List<TransaccionConMonedasYUsuario> transaccionesConDetalles = new ArrayList<>();

    @Override
    public TransaccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaccion, parent, false);
        return new TransaccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransaccionViewHolder holder, int position) {
        TransaccionConMonedasYUsuario transaccionConDetalles = transaccionesConDetalles.get(position);
        holder.bind(transaccionConDetalles);
    }

    @Override
    public int getItemCount() {
        return transaccionesConDetalles.size();
    }

    public void setTransaccionesConDetalles(List<TransaccionConMonedasYUsuario> transaccionesConDetalles) {
        this.transaccionesConDetalles = transaccionesConDetalles;
        notifyDataSetChanged();
    }

    public static class TransaccionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMontoOriginal, tvMontoConvertido, tvUserInfo, tvMonedaOrigen, tvMonedaDestino, tvFecha;

        public TransaccionViewHolder(View itemView) {
            super(itemView);
            tvMontoOriginal = itemView.findViewById(R.id.tvMontoOriginal);
            tvMontoConvertido = itemView.findViewById(R.id.tvMontoConvertido);
            tvUserInfo = itemView.findViewById(R.id.tvUserInfo);
            tvMonedaOrigen = itemView.findViewById(R.id.tvMonedaOrigen);
            tvMonedaDestino = itemView.findViewById(R.id.tvMonedaDestino);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }

        public void bind(TransaccionConMonedasYUsuario transaccionConDetalles) {
            tvUserInfo.setText("User: " + transaccionConDetalles.usuario.getNombre());
            tvMonedaOrigen.setText("From: " + transaccionConDetalles.monedaOrigen.getCodigo() + " - " + transaccionConDetalles.monedaOrigen.getNombre());
            tvMonedaDestino.setText("To: " + transaccionConDetalles.monedaDestino.getCodigo() + " - " + transaccionConDetalles.monedaDestino.getNombre());
            tvMontoOriginal.setText(String.format(Locale.getDefault(), "Monto: %.2f", transaccionConDetalles.transaccion.getMonto()));
            tvMontoConvertido.setText(String.format(Locale.getDefault(), "Convertido: %.2f", transaccionConDetalles.transaccion.getMontoConvertido()));

            // Format timestamp to date string
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String formattedDate = sdf.format(new Date(transaccionConDetalles.transaccion.getFecha()));
            tvFecha.setText("Date: " + formattedDate);
        }
    }
}
