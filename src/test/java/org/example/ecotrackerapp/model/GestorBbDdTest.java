package org.example.ecotrackerapp.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.*;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.anyString;

class GestorBbDdTest {

    /**
     * Crea un mock de la connexió a la BBDD
     * @return Connection
     * @throws SQLException
     */
    private Connection crearConnectionMock() throws SQLException {
        Connection connectionMock = Mockito.mock(Connection.class);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);

        Mockito.when(connectionMock.prepareStatement(anyString()))
                .thenReturn(preparedStatementMock);

        return connectionMock;
    }

    /**
     * Test 1: Afegir una activitat a la BBDD - Verifica que s'executa l'INSERT
     */
    @Test
    void testAfegirActivitatBBDD() throws SQLException {
        Connection connectionMock = crearConnectionMock();
        PreparedStatement psMock = connectionMock.prepareStatement(anyString());

        // Crear una activitat de prova
        Categoria categoria = new Categoria(1, "TRANSPORT", "Desplaçament", 0.2);
        ActivitatsSostenibles activitat = new ActivitatsSostenibles("Bici", LocalDate.now(), categoria, "Test", 10);

        // Executar el mètode
        GestorBbDd.afegirActivitataBBDD(activitat, connectionMock);

        // Verificar que s'han configurat els paràmetres correctes
        Mockito.verify(psMock).setString(1, "Bici");
        Mockito.verify(psMock).setDate(2, Date.valueOf(LocalDate.now()));
        Mockito.verify(psMock).setString(3, "TRANSPORT");
        Mockito.verify(psMock).executeUpdate();
    }

    /**
     * Test 2: Eliminar una activitat - Verifica que s'executa el DELETE amb l'ID correcte
     */
    @Test
    void testEliminarActivitatBBDD() throws SQLException {
        Connection connectionMock = crearConnectionMock();
        PreparedStatement psMock = connectionMock.prepareStatement(anyString());

        GestorBbDd.eliminarActivitatBBDD(5, connectionMock);

        Mockito.verify(psMock).setInt(1, 5);
        Mockito.verify(psMock).executeUpdate();
    }

    /**
     * Test 3: Modificar una activitat - Verifica l'UPDATE i l'actualització de la llista
     */
    @Test
    void testModificarActivitat() throws SQLException {
        Connection connectionMock = crearConnectionMock();
        PreparedStatement psMock = connectionMock.prepareStatement(anyString());

        // Crear activitat original
        Categoria categoriaVella = new Categoria(1, "ENERGIA", "Vella", 0.9);
        ActivitatsSostenibles activitat = new ActivitatsSostenibles(1, "Antic", LocalDate.now(), categoriaVella, "Descripció vella", 10, 9.0);

        // Modificar dades
        Categoria categoriaNova = new Categoria(2, "RESIDUS", "Nova", 0.6);
        activitat.setNom("Nou");
        activitat.setCategoria(categoriaNova);

        GestorBbDd.modificarActivitat(activitat, connectionMock);

        // Verificar paràmetres de l'UPDATE
        Mockito.verify(psMock).setString(1, "Nou");
        Mockito.verify(psMock).setString(3, "RESIDUS");
        Mockito.verify(psMock).setInt(7, 1); // ID
    }
}