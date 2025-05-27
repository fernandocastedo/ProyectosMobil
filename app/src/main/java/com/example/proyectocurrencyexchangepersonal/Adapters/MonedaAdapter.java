package com.example.proyectocurrencyexchangepersonal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocurrencyexchangepersonal.R;
import com.example.proyectocurrencyexchangepersonal.models.Moneda;

import java.util.ArrayList;
import java.util.List;

public class MonedaAdapter extends RecyclerView.Adapter<MonedaAdapter.MonedaViewHolder> {

    private List<Moneda> monedas = new ArrayList<>();

    @Override
    public MonedaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moneda, parent, false);
        return new MonedaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonedaViewHolder holder, int position) {
        Moneda moneda = monedas.get(position);
        holder.bind(moneda);
    }

    @Override
    public int getItemCount() {
        return monedas.size();
    }

    public void setMonedas(List<Moneda> monedas) {
        this.monedas = monedas;
        notifyDataSetChanged();
    }

    public static class MonedaViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombreMoneda, tvCodigoMoneda, tvPaisOrigen, tvValorReferencia;

        public MonedaViewHolder(View itemView) {
            super(itemView);
            tvNombreMoneda = itemView.findViewById(R.id.tvNombreMoneda);
            tvCodigoMoneda = itemView.findViewById(R.id.tvCodigoMoneda);
            tvPaisOrigen = itemView.findViewById(R.id.tvPaisOrigen);
            tvValorReferencia = itemView.findViewById(R.id.tvValorReferencia);
        }

        public void bind(Moneda moneda) {
            tvNombreMoneda.setText("Nombre: " + moneda.getNombre());
            tvCodigoMoneda.setText("Código: " + moneda.getCodigo());
            tvPaisOrigen.setText("País: " + moneda.getPaisOrigen());
            tvValorReferencia.setText("Valor: " + moneda.getValorReferencia());
        }
    }
}

