package org.example.ecotrackerapp.model;

import java.sql.*;
import java.util.ArrayList;

public class GestorBbDd {
    // Llistes de dades
    private static ArrayList<Categoria> LlistaCategories = new ArrayList<>();
    private static ArrayList<ActivitatsSostenibles> LlistaActivitatsSostenibles = new ArrayList<>();

    // Constants per a la connexió a la base de dades
    private static String url;
    private static String user;
    private static String password;
    private static GestorBbDd instance;

    /**
     * Constructor privat per a la classe GestorBbDd.
     */
    private GestorBbDd() throws SQLException {
        url = "jdbc:mysql://localhost:3306/ecotrackerapp";
        user = "root";
        password = "";
        connectarAmbBbDd();
    }

    /**
     * Mètode per obtenir una instància de la classe GestorBbDd SINGLETON.
     * @return Instància de GestorBbDd
     */
    public static GestorBbDd getInstance() throws SQLException {
        if (instance == null) {
            instance = new GestorBbDd();
        }
        return instance;
    }

    /**
     * Mètode per obtenir una connexió a la base de dades.
     */
    private void connectarAmbBbDd() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexió a la base de dades establerta.");
            crearLlistaCategories(connection);
            crearLlistaActivitatsSostenibles(connection);
        }catch (SQLException sqle) {
            System.out.println("Error al connectar a la base de dades: " + sqle.getMessage());
        }
    }

    /**
     * Mètode que exporta a la classe Categoria les dades de la BbDd
     */
    private void crearLlistaCategories(Connection connection) throws SQLException {
        String query = "SELECT * FROM categoria";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String descripcio = resultSet.getString("descripcio");
            double co2UnitariEstalviat = resultSet.getDouble("co2unitariestalviat");
            Categoria categoria = new Categoria(id, nom, descripcio, co2UnitariEstalviat);
            LlistaCategories.add(categoria);
        }

        resultSet.close();
        statement.close();
    }

    /**
     * Mètode que exporta a la classe ActivitatsSostenibles les dades de la BbDd
     */
    private void crearLlistaActivitatsSostenibles(Connection connection) throws SQLException {
        String query = "SELECT * FROM activitatssostenibles";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String data = resultSet.getString("data");
            String nomCategoria = resultSet.getString("nomcategoria");
            String descripcio = resultSet.getString("descripcio");
            double quantitat = resultSet.getDouble("quantitat");
            double co2UnitariEstalviat = resultSet.getDouble("co2totalestalviat");
            //Busquem la categoria associada a l'activitat
            Categoria categoria = null;
            for (Categoria cat : LlistaCategories) {
                if (cat.getNomCategoria().equals(nomCategoria)) {
                    categoria = cat;
                    break;
                }
            }
            ActivitatsSostenibles activitatssostenibles = new ActivitatsSostenibles(id, nom, data, categoria, descripcio, quantitat, co2UnitariEstalviat);
            LlistaActivitatsSostenibles.add(activitatssostenibles);
        }

        resultSet.close();
        statement.close();
    }
}
