package Modelo;

import java.util.ArrayList;


public class Combo implements Producto
{
	/*
	 * Atributos de los combos
	 */
	private Double descuento;
	private String ID;
	private String nombreCombo;
	private ArrayList<ProductoMenu> itemsCombo;

	/*
	 * Constructor de los combos
	 */
	public Combo(String ID, Double descuento, String nombreCombo)
	{
		this.ID = ID;
		this.descuento = descuento;
		this.nombreCombo = nombreCombo;
		this.itemsCombo = new ArrayList<ProductoMenu>();
	}
	
	/*
	 * Metodos para acceder a los atributos
	 */
	@Override
	public int getPrecio()
	{
		int precio = 0;
		
		for (ProductoMenu producto : itemsCombo)
			precio += producto.getPrecio();
		
		double precioConDescuento = (double) precio * descuento;
		int precioRedondeado = (int) Math.round(precioConDescuento);
		return precioRedondeado;
	}

	@Override
	public String getNombre()
	{
		return nombreCombo;
	}

	@Override
	public String getID()
	{
		return ID;
	}
		
	/*
	 * Agregar un producto al combo
	 */
	public void agregarItemACombo(Producto itemCombo)
	{
		itemsCombo.add((ProductoMenu) itemCombo);
	}
	
	/*
	 * Texto Factura
	 */
	@Override
	public String generarTextoFactura()
	{
		String textoFactura = this.getID()+ " "+this.getNombre() + " ---> " + this.getPrecio();
 		return textoFactura;
	}
}