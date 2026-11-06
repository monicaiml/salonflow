
package modelo;

import java.time.LocalDateTime;

public class Factura {

    private int id_factura;
    private LocalDateTime fecha_emision;
    private double total;
    private int id_cita;

    public Factura(int id_factura, LocalDateTime fecha_emision, double total, int id_cita) 
    {

        this.id_factura = id_factura;
        this.fecha_emision = fecha_emision;
        this.total = total;
        this.id_cita = id_cita;

    }

    
    public int getId_factura() 
    {
        return id_factura;
    }

    public void setId_factura(int id_factura) 
    {
        this.id_factura = id_factura;
    }

    public LocalDateTime getFecha_emision() 
    {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDateTime fecha_emision) 
    {
        this.fecha_emision = fecha_emision;
    }

    public double getTotal() 
    {
        return total;
    }

    public void setTotal(double total) 
    {
        this.total = total;
    }

    public int getId_cita() 
    {
        return id_cita;
    }

    public void setId_cita(int id_cita) 
    {
        this.id_cita = id_cita;
    }

}
