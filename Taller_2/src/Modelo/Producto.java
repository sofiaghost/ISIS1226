package Modelo;

/*
 * iInterfaz para todos los productos 
 */
public interface Producto
{
	public String getID();
	public int getPrecio();
	public String getNombre();
	public String generarTextoFactura();
}
