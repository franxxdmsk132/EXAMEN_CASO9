/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPlatos;
import Modelo.Platos;
import Modelo.ConectPG;
import Vista.Vista;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Usuario
 */
public class ControladorPlatos {

    private ModeloPlatos modelo;
    private Vista vista;

    public ControladorPlatos(ModeloPlatos modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        cargaPlatos();
    }

//    public ControladorPlatos(ModeloPlatos modelo, Vista vista) {
//        this.modelo = modelo;
//        this.vista = vista;
//        vista.setVisible(true);
//        cargaPlatos();
//    }
    private void cargaPlatos() {
        //Control para consultar a mi modelo o base de datos 
        //Y luego en la vista.
        List<Platos> listap = modelo.listaPlatos();
        DefaultTableModel mTabla;
        mTabla = (DefaultTableModel) vista.getTbPersona().getModel();
        mTabla.setNumRows(0);//Limpio la tabla 
        listap.stream().forEach(pe -> {
            Object[] filanueva = {pe.getCodigo(), pe.getNombres(), pe.getTipo(), pe.getIngredientes(), String.valueOf(pe.getCosto()), String.valueOf(pe.getPvp()),};
            mTabla.addRow(filanueva);

        });
    }
//    public void iniciaControl(){
//        vista.getBtnActualizar().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//    }

    public void iniciaControl() {
        vista.getBtnActualizar().addActionListener(l -> cargaPlatos());
        vista.getBtnCrear().addActionListener(l -> abrirDialogo(1));
//        vista.getBtnEditar().addActionListener(l-> abrirDialogo(2));
        vista.getBtnEditar().addActionListener(l -> abrirYCargarDatosEnElDialog());
        vista.getBtnAceptar().addActionListener(l -> crearEditarPersona());
        vista.getBtnEliminar().addActionListener(l -> eliminarPersona());
        vista.getBtnImprimir().addActionListener(l -> imprimirPersona());
        buscarPersona();
        cargaPlatos();

    }

