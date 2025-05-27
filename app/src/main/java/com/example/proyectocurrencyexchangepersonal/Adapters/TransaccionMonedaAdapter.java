package com.example.proyectocurrencyexchangepersonal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocurrencyexchangepersonal.R;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;

import java.util.ArrayList;
import java.util.List;

public class TransaccionMonedaAdapter extends RecyclerView.Adapter<TransaccionMonedaAdapter.TransaccionViewHolder> {

    private List<TransaccionMoneda> transacciones = new ArrayList<>();

    @Override
    public TransaccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaccion, parent, false);
        return new TransaccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransaccionViewHolder holder, int position) {
        TransaccionMoneda transaccion = transacciones.get(position);
        holder.bind(transaccion);
    }

    @Override
    public int getItemCount() {
        return transacciones.size();
    }

    public void setTransacciones(List<TransaccionMoneda> transacciones) {
        this.transacciones = transacciones;
        notifyDataSetChanged();
    }

    public static class TransaccionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMontoOriginal, tvMontoConvertido;

        public TransaccionViewHolder(View itemView) {
            super(itemView);
            tvMontoOriginal = itemView.findViewById(R.id.tvMontoOriginal);
            tvMontoConvertido = itemView.findViewById(R.id.tvMontoConvertido);
        }

        public void bind(TransaccionMoneda transaccion) {
            tvMontoOriginal.setText("Monto: " + transaccion.getMonto());
            tvMontoConvertido.setText("Convertido: " + transaccion.getTasaCambio());
        }
    }
}
