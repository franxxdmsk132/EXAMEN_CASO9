/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPlatos;
import Vista.Vista;
import Vista.VistaPrincipal;

/**
 *
 */
public class ControladorMenuPrincipal {
    VistaPrincipal vistaPrincipal;

    public ControladorMenuPrincipal() {
    }

    public ControladorMenuPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        vistaPrincipal.setVisible(true);
        //vistaPrincipal.setLocation( null);
       
    }
    
    public void iniciaControl(){
        vistaPrincipal.getBtnPersonas().addActionListener(l -> crudPersonas());
        
    }
    
    private void crudPersonas(){
         Vista vista =new Vista();
        ModeloPlatos modelo= new ModeloPlatos();
       //vistaPrincipal.getDspPrincipal().add(vista);
       vista.setVisible(true);
        
        ControladorPlatos control = new ControladorPlatos(modelo, vista);
        control.iniciaControl();
        
    }
}