    /*public void reportePersonas(){

      
        ConectPG cpg = new ConectPG();//conexion base de datis
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/ReportePersonas.jasper"));
                    JasperPrint jp= JasperFillManager.fillReport(jr,null,cpg.getCon());
        JasperViewer jv = new JasperViewer(jp,false);
        jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControladorPlatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/
    public void imprimirPersona() {

        ConectPG cpg = new ConectPG();//Instanciar la conexion con esto abrimos la conexion a la BD
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/platos.jasper"));

            //Hacer una vista previa
            JasperPrint jp = JasperFillManager.fillReport(jr, null, cpg.getCon());//JasperFillManager.fillReport: Carga los datos de la BD.//JasperPrint: Hace la impresion del reporte. Puede ir 'null' si en el jasper no existen parametros caso contrario se envian los parametros necesarios
            //vista.getjTextFieldTituloParametro().getText()
            //Double.parseDouble(vista.getjSpinnerSuledoMaximo().getValue().toString())
//            Double.parseDouble(vista.getjSpinnerSuldoMinimo().getValue().toString())

            // JasperPrint jp = JasperFillManager.fillReport(jr,  cpg.getCon());//'parametros' es el Map recien creado que contiene los parametros que iran al jasper
            JasperViewer jv = new JasperViewer(jp, false); //Se pasa false para que no se cierre el sistema 
            jv.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ControladorPlatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarPersona() {

        int fila = vista.getTbPersona().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una fila");
        } else {

            int response = JOptionPane.showConfirmDialog(vista, "Seguro que desea eliminar esta información?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {

                String cedula;
                cedula = vista.getTbPersona().getValueAt(fila, 0).toString();

                if (modelo.eliminarPersona(cedula)) {
                    JOptionPane.showMessageDialog(null, "El Plato fue eliminada exitosamente");
                    cargaPlatos();//Actualizo la tabla con los datos
                } else {
                    JOptionPane.showMessageDialog(null, "Error: El Plato no se pudo eliminar");
                }
            }
        }

    }

    public void abrirYCargarDatosEnElDialog() {

        int seleccion = vista.getTbPersona().getSelectedRow();

        if (seleccion == -1) {
            JOptionPane.showMessageDialog(null, "Aun no ha seleccionado una fila");
        } else {

            String codigo = vista.getTbPersona().getValueAt(seleccion, 0).toString();
            modelo.listaPlatos().forEach((pe) -> {
                if (pe.getCodigo().equals(codigo)) {

                    //Abre el jDialog y carga los datos en el jDialog
                    vista.getjDialogPlatos().setName("Editar");
                    vista.getjDialogPlatos().setLocationRelativeTo(vista);
                    vista.getjDialogPlatos().setSize(1100, 500);
                    vista.getjDialogPlatos().setTitle("Editar");
                    vista.getjDialogPlatos().setVisible(true);

                    vista.getTxtCodigo().setText(pe.getCodigo());
                    vista.getTxtNombre().setText(pe.getNombres());
                    vista.getTxttipo().setText(pe.getTipo());
                    vista.getTxtIngredientes().setText(pe.getIngredientes());
                    vista.getTxtCosto().setText(String.valueOf(pe.getCosto()));
                    vista.getTxtPVP().setText(String.valueOf(pe.getPvp()));

                }
            });
        }
    }

    private void abrirDialogo(int ce) {
        String title;
        if (ce == 1) {
            title = "Crear nuevo Plato";
            vista.getjDialogPlatos().setName("crear");
        } else {

            title = "Editar";
            vista.getjDialogPlatos().setName("editar");
        }

        vista.getjDialogPlatos().setLocationRelativeTo(vista);
        vista.getjDialogPlatos().setSize(816, 512);
        vista.getjDialogPlatos().setTitle(title);
        vista.getjDialogPlatos().setVisible(true);
    }

    public void buscarPersona() {

        KeyListener eventoTeclado = new KeyListener() {//Crear un objeto de tipo keyListener(Es una interface) por lo tanto se debe implementar sus metodos abstractos

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                List<Platos> listap = modelo.buscarPlatos(vista.getTxtBuscar().getText());
                DefaultTableModel mTabla;
                mTabla = (DefaultTableModel) vista.getTbPersona().getModel();
                mTabla.setNumRows(0);//Limpio la tabla 
                listap.stream().forEach(pe -> {
                    Object[] filanueva = {pe.getCodigo(), pe.getNombres(), pe.getNombres(), pe.getTipo(), String.valueOf(pe.getCosto()), String.valueOf(pe.getPvp())};
                    mTabla.addRow(filanueva);

                });

            }
        };

        vista.getTxtBuscar().addKeyListener(eventoTeclado); //"addKeyListener" es un metodo que se le tiene que pasar como argumento un objeto de tipo keyListener 
    }

    private void crearEditarPersona() {
        if ("crear".equals(vista.getjDialogPlatos().getName())) {
            //INSERTAR
            String codigo = vista.getTxtCodigo().getText();
            String nombres = vista.getTxtNombre().getText();
            String tipo = vista.getTxttipo().getText();
            String Ingredientes = vista.getTxtIngredientes().getText();
            double costo = Double.parseDouble(vista.getTxtCosto().getText());
            double pvp = Double.parseDouble(vista.getTxtPVP().getText());

            ModeloPlatos platos = new ModeloPlatos();
            platos.setCodigo(codigo);
            platos.setNombres(nombres);
            platos.setTipo(tipo);
            platos.setIngredientes(Ingredientes);
            platos.setCosto(costo);
            platos.setPvp(pvp);

            if (platos.crearPersona() == null) {
                vista.getjDialogPlatos().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Platp Creada Satisfactoriamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo crear el Plato");
            }

        } else {
            String codigo = vista.getTxtCodigo().getText();
            String nombres = vista.getTxtNombre().getText();
            String tipo = vista.getTxttipo().getText();
            String Ingredientes = vista.getTxtIngredientes().getText();
            double costo = Double.parseDouble(vista.getTxtCosto().getText());
            double pvp = Double.parseDouble(vista.getTxtPVP().getText());

            ModeloPlatos platos = new ModeloPlatos();
            platos.setCodigo(codigo);
            platos.setNombres(nombres);
            platos.setTipo(tipo);
            platos.setIngredientes(Ingredientes);
            platos.setCosto(costo);
            platos.setPvp(pvp);

            if (platos.modificarPersona()) {
                vista.getjDialogPlatos().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Plato Modificada Satisfactoriamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo modificar el Platp");
            }
        }
//else hacemos el editar
        //EDITAR

        cargaPlatos(); //Actualizo la tabla con los datos

    }
}
