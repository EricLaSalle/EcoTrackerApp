package org.example.ecotrackerapp.model;

public class Categoria {
    // Atributs
    private int id;
    private String nomCategoria;
    private String descripcio;
    private double co2UnitariEstalviat;

    // Constructor
    public Categoria(int id, String nomCategoria, String descripcio, double co2UnitariEstalviat) {
        this.id = id;
        this.nomCategoria = nomCategoria;
        this.descripcio = descripcio;
        this.co2UnitariEstalviat = co2UnitariEstalviat;
    }

    // Getters i Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNomCategoria() {
        return nomCategoria;
    }
    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    public double getCo2UnitariEstalviat() {
        return co2UnitariEstalviat;
    }
    public void setCo2UnitariEstalviat(double co2UnitariEstalviat) {
        this.co2UnitariEstalviat = co2UnitariEstalviat;
    }
}