package ModeloModificado;

public class Bebida implements ProductoM
{
	private String ID;
	private String nombre;
	private int precio;
	private int calorias;

	public Bebida(String ID, String nombre, int precio, int calorias)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.precio = precio;
		this.calorias = calorias;
	}

	@Override
	public String getID()
	{
		return ID;
	}
	@Override
	public int getPrecio()
	{
		return precio;
	}

	@Override
	public String getNombre()
	{
		return nombre;
	}

	@Override
	public int getCalorias()
	{
		return calorias;
	}

	@Override
	public String generarTextoFactura()
	{
		String textoFactura = this.getID()+" "+this.getNombre() + " ---> " + this.getPrecio();
		textoFactura += "\n\t Calorías ---> " + this.getCalorias(); 
		return textoFactura;
	}

}