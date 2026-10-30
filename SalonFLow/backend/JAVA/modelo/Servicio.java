
package modelo;

public class Servicio 
{

    private int id_servicio;
    private String nombre_servicio;
    private double precio;

    public Servicio(int id_servicio, String nombre_servicio, double precio) 
    {

        this.id_servicio = id_servicio;
        this.nombre_servicio = nombre_servicio;
        this.precio = precio;

    }

    

    public int getId_servicio() 
    {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) 
    {
        this.id_servicio = id_servicio;
    }

    public String getNombre_servicio() 
    {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) 
    {
        this.nombre_servicio = nombre_servicio;
    }

    public double getPrecio() 
    {
        return precio;
    }

    public void setPrecio(double precio) 
    {
        this.precio = precio;
    }

}
