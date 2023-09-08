package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;




public class Restaurante
{

	private ArrayList<Ingrediente> ingredientes;
	private ArrayList<ProductoMenu> menuBase;
	private ArrayList<Combo> combos;
	private ArrayList<Pedido> pedidos;
	private Pedido pedidoEnCurso;

	public Restaurante()
	{
		this.pedidos = new ArrayList<Pedido>();
	}
	
	/**
     * Ejecutar Cargar Archivos del restaurante
     */
	public void cargarInformacionRestaurante(String archivoIngredientes, String archivoMenu, String archivoCombos)
	{
		cargarIngredientes(archivoIngredientes);
		cargarMenu(archivoMenu);
		cargarCombos(archivoCombos);
	}

	/**
     * Cargar ingredientes
     */
	private void cargarIngredientes(String filePath)
	{
		this.ingredientes = new ArrayList<Ingrediente>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				Ingrediente ingrediente = new Ingrediente(datos[0],datos[1], Integer.parseInt(datos[2]));
				ingredientes.add(ingrediente);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}

	}

	/**
     * Cargar Menu del restaurante
     */
	private void cargarMenu(String filePath)
	{
		this.menuBase = new ArrayList<ProductoMenu>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				ProductoMenu producto = new ProductoMenu(datos[0],datos[1], Integer.parseInt(datos[2]));
				menuBase.add(producto);
			}
			lector.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("File " + filePath + "not found.");
			e.printStackTrace();
		}

	}

	/**
     * Cargar combos
     */
	private void cargarCombos(String filePath)
	{
		this.combos = new ArrayList<Combo>();
		try
		{
			File archivo = new File(filePath);
			Scanner lector = new Scanner(archivo);
			while (lector.hasNextLine())
			{
				String linea = lector.nextLine();
				String[] datos = linea.split(";");
				Double descuento = 1.0 - (Double.parseDouble(datos[2].replace("%", "")) / 100);
				Combo combo = new Combo(datos[0],descuento, datos[1]);

				String nombreProducto1 = datos[3];
				String nombreProducto2 = datos[4];
				String nombreProducto3 = datos[5];
				int encontrados = 0;
				for (Producto elementoMenu : menuBase)
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

	/*
	 * -----------------------------------------------------------------------------------------------------------------
	 */
	
	/**
     * Ejecura iniciar pedido nuevo
     */
	public void iniciarPedido(String nombreCliente, String direccionCliente)
	{
		this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
	}

	/**
     * Ejecura Terminar pedido
     */
	public void cerrarYGuardarPedido()
	{
		pedidoEnCurso.guardarFactura();
		pedidos.add(pedidoEnCurso);
		System.out.println("ID del pedido: " + pedidoEnCurso.getIdPedido());
		System.out.println("Pedido realizado.\n");
	}

	/**
     * Consultar información de un pedido
     */
	public void consultarInfoPedido(String idPedido)
	{
		File directorio = new File("facturas");
		String[] hijos = directorio.list();

		if (hijos == null)
		{
			System.out.println("No se han realizado pedidos");
		} 
		else
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

	/**
     * Agregar un producto básico a un pedido
     */
	public void agregarProductoBase(String IDProductoAgregar)
	{
		boolean encontrado = false;
		for (Producto base : this.menuBase)
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

	/**
     * Agregar un ingrediente al pedido
     */
	public void adicionarIngredienteAlPedido(String IDIngredienteAgregar)
	{
		boolean encontrado = false;
		for (Ingrediente ingrediente : this.ingredientes)
		{
			String nombreID = ingrediente.getID();
			if (nombreID.equals(IDIngredienteAgregar))
			{
				encontrado = true;
				pedidoEnCurso.adicionarIngredienteAlPedido(ingrediente);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el ingrediente.");
	}

	/**
     * Eliminar un ingrediente de un pedido
     */
	public void eliminarIngredienteDelPedido(String IDIngredienteEliminar)
	{
		boolean encontrado = false;
		for (Ingrediente ingrediente : this.ingredientes)
		{
			String IDIngrediente = ingrediente.getID();
			if (IDIngrediente.equals(IDIngredienteEliminar))
			{
				encontrado = true;
				pedidoEnCurso.eliminarIngredienteDelPedido(ingrediente);
				break;
			}
		}
		if (!encontrado)
			System.out.println("No se encontró el ingrediente.");
	}

	/**
     * Agregar un combo al pedido
     */
	public void agregarComboAlPedido(String IDComboAgregar)
	{
		boolean encontrado = false;
		for (Combo combo : this.combos)
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

	/*
	 * -----------------------------------------------------------------------------------------------------------
	 */
	/**
     * Confirmar los cambios en el pedido
     */
	public void confirmarProducto()
	{
		pedidoEnCurso.agregarProductoAjustado();
	}

	
	/**
     * Variables con la información 
     */
	public ArrayList<Ingrediente> getIngredientes()
	{
		return ingredientes;
	}

	public ArrayList<ProductoMenu> getMenuBase()
	{
		return menuBase;
	}

	public ArrayList<Combo> getCombos()
	{
		return combos;
	}

	public Pedido getPedidoEnCurso()
	{
		return pedidoEnCurso;
	}

	/**
     * Mostar lnformación
     */

	public void mostrarMenu() {
	    System.out.println("\nMENÚ---------------");
	    System.out.printf("%-10s%-24s%-10s%n", "ID", "Nombre", "Precio");
	    for (ProductoMenu producto : this.getMenuBase()) {
	        System.out.printf("%-10s%-24s%-10d%n", producto.getID(), producto.getNombre(), producto.getPrecio());
	    }
	    
	    System.out.println("\nCOMBOS---------------");
	    System.out.printf("%-10s%-20s%-10s%n", "ID", "Nombre", "Precio");
	    for (Combo combo : this.getCombos()) {
	        System.out.printf("%-10s%-20s%-10d%n", combo.getID(), combo.getNombre(), combo.getPrecio());
	    }
	    
	    System.out.println("\nADICIONES---------------");
	    System.out.printf("%-10s%-20s%-15s%n", "ID", "Nombre", "Costo Adicional");
	    for (Ingrediente ingrediente : this.getIngredientes()) {
	        System.out.printf("%-10s%-20s%-15s%n", ingrediente.getID(), ingrediente.getNombre(), ingrediente.getCostoAdicional());
	    }
	    
	    try {
	        Thread.sleep(500);
	    } catch (InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}

}