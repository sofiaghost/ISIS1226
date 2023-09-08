package Modelo;

public class Ingrediente
{
	/*
	 * Atributos para ingrediente
	 */
	private String ID;
	private String nombre;
	private int costoAdicional;

	/*
	 * Constructor de Ingrediente
	 */
	public Ingrediente(String ID, String nombre, int costoAdicional)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
	}

	/*
	 * Acceder a los atributos
	 */
	public String getID()
	{
		return ID;
	}
	
	public String getNombre()
	{
		return nombre;
	}

	public int getCostoAdicional()
	{
		return costoAdicional;
	}

}