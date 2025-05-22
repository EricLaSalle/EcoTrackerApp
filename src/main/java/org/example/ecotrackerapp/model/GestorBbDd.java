package org.example.ecotrackerapp.model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestorBbDd {
    // Llistes de dades
    private static ArrayList<Categoria> LlistaCategories = new ArrayList<>();
    private static ArrayList<ActivitatsSostenibles> LlistaActivitatsSostenibles = new ArrayList<>();
    private static double sumaCo2TotalEstalviat;

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

    //Getters i Setters
    public static ArrayList<Categoria> getLlistaCategories() {
        return LlistaCategories;
    }
    public static void setLlistaCategories(ArrayList<Categoria> llistaCategories) {
        LlistaCategories = llistaCategories;
    }
    public static ArrayList<ActivitatsSostenibles> getLlistaActivitatsSostenibles() {
        return LlistaActivitatsSostenibles;
    }
    public static void setLlistaActivitatsSostenibles(ArrayList<ActivitatsSostenibles> llistaActivitatsSostenibles) {
        LlistaActivitatsSostenibles = llistaActivitatsSostenibles;
    }
    public static double getSumaCo2TotalEstalviat() {
        return sumaCo2TotalEstalviat;
    }
    public static void setSumaCo2TotalEstalviat(double sumaCo2TotalEstalviat) {
        GestorBbDd.sumaCo2TotalEstalviat = sumaCo2TotalEstalviat;
    }
    //Get de connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
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
            //Exportem les dades de la BbDd a les llistes
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
    public static void crearLlistaActivitatsSostenibles(Connection connection) throws SQLException {
        //Netejem la llista
        LlistaActivitatsSostenibles.clear();

        //Obtenim les dades de la BbDd
        String query = "SELECT * FROM activitatssostenibles";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            LocalDate data = LocalDate.parse(resultSet.getString("data"));
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

        calcularSumaCo2TotalEstalviat();
    }

    /**
     * Mètode que calcula la suma total de CO2 estalviat
     */
    public static void calcularSumaCo2TotalEstalviat() {
        sumaCo2TotalEstalviat = 0;
        for (ActivitatsSostenibles activitat : LlistaActivitatsSostenibles) {
            sumaCo2TotalEstalviat += activitat.getCo2TotalEstalviat();
        }
    }

    /**
     * Mètode que afegeix una activitat a la base de dades
     * @param activitat
     * @throws SQLException
     */
    public static void afegirActivitataBBDD(ActivitatsSostenibles activitat, Connection connection) throws SQLException {
        String query = "INSERT INTO activitatssostenibles (nom, data, nomcategoria, descripcio, quantitat, co2totalestalviat) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, activitat.getNom());
        preparedStatement.setDate(2, Date.valueOf(activitat.getData()));
        preparedStatement.setString(3, activitat.getCategoria().getNomCategoria());
        preparedStatement.setString(4, activitat.getDescripcio());
        preparedStatement.setDouble(5, activitat.getQuantitat());
        preparedStatement.setDouble(6, activitat.getCo2TotalEstalviat());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Mètode que elimina una activitat de la base de dades
     * @param id
     * @throws SQLException
     */
    public static void eliminarActivitatBBDD(int id, Connection connection) throws SQLException {
        String query = "DELETE FROM activitatssostenibles WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * Mètode que modifica una activitat de la base de dades
     * @param activitat
     * @throws SQLException
     */
    public static void modificarActivitat(ActivitatsSostenibles activitat, Connection connection) throws SQLException {
        String query = "UPDATE activitatssostenibles SET nom = ?, data = ?, nomcategoria = ?, descripcio = ?, quantitat = ?, co2totalestalviat = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, activitat.getNom());
        preparedStatement.setDate(2, Date.valueOf(activitat.getData()));
        preparedStatement.setString(3, activitat.getCategoria().getNomCategoria());
        preparedStatement.setString(4, activitat.getDescripcio());
        preparedStatement.setDouble(5, activitat.getQuantitat());
        preparedStatement.setDouble(6, activitat.getCo2TotalEstalviat());
        preparedStatement.setInt(7, activitat.getId());
        preparedStatement.executeUpdate();


        // Actualitzem la llista d'activitats sostenibles
        for (ActivitatsSostenibles activitatFor : LlistaActivitatsSostenibles) {
            if (activitatFor.getId() == activitat.getId()) {
                activitatFor.setNom(activitat.getNom());
                activitatFor.setData(activitat.getData());
                activitatFor.setCategoria(activitat.getCategoria());
                activitatFor.setDescripcio(activitat.getDescripcio());
                activitatFor.setQuantitat(activitat.getQuantitat());
                activitatFor.setCo2TotalEstalviat(activitat.getCo2TotalEstalviat());
                break;
            }
        }

        preparedStatement.close();
    }

    /**
     * Afegeix activitats a la base de dades des d'un arxiu extern
     * @param rutaArxiu Ruta completa de l'arxiu amb les dades
     * @return true si s'han afegit les dades correctament, false si hi ha hagut algun error
     */
    public static boolean afegirArxiuActivitats(String rutaArxiu, Connection connection) {
        try {
            //Convertim les contrabarres a barres
            rutaArxiu = rutaArxiu.replace("\\", "/");

            // Verificar que l'arxiu existeix abans de començar
            File arxiu = new File(rutaArxiu);
            if (!arxiu.exists() || !arxiu.canRead()) {
                System.err.println("Error: L'arxiu no existeix o no es pot llegir");
                return false;
            }

            // Preparar la consulta
            String query = "LOAD DATA INFILE ?\n" +
                    "INTO TABLE activitatssostenibles\n" +
                    "FIELDS TERMINATED BY ';'\n" +
                    "LINES TERMINATED BY '\\n'\n" +
                    "IGNORE 1 ROWS \n" +
                    "(nom, data, nomcategoria, descripcio, quantitat, co2totalestalviat)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rutaArxiu);

            // Executar la consulta
            int filesAfectades = preparedStatement.executeUpdate();

            preparedStatement.close();

            // Si s'han afectat files, considerem que ha estat un èxit
            return filesAfectades > 0;

        } catch (SQLException e) {
            System.err.println("Error SQL en afegir arxiu d'activitats: " + e.getMessage());
            return false;
        }
    }
}
