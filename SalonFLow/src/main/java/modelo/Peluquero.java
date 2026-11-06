package modelo;

public class Peluquero 
{

    private int id_peluquero;
    private String nombre;
    private String especialidad;

    public Peluquero(int id_peluquero, String nombre, String especialidad) 
    {
        this.id_peluquero = id_peluquero;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }


    public int getId_peluquero() 
    {
        return id_peluquero;
    }

    public void setId_peluquero(int id_peluquero) 
    {
        this.id_peluquero = id_peluquero;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getEspecialidad() 
    {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) 
    {
        this.especialidad = especialidad;
    }

}
