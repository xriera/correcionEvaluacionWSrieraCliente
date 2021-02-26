package ec.edu.ups.evaluacionws.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.internal.guava.Ticker;

import ec.edu.ups.evaluacionws.modelo.CabeceraDetalle;
import ec.edu.ups.evaluacionws.modelo.Detalles;
import ec.edu.ups.evaluacionws.modelo.Parametros;
import ec.edu.ups.evaluacionws.modelo.Producto;
import ec.edu.ups.evaluacionws.modelo.Respuesta;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaIngreso extends JFrame {
	private String WS_GET_PRODUCTO = "http://localhost:8080/correcionEvaluacionWSriera/rest/cliente/productoid";
	private String WS_SAVE_GUARDAR = "http://localhost:8080/correcionEvaluacionWSriera/rest/cliente/guardar";
	private List<Detalles> listDetalles = new ArrayList<Detalles>();
	private JPanel contentPane;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	private JTextField txtProductoId;
	private JTextField txtNombreProducto;
	private JTextField txtPrecio;
	private JTextField txtCantidad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaIngreso frame = new VentanaIngreso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaIngreso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cedula");
		lblNewLabel.setBounds(16, 46, 61, 16);
		contentPane.add(lblNewLabel);
		
		txtCedula = new JTextField();
		txtCedula.setBounds(111, 41, 130, 26);
		contentPane.add(txtCedula);
		txtCedula.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(16, 84, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(111, 79, 130, 26);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Correo");
		lblNewLabel_2.setBounds(16, 122, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		txtCorreo = new JTextField();
		txtCorreo.setBounds(111, 117, 130, 26);
		contentPane.add(txtCorreo);
		txtCorreo.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Producto id ");
		lblNewLabel_3.setBounds(16, 162, 76, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Nombre ");
		lblNewLabel_4.setBounds(26, 190, 61, 16);
		contentPane.add(lblNewLabel_4);
		
		txtProductoId = new JTextField();
		txtProductoId.setBounds(111, 157, 130, 26);
		contentPane.add(txtProductoId);
		txtProductoId.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Producto producto= new Producto();
					producto.setCodigoProducto(Integer.valueOf(txtProductoId.getText()));
					
					buscarProducto(Integer.valueOf(txtProductoId.getText()));
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "No existe producto ");
				}
						

			}
		});
		btnBuscar.setBounds(255, 157, 117, 29);
		contentPane.add(btnBuscar);
		
		txtNombreProducto = new JTextField();
		txtNombreProducto.setBounds(111, 190, 130, 26);
		contentPane.add(txtNombreProducto);
		txtNombreProducto.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Precio");
		lblNewLabel_5.setBounds(255, 195, 61, 16);
		contentPane.add(lblNewLabel_5);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(302, 190, 67, 26);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Cantidad");
		lblNewLabel_6.setBounds(381, 195, 61, 16);
		contentPane.add(lblNewLabel_6);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(442, 190, 67, 26);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parametros p=new Parametros();
				p.setCedula(txtCedula.getText());
				p.setNombre(txtNombre.getText());
				p.setCorreo(txtCorreo.getText());
				p.setListaDetalles(listDetalles);
				
				
				Client client = ClientBuilder.newClient();		
				WebTarget target = client.target(WS_SAVE_GUARDAR);
				Respuesta respuesta = target.request().post(Entity.json(p), Respuesta.class);
				System.out.println(respuesta);
				JOptionPane.showMessageDialog(null, respuesta.getMensaje());
			}
		});
		btnGuardar.setBounds(209, 257, 117, 29);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lista();
			}
		});
		btnNewButton.setBounds(509, 185, 93, 29);
		contentPane.add(btnNewButton);
	}

	protected Producto buscarProducto(int id) {
		// TODO Auto-generated method stub
		Client client = ClientBuilder.newClient();
				
		WebTarget target = client.target(
				WS_GET_PRODUCTO).queryParam("id", id);

		Producto producto = target.request().get(Producto.class);
		
		client.close();
		txtNombreProducto.setText(producto.getNombreProducto());
		txtPrecio.setText(String.valueOf(producto.getPrecio()));
		
		System.out.println("producto "+producto.getCodigoProducto()+" "+producto.getNombreProducto());
		
		
		return producto;
	}
	
	public void lista() {
		Detalles d =new  Detalles();
		d.setCodigoProducto(Integer.valueOf(txtProductoId.getText()));
		d.setCantidad(Integer.valueOf(txtCantidad.getText()));
		System.out.println("detalles "+d.getCantidad());
		listDetalles.add(d);
		
		txtNombreProducto.setText("");
		txtPrecio.setText("");
		txtCantidad.setText("");
	}
	
}
