package ModeloModificado;



public class ProductoMenuM implements ProductoM
{   
	/*
	 * Atributos del Menú
	 */
	private String ID;
	private String nombre;
	private int precioBase;
	private int calorias;
	
	/*
	 * Contructor Menú
	 */
	public ProductoMenuM(String ID, String nombre, int precioBase, int calorias)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.precioBase = precioBase;
		this.calorias = calorias;
	}

	@Override
	public int getPrecio()
	{
		return precioBase;
	}

	@Override
	public String getNombre()
	{
		return nombre;
	}
	
	@Override
	public String getID()
	{
		return ID;
	}

	@Override
	public int getCalorias()
	{
		return calorias;
	}


	/*
	 * Texto para generar la factura
	 */
	@Override
	public String generarTextoFactura()
	{
		String textoFactura = this.getID()+ " "+this.getNombre() + " ---> " + this.getPrecio();
		textoFactura += "\n\t Calorías ---> " + this.getCalorias();
		return textoFactura;
	}

}