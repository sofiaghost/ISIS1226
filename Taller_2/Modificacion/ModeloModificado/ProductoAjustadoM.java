package ModeloModificado;
import java.util.ArrayList;


public class ProductoAjustadoM implements ProductoM
{
	private ProductoMenuM base;
	private ArrayList<IngredienteM> agregados;
	private ArrayList<IngredienteM> eliminados;

	public ProductoAjustadoM(ProductoMenuM base)
	{
		/*
		 * Atributos para ajustar el producto
		 */
		this.base = base;
		this.agregados = new ArrayList<IngredienteM>();
		this.eliminados = new ArrayList<IngredienteM>();
	}

	/*
	 * Metodos para adicionar o eliminar un ingrediente
	 */
	public void adicionarIngrediente(IngredienteM ingrediente)
	{
		agregados.add(ingrediente);
	}

	public void eliminarIngrediente(IngredienteM ingrediente)
	{
		eliminados.add(ingrediente);
	}

	/*
	 * Obtener el nuevo precio
	 */
	@Override
	public int getPrecio()
	{
		int precio = 0;

		if (base != null)
			precio += base.getPrecio();

		for (IngredienteM ingrediente : agregados)
			precio += ingrediente.getCostoAdicional();

		return precio;
	}

	@Override
	public String getNombre()
	{
		// TODO revisar
		String nombre = "";

		if (base != null)
			nombre += base.getNombre();

		if (!eliminados.isEmpty())
		{
			nombre += " sin";
			for (IngredienteM ingrediente : eliminados)
				nombre += " " + ingrediente.getNombre() + ",";
		}
		nombre = nombre.substring(0, nombre.length() - 1) + ".";

		if (!agregados.isEmpty())
		{
			nombre += " Con adición de";
			for (IngredienteM ingrediente : agregados)
				nombre += " " + ingrediente.getNombre() + ",";
		}
		nombre = nombre.substring(0, nombre.length() - 1) + ".";

		return nombre;
	}
	
	@Override
	public String getID()
	{
		// TODO revisar
		String ID = "";

		if (base != null)
			ID += base.getID();
		return ID;
	}
	
	@Override 
	public int getCalorias()
	{
		int calorias = 0;
		if (base != null)
			calorias += base.getCalorias();

		for (IngredienteM ingrediente : agregados)
			calorias += ingrediente.getCalorias();
		for (IngredienteM ingrediente: eliminados)
			calorias -= ingrediente.getCalorias();

		return calorias;
	}
	/*
	 * Obtener el ingrediente modificado
	 */
	public ArrayList<IngredienteM> getAgregados()
	{
		return this.agregados;
	}

	public ArrayList<IngredienteM> getEliminados()
	{
		return this.eliminados;
	}
	
	
	/*
	 * Generar factura
	 */
	@Override
	public String generarTextoFactura()
	{
		// TODO revisar
		String textoFactura = "";

		if (base != null)
			textoFactura += base.generarTextoFactura();

		if (!agregados.isEmpty())
		{
			textoFactura += "\nAdiciones:";
			for (IngredienteM ingrediente : agregados)
			{
				textoFactura += "\n\t" + ingrediente.getNombre() + " ---> " + ingrediente.getCostoAdicional();
				textoFactura += "\n\t\t Calorías ---> " + ingrediente.getCalorias(); 
			}
		}

		if (!eliminados.isEmpty())
		{
			textoFactura += "\nEliminaciones:";
			for (IngredienteM ingrediente : eliminados)
			{
				textoFactura += "\n\t" + ingrediente.getNombre() + " ---> 0";
				textoFactura += "\n\t\t Calorías eliminadas ---> " + ingrediente.getCalorias(); 
			}
		}

		return textoFactura;
	}	
}
