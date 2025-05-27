package com.example.proyectocurrencyexchangepersonal.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Index;

@Entity(tableName = "transaccionmoneda",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "usuarioID",
                        childColumns = "usuarioID",
                        onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Moneda.class,
                        parentColumns = "monedaID",
                        childColumns = "monedaOrigenID",
                        onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Moneda.class,
                        parentColumns = "monedaID",
                        childColumns = "monedaDestinoID",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("usuarioID"), @Index("monedaOrigenID"), @Index("monedaDestinoID")}
)
public class TransaccionMoneda {

    @PrimaryKey(autoGenerate = true)
    private int transaccionID;

    private int usuarioID;
    private int monedaOrigenID;
    private int monedaDestinoID;
    private double monto;
    private double tasaCambio;
    private double montoConvertido;
    private long fecha;

    public TransaccionMoneda(int usuarioID, int monedaOrigenID, int monedaDestinoID,
                             double monto, double tasaCambio, double montoConvertido, long fecha) {
        this.usuarioID = usuarioID;
        this.monedaOrigenID = monedaOrigenID;
        this.monedaDestinoID = monedaDestinoID;
        this.monto = monto;
        this.tasaCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getTransaccionID() { return transaccionID; }
    public void setTransaccionID(int transaccionID) { this.transaccionID = transaccionID; }
    public int getUsuarioID() { return usuarioID; }
    public void setUsuarioID(int usuarioID) { this.usuarioID = usuarioID; }
    public int getMonedaOrigenID() { return monedaOrigenID; }
    public void setMonedaOrigenID(int monedaOrigenID) { this.monedaOrigenID = monedaOrigenID; }
    public int getMonedaDestinoID() { return monedaDestinoID; }
    public void setMonedaDestinoID(int monedaDestinoID) { this.monedaDestinoID = monedaDestinoID; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public double getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(double tasaCambio) { this.tasaCambio = tasaCambio; }
    public double getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(double montoConvertido) { this.montoConvertido = montoConvertido; }
    public long getFecha() { return fecha; }
    public void setFecha(long fecha) { this.fecha = fecha; }
}
