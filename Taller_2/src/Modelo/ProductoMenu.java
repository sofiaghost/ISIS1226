package Modelo;



public class ProductoMenu implements Producto
{   
	/*
	 * Atributos del Menú
	 */
	private String ID;
	private String nombre;
	private int precioBase;
	
	/*
	 * Contructor Menú
	 */
	public ProductoMenu(String ID, String nombre, int precioBase)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.precioBase = precioBase;
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

	/*
	 * Texto para generar la factura
	 */
	@Override
	public String generarTextoFactura()
	{
		String textoFactura = this.getID()+ " "+this.getNombre() + " ---> " + this.getPrecio();
		return textoFactura;
	}

}