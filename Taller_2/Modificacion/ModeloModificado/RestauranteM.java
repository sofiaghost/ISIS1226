package ModeloModificado;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RestauranteM
{
	/*
	 * Atributos del restaurante
	 */

	private ArrayList<IngredienteM> ingredientes;
	private ArrayList<ProductoMenuM> menuBase;
	private ArrayList<ComboM> combos;
	private ArrayList<PedidoM> pedidos;
	private ArrayList<Bebida> bebidas;
	private PedidoM pedidoEnCurso;

	public RestauranteM()
	{
		this.pedidos = new ArrayList<PedidoM>();
	}

	/*
	 * Cargar archivos 
	 */
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos,
			String archivoBebidas)
	{

		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
		cargarBebidas(archivoBebidas);

	}

	private void cargarIngredientes(String filePath)
	{
		this.ingredientes = new ArrayList<IngredienteM>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				IngredienteM ingrediente = new IngredienteM(datos[0],datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
				ingredientes.add(ingrediente);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}

	}

	private void cargarMenu(String filePath)
	{
		this.menuBase = new ArrayList<ProductoMenuM>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				ProductoMenuM producto = new ProductoMenuM(datos[0],datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
				menuBase.add(producto);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}

	}

	private void cargarCombos(String filePath)
	{
		this.combos = new ArrayList<ComboM>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				Double descuento = 1.0 - (Double.parseDouble(datos[2].replace("%", "")) / 100);
				ComboM combo = new ComboM(datos[0],descuento, datos[1]);

				String nombreProducto1 = datos[2];
				String nombreProducto2 = datos[3];
				String nombreProducto3 = datos[4];
				int encontrados = 0;
				for (ProductoM elementoMenu : menuBase)
				{
					if ((nombreProducto1.equals(elementoMenu.getNombre()))
							|| (nombreProducto2.equals(elementoMenu.getNombre()))
							|| (nombreProducto3.equals(elementoMenu.getNombre())))
					{
						encontrados++;
						combo.agregarItemACombo(elementoMenu);
					}

					if (encontrados == 3)
						break;
				}
				combos.add(combo);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}
	}

	public void cargarBebidas(String filePath)
	{
		this.bebidas = new ArrayList<Bebida>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				Bebida producto = new Bebida(datos[0],datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
				bebidas.add(producto);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}

	}

	
	/*
	 * Interaccion con el pedido
	 */
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		this.pedidoEnCurso = new PedidoM(nombreCliente, direccionCliente);
	}

	public void cerrarYGuardarPedido()
	{
		pedidoEnCurso.guardarFactura();
		if (existePedidoIdentico())
			System.out.println("MODIFICACION: Un pedido idéntico ya se ha realizado...");
		else
			pedidos.add(pedidoEnCurso);
		System.out.println("ID del pedido: " + pedidoEnCurso.getIdPedido());
		System.out.println("Pedido realizado.\n");
	}

	private boolean existePedidoIdentico()
	{
		boolean existeIdentico = false;

		ArrayList<ProductoM> itemsPedidoEnCurso = pedidoEnCurso.getItems();

		for (PedidoM pedidoEnRevision : pedidos)
		{
			ArrayList<ProductoM> itemsPedidoEnRevision = pedidoEnRevision.getItems();
			if (itemsPedidoEnCurso.size() == itemsPedidoEnRevision.size())
			{
				int coincidencias = 0;

				for (ProductoM itemPedidoEnCurso : itemsPedidoEnCurso)
				{
					for (ProductoM itemPedidoEnRevision : itemsPedidoEnRevision)
					{
						if ((itemPedidoEnCurso instanceof ProductoAjustadoM)
								&& (itemPedidoEnRevision instanceof ProductoAjustadoM))
						{
							if (compararProductosAjustados((ProductoAjustadoM)itemPedidoEnCurso, (ProductoAjustadoM) itemPedidoEnRevision))
							{
								coincidencias++;
							}
						} else if (itemPedidoEnCurso.equals(itemPedidoEnRevision))
						{
							coincidencias++;
						}
					}
				}
				if (coincidencias == itemsPedidoEnCurso.size())
				{
					existeIdentico = true;
					break;
				}
			}
		}

		return existeIdentico;
	}

	private boolean compararProductosAjustados(ProductoAjustadoM itemPedidoEnCurso,
			ProductoAjustadoM itemPedidoEnRevision)
	{
		boolean iguales = false;

		ArrayList<IngredienteM> agregadosEnCurso = itemPedidoEnCurso.getAgregados();
		ArrayList<IngredienteM> agregadosEnRevision = itemPedidoEnRevision.getAgregados();
		ArrayList<IngredienteM> eliminadosEnCurso = itemPedidoEnCurso.getEliminados();
		ArrayList<IngredienteM> eliminadosEnRevision = itemPedidoEnRevision.getEliminados();
		String nombreEnCurso = itemPedidoEnCurso.getNombre();
		String nombreEnRevision = itemPedidoEnRevision.getNombre();

		if ((agregadosEnCurso.size() == agregadosEnRevision.size())
				&& (eliminadosEnCurso.size() == eliminadosEnRevision.size())
				&& (nombreEnCurso.equals(nombreEnRevision)))
		{
			int coincidenciasAgregados = 0;
			int coincidenciasEliminados = 0;

			for (IngredienteM ingredienteEnCurso1 : agregadosEnCurso)
			{
				for (IngredienteM ingredienteEnRevision1 : agregadosEnRevision)
				{
					if (ingredienteEnCurso1.equals(ingredienteEnRevision1))
						coincidenciasAgregados++;
				}
				if (coincidenciasAgregados == agregadosEnCurso.size())
				{
					for (IngredienteM ingredienteEnCurso2 : eliminadosEnCurso)
					{
						for (IngredienteM ingredienteEnRevision2 : eliminadosEnRevision)
						{
							if (ingredienteEnCurso2.equals(ingredienteEnRevision2))
								coincidenciasEliminados++;
						}
					}
				}
			}

			if ((coincidenciasAgregados == agregadosEnCurso.size())
					&& (coincidenciasEliminados == eliminadosEnCurso.size()))
				iguales = true;
		}
		return iguales;
	}

	public void consultarInfoPedido(String idPedido)
	{
		File directorio = new File("facturas");
		String[] hijos = directorio.list();

		if (hijos == null)
		{
			System.out.println("No se han realizado pedidos");
		} else
		{
			for (int i = 0; i < hijos.length; i++)
			{
				String nombreArchivo = hijos[i];
				if (nombreArchivo.equals(idPedido + ".txt"))
				{
					try
					{
						File archivo = new File("facturas/" + nombreArchivo);
						Scanner lector = new Scanner(archivo);
						while (lector.hasNextLine())
						{
							String linea = lector.nextLine();
							System.out.println(linea);
						}
						lector.close();
					} catch (FileNotFoundException e)
					{
						System.out.println("File " + " facturas/" + nombreArchivo + "not found.");
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * Agregar productos al pedido
	 */
	public void agregarProductoBase(String IDProductoAgregar)
	{
		boolean encontrado = false;
		for (ProductoM base : this.menuBase)
		{
			String IDBase = base.getID();
			if (IDBase.equals(IDProductoAgregar))
			{
				encontrado = true;
				pedidoEnCurso.nuevoProductoAjustado(base);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el producto.");
	}

	public void adicionarIngredienteAlPedido(String IDIngredienteAgregar)
	{
		boolean encontrado = false;
		for (IngredienteM ingrediente : this.ingredientes)
		{
			String IDIngrediente = ingrediente.getID();
			if (IDIngrediente.equals(IDIngredienteAgregar))
			{
				encontrado = true;
				pedidoEnCurso.adicionarIngredienteAlProducto(ingrediente);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el ingrediente.");
	}

	public void eliminarIngredienteDelPedido(String IDIngredienteEliminar)
	{
		boolean encontrado = false;
		for (IngredienteM ingrediente : this.ingredientes)
		{
			String IDIngrediente = ingrediente.getID();
			if (IDIngrediente.equals(IDIngredienteEliminar))
			{
				encontrado = true;
				pedidoEnCurso.eliminarIngredienteDelProducto(ingrediente);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el ingrediente.");
	}

	public void confirmarProducto()
	{
		pedidoEnCurso.agregarProductoAjustado();
	}

	public void agregarComboAlPedido(String IDComboAgregar)
	{
		boolean encontrado = false;
		for (ComboM combo : this.combos)
		{
			String IDCombo = combo.getID();
			if (IDCombo.equals(IDComboAgregar))
			{
				encontrado = true;
				pedidoEnCurso.agregarCombo(combo);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el combo.");
	}

	
	public void agregarBebidaAlPedido(String IDBebidaAgregar)
	{
		boolean encontrado = false;
		for (Bebida bebida : this.bebidas)
		{
			String IDBebida = bebida.getID();
			if (IDBebida.equals(IDBebidaAgregar))
			{
				encontrado = true;
				pedidoEnCurso.agregarBebida(bebida);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró la bebida.");
	}

	/*
	 * Variables con la información
	 */
	public ArrayList<IngredienteM > getIngredientes()
	{
		return ingredientes;
	}

	public ArrayList<ProductoMenuM> getMenuBase()
	{
		return menuBase;
	}

	public ArrayList<ComboM> getCombos()
	{
		return combos;
	}

	public ArrayList<PedidoM> getPedidos()
	{
		return pedidos;
	}

	public ArrayList<Bebida> getBebidas()
	{
		return bebidas;
	}

	public PedidoM getPedidoEnCurso()
	{
		return pedidoEnCurso;
	}

	/*
	 * Mostrar Menú
	 */
	public void mostrarMenu() {
	    System.out.println("\nMENÚ---------------");
	    System.out.printf("%-10s%-24s%-10s%n", "ID", "Nombre", "Precio");
	    for (ProductoMenuM producto : this.getMenuBase()) {
	        System.out.printf("%-10s%-24s%-10d%n", producto.getID(), producto.getNombre(), producto.getPrecio());
	    }
	    
	    System.out.println("\nBEBIDAS---------------");
	    System.out.printf("%-10s%-20s%-10s%n", "ID", "Nombre", "Precio");
	    for (Bebida bebida : this.getBebidas())
	    	System.out.printf("%-10s%-20s%-10d%n", bebida.getID(), bebida.getNombre(), bebida.getPrecio());
	    
	    
	    System.out.println("\nCOMBOS---------------");
	    System.out.printf("%-10s%-20s%-10s%n", "ID", "Nombre", "Precio");
	    for (ComboM combo : this.getCombos()) {
	        System.out.printf("%-10s%-20s%-10d%n", combo.getID(), combo.getNombre(), combo.getPrecio());
	    }
	    
	    System.out.println("\nADICIONES---------------");
	    System.out.printf("%-10s%-20s%-15s%n", "ID", "Nombre", "Costo Adicional");
	    for (IngredienteM ingrediente : this.getIngredientes()) {
	        System.out.printf("%-10s%-20s%-15s%n", ingrediente.getID(), ingrediente.getNombre(), ingrediente.getCostoAdicional());
	    }
	    
	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}

}