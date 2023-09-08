package ModeloModificado;

import java.util.ArrayList;


public class ComboM implements ProductoM
{
	/*
	 * Atributos de los combos
	 */
	private Double descuento;
	private String ID;
	private String nombreCombo;
	private ArrayList<ProductoMenuM> itemsCombo;

	/*
	 * Constructor de los combos
	 */
	public ComboM(String ID, Double descuento, String nombreCombo)
	{
		this.ID = ID;
		this.descuento = descuento;
		this.nombreCombo = nombreCombo;
		this.itemsCombo = new ArrayList<ProductoMenuM>();
	}
	
	/*
	 * Metodos para acceder a los atributos
	 */
	@Override
	public int getPrecio()
	{
		int precio = 0;
		
		for (ProductoMenuM producto : itemsCombo)
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
	
	@Override
	public int getCalorias()
	{
		int calorias = 0;

		for (ProductoMenuM producto : itemsCombo)
			calorias += producto.getCalorias();

		return calorias;
	}
	/*
	 * Agregar un producto al combo
	 */
	public void agregarItemACombo(ProductoM elementoMenu)
	{
		itemsCombo.add((ProductoMenuM) elementoMenu);
	}
	
	/*
	 * Texto Factura
	 */
	@Override
	public String generarTextoFactura()
	{
		String textoFactura = this.getID()+ " "+this.getNombre() + " ---> " + this.getPrecio();
		textoFactura += "\n\t Calorías ---> " + this.getCalorias(); 
 		return textoFactura;
	}
}