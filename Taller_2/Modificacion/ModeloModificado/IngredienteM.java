package ModeloModificado;

public class IngredienteM
{
	/*
	 * Atributos para ingrediente
	 */
	private String ID;
	private String nombre;
	private int costoAdicional;
	private int calorias;

	/*
	 * Constructor de Ingrediente
	 */
	public IngredienteM(String ID, String nombre, int costoAdicional, int calorias)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.costoAdicional = costoAdicional;
		this.calorias = calorias;
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

	public int getCalorias()
	{
		return calorias;
	}
}