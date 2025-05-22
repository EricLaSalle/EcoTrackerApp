package org.example.ecotrackerapp.model;

import java.time.LocalDate;

public class ActivitatsSostenibles {
    // Atributs
    private int id;
    private String nom;
    private LocalDate data;
    private Categoria categoria;
    private String descripcio;
    private double quantitat;
    private double co2TotalEstalviat;

    // Constructor
    public ActivitatsSostenibles(String nom, LocalDate data, Categoria categoria, String descripcio, double quantitat) {
        this.id = 0;
        this.nom = nom;
        this.data = data;
        this.categoria = categoria;
        this.descripcio = descripcio;
        this.quantitat = quantitat;
        this.co2TotalEstalviat = calculCo2Estalviat(categoria);
    }

    // Constructor per a la modificació
    public ActivitatsSostenibles(Integer id, String nom, LocalDate data, Categoria categoria, String descripcio, double quantitat) {
        this.id = id;
        this.nom = nom;
        this.data = data;
        this.categoria = categoria;
        this.descripcio = descripcio;
        this.quantitat = quantitat;
        this.co2TotalEstalviat = calculCo2Estalviat(categoria);
    }

    // Constructor
    public ActivitatsSostenibles(int id, String nom, LocalDate data, Categoria categoria, String descripcio, double quantitat, double co2TotalEstalviat) {
        this.id = id;
        this.nom = nom;
        this.data = data;
        this.categoria = categoria;
        this.descripcio = descripcio;
        this.quantitat = quantitat;
        this.co2TotalEstalviat = co2TotalEstalviat;
    }

    // Getters i Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    public double getQuantitat() {
        return quantitat;
    }
    public void setQuantitat(double quantitat) {
        this.quantitat = quantitat;
    }
    public double getCo2TotalEstalviat() {
        return co2TotalEstalviat;
    }
    public void setCo2TotalEstalviat(double co2Estalviat) {
        this.co2TotalEstalviat = co2TotalEstalviat;
    }

    // Mètode per calcular el CO2 estalviat
    private double calculCo2Estalviat(Categoria categoria) {
        switch (categoria.getNomCategoria().toUpperCase()) {
            case "TRANSPORT": {
                return (0.2 * quantitat);
            }
            case "ENERGIA": {
                return (0.9 * quantitat);
            }
            case "ALIMENTACIO": {
                return (0.7 * quantitat);
            }
            case "RESIDUS": {
                return (0.6 * quantitat);
            }
            default: {
                return 0.4 * quantitat;
            }
        }
    }
}