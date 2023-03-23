
package Modelo;

import java.sql.Date;

public class Platos {
    String codigo;
    String nombres;
    String tipo;
    String ingredientes;
    String sexo;
    double costo,pvp;

    public Platos() {
    }

    public Platos(String codigo, String nombres, String tipo, String ingredientes, String sexo, double costo, double pvp) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.tipo = tipo;
        this.ingredientes = ingredientes;
        this.sexo = sexo;
        this.costo = costo;
        this.pvp = pvp;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }





}
