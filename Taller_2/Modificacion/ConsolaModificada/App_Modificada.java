package ConsolaModificada;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import ConsolaModificada.App_Modificada;
import ModeloModificado.RestauranteM;

public class App_Modificada {

    private RestauranteM restaurante;
    
    /**
     * Cargar datos del restaurante, ejecutar menú, Finalizar aplicación 
     */
	public void ejecutarMenu()
	{
		/**
	     * Cargando datos del restaurante
	     */
		System.out.println("Pedidos Restaurante de Hamburguesas");
		System.out.println("Cargando información del restaurante ...");
		cargarInfo();
		boolean continuar = true;
		
		while (continuar)
		{
			/**
		     * Ejecutar opciones del menú
		     */
		  try
			{
				mostrarMenuAplicacion();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción: "));
				if (opcion_seleccionada == 1 && restaurante != null)
					ejecutarMostrarMenuRestaurante();
				else if (opcion_seleccionada == 2 && restaurante != null)
					ejecutarIniciarPedido();
				else if (opcion_seleccionada == 3 && restaurante != null)
					ejecutarConsultarInfoPedido();
				else if (opcion_seleccionada == 4 && restaurante != null)
				{
					/**
				     * Finalizar aplicación o seleccionar opcion validad
				     */
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				} else
					System.out.println("\nPor favor seleccione una opción válida.");
			} 
		  catch (NumberFormatException e)
			{
				System.out.println("Debes seleccionar uno de los números de las opciones.");
			}
		}
	}

	/**
     * Lectura y posibles excepciones
     */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + "");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	/**
     * Mostrar menú de la aplicación
     */
	public void mostrarMenuAplicacion()
	{
		System.out.println("\nOpciones de la aplicacion\n");
		System.out.println("1. Ver Menu");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Consultar la informacion de un pedido dado su ID");
		System.out.println("4. Salir de la aplicación\n");
	}
	
	/**
     * Consultar la información de un pedido
     */
	private void ejecutarConsultarInfoPedido()
	{
		String idPedidoConsultar = input("Ingresa el ID del pedido que deseas consultar: ");
		restaurante.consultarInfoPedido(idPedidoConsultar);
	}

	/**
     * Ejecuta Mostrar Menú
     */
	private void ejecutarMostrarMenuRestaurante()
	{
		restaurante.mostrarMenu();
	}

	/**
     * Iniciar pedido
     */
	private void ejecutarIniciarPedido()
	{
		String nombreCliente = input("Ingresa tú nombre: ");
		String direccionCliente = input("Ingresa tú dirección: ");
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
		boolean continuar = true;
		while (continuar)
		{
			System.out.println("\nOpciones del pedido:");
			System.out.println("1. Ver Menu");
			System.out.println("2. Añadir producto al pedido");
			System.out.println("3. Añadir combo al pedido");
			System.out.println("4. Añadir bebida al pedido");
			System.out.println("5. Finalizar pedido");

			int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción: "));
			if (opcion_seleccionada == 1)
				ejecutarMostrarMenuRestaurante();
			else if (opcion_seleccionada == 2)
				ejecutarAgregarProductoAlPedido();
			else if (opcion_seleccionada == 3)
				ejecutarAgregarComboAlPedido();
			else if (opcion_seleccionada == 4)
				ejecutarAgregarBebidaAlPedido();
			else if (opcion_seleccionada == 5)
			{
				ejecutarCerrarYGuardarPedido();
				continuar = false;
			} else
				System.out.println("\nPor favor seleccione una opción válida.");
		}

	}

	/**
     * Terminar el pedido
     */
	private void ejecutarCerrarYGuardarPedido()
	{
		restaurante.cerrarYGuardarPedido();
	}
	
	/**
     * Agregar un producto al pedido
     */
	private void ejecutarAgregarProductoAlPedido()
	{
		boolean continuar = true;
		ejecutarAgregarProductoBaseAlPedido();
		
		/**
	     * Menú para editar el pedido
	     */
		while (continuar)
		{
			System.out.println("Añadir y editar producto:");
			System.out.println("1. Adicionar un ingrediente al pedido");
			System.out.println("2. Eliminar un ingrediente del pedido");
			System.out.println("3. Confirmar un producto");
			
			int opcion_seleccionada = Integer.parseInt(input("Por favor selecciona una opción:"));
			
			if (opcion_seleccionada == 1)
				ejecutarAdicionarIngredienteAlProducto();
			else if (opcion_seleccionada == 2)
				ejecutarEliminarIngredienteDelProducto();
			else if (opcion_seleccionada == 3)
			{
				ejecutarConfirmarProducto();
				continuar = false;
				System.out.println("Producto agregado al pedido");
			}
			else
				System.out.println("Por favor ingrese una opción válida.");
		}
	}

	/**
     * Ejecutar opciones de agregar un producto al pedido
     */
	private void ejecutarAgregarProductoBaseAlPedido()
	{
		String nombreProductoAgregar = input("Ingresa el ID del producto que deseas agregar al pedido: ");
		restaurante.agregarProductoBase(nombreProductoAgregar);
	}
	
	private void ejecutarAdicionarIngredienteAlProducto()
	{
		String nombreIngrediente = input("Ingresa el ID del ingrediente a adicionar: ");
		restaurante.adicionarIngredienteAlPedido(nombreIngrediente);
	}
	
	private void ejecutarEliminarIngredienteDelProducto()
	{
		String nombreIngrediente = input("Ingresa el ID del ingrediente a eliminar: ");
		restaurante.eliminarIngredienteDelPedido(nombreIngrediente);
	}

	private void ejecutarAgregarBebidaAlPedido()
	{
		String nombreBebida = input("Ingresa el ID de la bebida: ");
		restaurante.agregarBebidaAlPedido(nombreBebida);
	}
	
	private void ejecutarAgregarComboAlPedido()
	{
		String nombreCombo = input("Ingresa el ID del combo: ");
		restaurante.agregarComboAlPedido(nombreCombo);
 
	}
	
	private void ejecutarConfirmarProducto()
	{
		restaurante.confirmarProducto();
	}

	/**
     * Ejecutar cargar la información del restaurante
     */
	private void cargarInfo()
	{
		String archivoIngredientes = "data/ingredientes_modificacion.txt";
		String archivoMenu = "data/menu_modificacion.txt";
		String archivoCombos = "data/combos.txt";
		String archivoBebidas = "data/bebidas.txt";

		restaurante = new RestauranteM();
		restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos, archivoBebidas);
	}

	/**
     * Iniciar aplicación
     */
	public static void main(String[] args)
	{
		App_Modificada consola = new App_Modificada();
		consola.ejecutarMenu();
	}

}
