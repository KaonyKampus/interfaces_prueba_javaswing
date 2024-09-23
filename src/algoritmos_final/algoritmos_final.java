package algoritmos_final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Clase Principal
public class algoritmos_final {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}

// Ventana Principal
class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Sistema de Inventario");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(crearMenuBar());
        setVisible(true);
    }

    private JMenuBar crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuEmpleados = new JMenu("Empleados");
        JMenuItem itemRegistroEmpleado = new JMenuItem("Registrar Empleado");
        itemRegistroEmpleado.addActionListener(e -> abrirRegistroEmpleado());
        menuEmpleados.add(itemRegistroEmpleado);
        menuBar.add(menuEmpleados);

        JMenu menuInventario = new JMenu("Inventario");
        JMenuItem itemRegistroProducto = new JMenuItem("Registrar Producto");
        itemRegistroProducto.addActionListener(e -> abrirRegistroProducto());
        menuInventario.add(itemRegistroProducto);
        menuBar.add(menuInventario);

        return menuBar;
    }

    private void abrirRegistroEmpleado() {
        RegistroEmpleados registroEmpleados = new RegistroEmpleados();
        JInternalFrame internalFrame = new JInternalFrame("Registro de Empleados", true, true, true, true);
        internalFrame.add(registroEmpleados);
        internalFrame.pack();
        internalFrame.setVisible(true);
        add(internalFrame);
    }

    private void abrirRegistroProducto() {
        RegistroProductos registroProductos = new RegistroProductos();
        JInternalFrame internalFrame = new JInternalFrame("Registro de Productos", true, true, true, true);
        internalFrame.add(registroProductos);
        internalFrame.pack();
        internalFrame.setVisible(true);
        add(internalFrame);
    }
}

// Clase Empleado
class Empleado {
    private String nombre;
    private String id;
    private int edad;
    private String jornada;
    private int tiempoTrabajando; // en años
    private String beneficios;

    public Empleado(String nombre, String id, int edad, String jornada, int tiempoTrabajando) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.jornada = jornada;
        this.tiempoTrabajando = tiempoTrabajando;
        this.beneficios = calcularBeneficios();
    }

    private String calcularBeneficios() {
        if (tiempoTrabajando < 1) {
            return "15% descuento en tienda, 20% en recreación";
        } else if (tiempoTrabajando <= 5) {
            return "30% descuento en tienda, 30% en recreación";
        } else {
            return "50% descuento en tienda, 60% en recreación";
        }
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public int getEdad() { return edad; }
    public String getJornada() { return jornada; }
    public int getTiempoTrabajando() { return tiempoTrabajando; }
    public String getBeneficios() { return beneficios; }
}

// Clase Producto
class Producto {
    private String nombre;
    private String tipo;
    private int unidades;
    private double valorUnitario;
    private double iva;
    private double valorTotal;

    public Producto(String nombre, String tipo, int unidades, double valorUnitario) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidades = unidades;
        this.valorUnitario = valorUnitario;
        this.iva = calcularIVA();
        this.valorTotal = calcularValorTotal();
    }

    private double calcularIVA() {
        switch (tipo) {
            case "Aseo": return 0.19;
            case "Papelería": return 0.09;
            case "Víveres": return 0.15;
            case "Mascotas": return 0.16;
            case "Otros": return 0.10;
            default: return 0.0;
        }
    }

    private double calcularValorTotal() {
        return unidades * valorUnitario * (1 + iva);
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getUnidades() { return unidades; }
    public double getValorUnitario() { return valorUnitario; }
    public double getIVA() { return iva * 100; } // porcentaje
    public double getValorTotal() { return valorTotal; }
}

// Registro de Empleados
class RegistroEmpleados extends JPanel {
    private JTextField txtNombre, txtID, txtEdad, txtJornada, txtTiempo;
    private JButton btnAgregar;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Empleado> empleados;

    public RegistroEmpleados() {
        empleados = new ArrayList<>();
        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel panel = new JPanel(new GridLayout(6, 2));
        txtNombre = new JTextField();
        txtID = new JTextField();
        txtEdad = new JTextField();
        txtJornada = new JTextField();
        txtTiempo = new JTextField();
        btnAgregar = new JButton("Agregar Empleado");

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("ID:"));
        panel.add(txtID);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Jornada:"));
        panel.add(txtJornada);
        panel.add(new JLabel("Tiempo en Compensar (años):"));
        panel.add(txtTiempo);
        panel.add(btnAgregar);

        add(panel, BorderLayout.NORTH);

        // Tabla
        model = new DefaultTableModel(new String[]{"Nombre", "ID", "Edad", "Jornada", "Tiempo", "Beneficios"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });
    }

    private void agregarEmpleado() {
        String nombre = txtNombre.getText();
        String id = txtID.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String jornada = txtJornada.getText();
        int tiempo = Integer.parseInt(txtTiempo.getText());

        Empleado empleado = new Empleado(nombre, id, edad, jornada, tiempo);
        empleados.add(empleado);
        model.addRow(new Object[]{empleado.getNombre(), empleado.getId(), empleado.getEdad(), empleado.getJornada(), empleado.getTiempoTrabajando(), empleado.getBeneficios()});
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtID.setText("");
        txtEdad.setText("");
        txtJornada.setText("");
        txtTiempo.setText("");
    }
}

// Registro de Productos
class RegistroProductos extends JPanel {
    private JTextField txtNombre, txtTipo, txtUnidades, txtValorUnitario;
    private JButton btnAgregar;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Producto> productos;

    public RegistroProductos() {
        productos = new ArrayList<>();
        setLayout(new BorderLayout());

        // Panel de entrada
        JPanel panel = new JPanel(new GridLayout(5, 2));
        txtNombre = new JTextField();
        txtTipo = new JTextField();
        txtUnidades = new JTextField();
        txtValorUnitario = new JTextField();
        btnAgregar = new JButton("Agregar Producto");

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Tipo:"));
        panel.add(txtTipo);
        panel.add(new JLabel("Unidades:"));
        panel.add(txtUnidades);
        panel.add(new JLabel("Valor Unitario:"));
        panel.add(txtValorUnitario);
        panel.add(btnAgregar);

        add(panel, BorderLayout.NORTH);

        // Tabla
        model = new DefaultTableModel(new String[]{"Nombre", "Tipo", "Unidades", "Valor Unitario", "IVA (%)", "Valor Total"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Acción del botón
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
    }

    private void agregarProducto() {
        String nombre = txtNombre.getText();
        String tipo = txtTipo.getText();
        int unidades = Integer.parseInt(txtUnidades.getText());
        double valorUnitario = Double.parseDouble(txtValorUnitario.getText());

        Producto producto = new Producto(nombre, tipo, unidades, valorUnitario);
        productos.add(producto);
        model.addRow(new Object[]{producto.getNombre(), producto.getTipo(), producto.getUnidades(), producto.getValorUnitario(), producto.getIVA(), producto.getValorTotal()});
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTipo.setText("");
        txtUnidades.setText("");
        txtValorUnitario.setText("");
    }
}
