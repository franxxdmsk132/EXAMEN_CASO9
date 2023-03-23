/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.security.interfaces.RSAKey;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ModeloPlatos extends Platos {

    ConectPG conpg = new ConectPG();

    public ModeloPlatos() {
    }

    public ModeloPlatos(String codigo, String nombres, String tipo, String ingredientes, String sexo, double costo, double pvp) {
        super(codigo, nombres, tipo, ingredientes, sexo, costo, pvp);
    }

    public List<Platos> listaPlatos() {
        try {
            //Me retorna un "List" de "persona"
            List<Platos> lista = new ArrayList<>();

            String sql = "select * from platos";
//        String sql = "select idpersona,nombres,apellidos,telefono from persona";

            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Platos persona = new Platos();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                persona.setCodigo(rs.getString("codigo"));
                persona.setNombres(rs.getString("nombres"));
                persona.setTipo(rs.getString("tipo"));
                persona.setIngredientes(rs.getString("ingredientes"));
                persona.setCosto(rs.getDouble("costo"));
                persona.setPvp(rs.getDouble("pvp"));
                
                lista.add(persona); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPlatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public SQLException crearPersona() { //Grabamos la instancia en la BD

        String sql = "INSERT INTO platos (codigo, nombres, tipo, ingredientes, costo, pvp) VALUES ('" + getCodigo()+ "', '" + getNombres() + "', '" + getTipo()+ "', '" + getIngredientes()+ "', '" + getCosto()+ "', '" + getPvp()+ "');";

        ConectPG conpg = new ConectPG();

        SQLException ex = conpg.accion1(sql); //Devuelve un valor de tipo "SQLException". Si devuelve 'null' esta bien, caso contrario me retornara la excepcion.
        return ex;
    }

    public boolean modificarPersona() { //modificamos la instancia en la BD

        String sql = "UPDATE platos SET nombres='" + getNombres() + "', tipo='" + getTipo()+ "', ingredientes='" + getIngredientes()+ "', costo='" + getCosto()+ "', pvp='" + getPvp()+ "';";

        ConectPG conpg = new ConectPG();

        return conpg.accion(sql);
    }

    public boolean eliminarPersona(String codigo) { //eliminar la instancia en la BD. Se pasa como parametro la cedula de la persona que se desea eliminar

        String sql = "DELETE FROM platos WHERE codigo = '" + codigo + "';";

        ConectPG conpg = new ConectPG();

        return conpg.accion(sql);
    }

    public List<Platos> buscarPlatos(String nombre) {
        try {
            //Me retorna un "List" de "persona"
            List<Platos> lista = new ArrayList<>();

            String sql = "Select * from platos where nombres like '" + nombre + "%'";

            //ConectPG conpg = new ConectPG();
            ResultSet rs = conpg.consulta(sql); //La consulta nos devuelve un "ResultSet"
            byte[] bytea;

            //Pasar de "ResultSet" a "List"
            while (rs.next()) {
                //Crear las instancias de las personas
                Platos persona = new Platos();

                //Todo lo que haga en la sentencia define como voy a extraer los datos
                persona.setCodigo(rs.getString("codigo"));
                persona.setNombres(rs.getString("nombres"));
                persona.setTipo(rs.getString("tipo"));
                persona.setIngredientes(rs.getString("ingredientes"));
                persona.setCosto(rs.getDouble("costo"));
                persona.setPvp(rs.getDouble("pvp"));

                // FOTO
                //ytea = rs.getBytes("foto");
                /*if (bytea != null) {

                    try {
                        persona.setImagen(obtenerImagen(bytea));
                    } catch (IOException ex) {
                        Logger.getLogger(ModeloPlatos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
                lista.add(persona); //Agrego los datos a la lista
            }

            //Cierro la conexion a la BD
            rs.close();
            //Retorno la lista
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(ModeloPlatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

//Hacer todos los metodos 
