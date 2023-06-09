
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectPG {
    Connection con;
    String cadenaConexion="jdbc:postgresql://localhost:5432/platos";
    String usuarioPG= "postgres";
    String passPG="1234";

    public ConectPG() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(cadenaConexion,usuarioPG, passPG);
        } catch (SQLException ex) {
            Logger.getLogger(ConectPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ResultSet consulta(String sql){
        try {
            Statement st =con.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConectPG.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public SQLException accion1(String sql){
        try {
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ConectPG.class.getName()).log(Level.SEVERE, null, ex);
            return ex;
        }
        
    }
    
        public boolean accion(String sql) {
        boolean correcto;
        try {
            Statement st = con.createStatement();
            st.execute(sql);
            st.close();
            correcto = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConectPG.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            correcto = false;
        }

        return correcto;
    }
    
    public Connection getCon(){
        return con;
    }
}
