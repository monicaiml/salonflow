
package modelo;

import java.time.LocalDateTime;

public class Cita {

    private int id_cita;
    private LocalDateTime fecha_hora;
    private String estado;
    private int id_cliente;
    private int id_peluquero;

    public Cita(int id_cita, LocalDateTime fecha_hora, String estado, int id_cliente, int id_peluquero) 
    {

        this.id_cita = id_cita;
        this.fecha_hora = fecha_hora;
        this.estado = estado;
        this.id_cliente = id_cliente;
        this.id_peluquero = id_peluquero;

    }

    
    public int getId_cita() 
    {
        return id_cita;
    }

    public void setId_cita(int id_cita) 
    {
        this.id_cita = id_cita;
    }

    public LocalDateTime getFecha_hora() 
    {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) 
    {
        this.fecha_hora = fecha_hora;
    }

    public String getEstado() 
    {
        return estado;
    }

    public void setEstado(String estado) 
    {
        this.estado = estado;
    }

    public int getId_cliente() 
    {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) 
    {
        this.id_cliente = id_cliente;
    }

    public int getId_peluquero() 
    {
        return id_peluquero;
    }

    public void setId_peluquero(int id_peluquero) 
    {
        this.id_peluquero = id_peluquero;
    }

}
