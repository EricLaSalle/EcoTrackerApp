package org.example.ecotrackerapp.model;

public class Categoria {
    // Atributs
    private int id;
    private NomCategoria nomCategoria;
    private String descripcio;
    private double co2UnitariEstalviat;

    // Constructor
    public Categoria(int id, NomCategoria nomCategoria, String descripcio) {
        this.id = id;
        this.nomCategoria = nomCategoria;
        this.descripcio = descripcio;
    }

    // Getters i Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public NomCategoria getNomCategoria() {
        return nomCategoria;
    }
    public void setNomCategoria(NomCategoria NomCategoria) {
        this.nomCategoria = NomCategoria;
    }
    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
}