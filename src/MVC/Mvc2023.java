/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import Controlador.ControladorPlatos;
import Controlador.ControladorMenuPrincipal;
import Modelo.ModeloPlatos;
import Vista.VistaPlatps;
import Vista.VistaPrincipal;

/**
 *

 */
public class Mvc2023 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        VistaPrincipal vista = new VistaPrincipal();
        ControladorMenuPrincipal control = new ControladorMenuPrincipal(vista);
        control.iniciaControl();
        
    }
    
}
