package org.example.ecotrackerapp.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ActivitatsSosteniblesTest {

    /**
     * Funció auxiliar per crear un mock de Categoria
     * @param nomCategoria
     * @return Categoria
     */
    private Categoria crearCategoria(String nomCategoria) {
        Categoria categoria = new Categoria(1, nomCategoria, "Descripció", 1.0);
        return categoria;
    }

    /**
     * Test 1: Càlcul de CO2 per categoria "TRANSPORT"
     */
    @Test
    void testCalculCo2_Transport() {
        Categoria transport = crearCategoria("TRANSPORT");
        ActivitatsSostenibles activitat = new ActivitatsSostenibles("Bici al treball", LocalDate.now(), transport, "Desplaçament sostenible", 100);
        assertEquals(20.0, activitat.getCo2TotalEstalviat());
    }

    /**
     * Test 3: Càlcul de CO2 per categoria desconeguda (default)
     */
    @Test
    void testCalculCo2_CategoriaDesconeguda() {
        Categoria desconeguda = crearCategoria("FICTICIA");
        ActivitatsSostenibles activitat = new ActivitatsSostenibles("Activitat Nova", LocalDate.now(), desconeguda, "Sense categoria", 200);
        assertEquals(80.0, activitat.getCo2TotalEstalviat()); // 0.4 * 200 = 80
    }

    /**
     * Test 4: Constructor amb ID i CO2 manual (no s'ha de recalcular)
     */
    @Test
    void testConstructorAmbCo2Manual() {
        Categoria residus = crearCategoria("RESIDUS");
        ActivitatsSostenibles activitat = new ActivitatsSostenibles(1, "Reciclar plàstic", LocalDate.now(), residus, "Reducció de residus", 30, 999
        );
        assertEquals(999.0, activitat.getCo2TotalEstalviat()); // No es calcula automàticament
    }
}