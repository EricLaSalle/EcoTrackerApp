# Documentació  

## Document Tècnica del Projecte  

### Descripció General del Projecte  

_Eco Tracker App és una aplicació desenvolupada per ajudar les empreses a portar_  
els comptes de quilograms de CO2 estalviats, amb una senzilla, interactiva i  
completa interfície capaç que l’usuari pugui guiar-se per ella fàcilment i sense gran  
coneixement d’informàtica.

En aquesta aplicació, l’usuari podrà veure el llistat de dades inserides d’una manera
senzilla i visual, on, a més a més, s’ha afegit la funcionalitat extra de veure les
dades amb un gràfic, gràcies al qual l’usuari de l’empresa podrà valorar quan i com
s’han fet millor les coses per tal de replicar-les, i veient si hi ha algun patró durant el
temps.

A més a més, es podran afegir activitats sostenibles a la base de dades de manera
senzilla, on tothom sabrà què i com fer-ho gràcies a una interfície que canvia
depenent de la categoria seleccionada per ajustar-se a aquesta (per exemple, el
valor de la quantitat va variant depenent de si la categoria seleccionada és Energia la quantitat és _Temps (hores) -, o si per exemple és_ _Transport - en aquest cas la_
quantitat és _Distància (km) -). Les activitats també podran ser afegides des d’un_
arxiu de l’ordinador, en un panell on només hauràs de posar la ruta a aquell arxiu i,
en cas d’estar la ruta correcta i l’arxiu ser acceptable, s’inseriran les dades sense
més dificultat.

També existeixen les opcions de modificar i eliminar dades, on des d’un buscador
molt senzill d’identificadors, trobaràs, si existeix a la base de dades, l’element cercat,
el qual podràs eliminar o modificar al teu gust, sempre actualitzant-se
automàticament el valor del CO2 per tal que l’usuari tingui una experiència ràpida i
eficient.

Per acabar, el programa està replet de controls d’errors que pugui fer l’usuari o de
coses que podan afectar l’aplicació, per tant, busquem que l’usuari no s’hagi de
preocupar d’insercions mal fetes o eliminacions indesitjades, ja que només volem
que tingui un ús de l’aplicació ràpid, senzill i còmode.


### Estructura del Projecte
#### 1. Estructura General  
El projecte segueix una arquitectura **Model-Vista-Controlador (MVC)**, organitzat en:  
- **`src/main/java`**: Codi font Java.  
  - **`controllers`**: Controladors de les vistes (lògica d'interacció).  
  - **`model`**: Classes de domini i gestió de dades (ex: `Activitat`, `DatabaseManager`).  
- **`resources`**: Recursos gràfics (FXML, CSS, imatges).  

---

#### 2. Classes Clau i les seves Responsabilitats  

##### **Model (`model`)**  
- **`Activitat`**:  
  - Representa una activitat sostenible (atributs: ID, nom, descripció, data, impacte ambiental, etc.).  
- **`DatabaseManager`** (implícit):  
  - Gestiona la connexió amb la base de dades i operacions CRUD (*Create, Read, Update, Delete*).  

##### **Controladors (`controllers`)**  
- **`LlistaActivitatsController`**:  
  - Mostra la llista d'activitats utilitzant dades del model.  
  - Es comunica amb `DatabaseManager` per carregar/actualitzar la llista.  
- **`AfegirModificarActivitatController`**:  
  - Afegeix noves activitats o modifica les existents.  
  - Crea/actualitza objectes `Activitat` i els persisteix mitjançant `DatabaseManager`.  
- **`EliminarModificarActivitatController`**:  
  - Elimina activitats o inicia la modificació (redirigeix a `AfegirModificarActivitatController`).  
- **`AfegirDadesDesdeArxiuController`**:  
  - Importa dades des d'arxius externs (ex: CSV) i els desa a la base de dades mitjançant `DatabaseManager`.  
- **`GraficController`**:  
  - Genera gràfics (ex: impacte ambiental) basats en dades del model.  
- **`IniciiMenuController`** i **`MenuDadesManagerController`**:  
  - Gestionen la navegació entre vistes (ex: obrir `llistat_activitats.fxml` o `afegir_dades_desde_arxiu.fxml`).  

##### **Vistes (`resources`)**  
- Arxius **FXML** (ex: `llistat_activitats.fxml`, `afegir_modificar_activitat.fxml`):  
  - Definició de la interfície gràfica, vinculada als seus controladors mitjançant `fx:controller`.  
- **CSS** (ex: `stylemenu.css`):  
  - Estils visuals per a les vistes.  

---

#### 3. Relacions Principals  

1. **Controladors ↔ Vistes (FXML)**:  
   - Cada controlador s'associa a una vista FXML per gestionar esdeveniments (ex: clics de botons).  
   - Exemple: `LlistaActivitatsController` actualitza `llistat_activitats.fxml` en eliminar una activitat.  

2. **Controladors ↔ Model**:  
   - Els controladors utilitzen classes del model per manipular dades.  
   - Exemple:  
     ```java  
     // A AfegirModificarActivitatController
     Activitat novaActivitat = new Activitat(nom, descripcio, data);
     DatabaseManager.saveActivitat(novaActivitat);
     ```  

3. **Model ↔ Base de Dades**:  
   - `DatabaseManager` executa consultes SQL/NoSQL per persistir objectes `Activitat`.  

4. **Navegació entre Vistes**:  
   - Els controladors de menú (`IniciiMenuController`, `MenuDadesManagerController`) carreguen noves vistes (ex: `grafic.fxml`).  

---

#### 4. Flux de Funcionalitats  

- **Afegir Activitat**:  
  `MenuDadesManagerController` → `afegir_modificar_activitat.fxml` → `AfegirModificarActivitatController` → `DatabaseManager`.  

- **Importar des d'Arxiu**:  
  `AfegirDadesDesdeArxiuController` → Llegeix arxiu → Valida dades → `DatabaseManager`.  

- **Eliminar/Modificar**:  
  `LlistaActivitatsController` → Invoca `EliminarModificarActivitatController` → Actualitza la base de dades.  

- **Visualitzar Gràfics**:  
  `GraficController` → Consulta dades → Genera gràfic amb llibreries (ex: JavaFX Charts).  

---

#### Diagrama de Relacions Simplificat
Vistes (FXML) → Controladors ↔ Model (Activitat, DatabaseManager) ↔ Base de Dades


### Esquema de la Base de Dades
![DiagramaE-R drawio](https://github.com/user-attachments/assets/d8fbfe3b-db7a-4f54-a916-695ee4447755)
### Diagrama d'Activitat
![DiagramaActividad drawio](https://github.com/user-attachments/assets/89abbbc7-c75f-4d11-b984-e19c1fb3c6ba)


**Per exportar la base de dades i els arxius .csv de prova pots trovar-los dins del repositori a la carpeta BBDD**


### Càlcul del CO₂  

El càlcul del CO₂ hem decidit fer-lo de la següent manera:  

Amb l’ajuda de la intel·ligència artificial, hem escollit els valors més adequats de CO₂  
estalviat per unitat de cada categoria:  

| CATEGORIA      | CO₂ ESTALVIAT PER UNITAT |  
|----------------|--------------------------|  
| Transport      | 0.2                      |  
| Energia        | 0.9                      |  
| Alimentació    | 0.7                      |  
| Residus        | 0.6                      |  
| Altres         | 0.4                      |  

Aquestes 5 categories són fixes, fent que l’usuari tingui un senzill i fàcil manegament  
de la situació sense haver de tenir gaires coneixements. Amb aquests valors, fem el  
càlcul del CO₂ total estalviat dins de cada categoria, multiplicant la variable quantitat  
pel CO₂ estalviat per unitat de la categoria a la qual pertany.  

La unitat de la quantitat depèn de la categoria, i això l’usuari ho sap, ja que cada cop  
que vulgui inserir o actualitzar una dada, l’espai de la quantitat anirà variant  
depenent de si escull una categoria o una altra.  

| CATEGORIA      | UNITAT DE QUANTITAT |  
|----------------|---------------------|  
| Transport      | Quilòmetres         |  
| Energia        | Hores               |  
| Alimentació    | Quilograms          |  
| Residus        | Quilograms          |  
| Altres         | No definit          |  

Per tant, el càlcul del CO₂ estalviat és completament automàtic, l’usuari només  
s’haurà de preocupar d’introduir la quantitat segons la categoria, i el càlcul es farà  
sol, multiplicant la quantitat unitària d’estalvi de CO₂ per la quantitat d’hores,  
quilòmetres o quilograms especificada per l’usuari.  

### Implementació Tècnica  

El mètode que s’encarrega d’això dins de la classe ActivitatsSostenibles és el següent:  

```java
// Mètode per calcular el CO2 estalviat  
private double calcu1Co2Estalviat(Categoria categoria) {  
    switch (categoria.getNomCategoria().toUpperCase()) {  
        case "TRANSPORT":  
            return (0.2 * quantitat);  
        case "ENERGIA":  
            return (0.9 * quantitat);  
        case "ALIMENTACIO":  
            return (0.7 * quantitat);  
        case "RESIDUS":  
            return (0.6 * quantitat);  
        default:  
            return 0.4 * quantitat;  
    }  
}
````

## Manual d'Usuari  

### Instruccions d'Instal·lació i Execució  

Un cop descarregada l'aplicació del repositori i la documentació del pou, aquests són els passos a seguir per a executar i fer servir l'aplicació sense problema:  

1. **Creació de la base de dades:**  
   - Obre l'arxiu dins de la carpeta `_BBDD`, anomenat `BBDD.sql`.  
   - Modifica les línies indicades amb "Inserir aquí la ruta del fitxer categoria.csv" i "Inserir aquí la ruta del fitxer activitatssostenibles.csv".  
   - Copia i enganxa la ruta absoluta dels arxius trobats a la carpeta `_Dades/DadesInicials`.  
   - Si treballes en Windows, canvia les `\` per `/` a les rutes.  
   - Executa el contingut del document al teu servidor per crear la base de dades i inserir dades inicials.  

2. **Configuració de l'aplicació:**  
   - Verifica les dades de connexió al servidor (URL, usuari i contrasenya) al document `GestorBbDd.java` dins de la carpeta `_src/main/java/org/example/ecotrackerapp/models`.  
   - Assegura't que tens instal·lades totes les dependències necessàries:  
     - Llibreria Maven de JavaFX.  
     - Connector Java-MySQL (preferiblement el de Maven).  
     - Tester Mockito.  

---

### Explicació Pas a Pas de Com Utilitzar l’Aplicació  

1. **Execució inicial:**  
   - Executa el `Main.java`.  
   - Prem el botó per connectar amb la base de dades. Si hi ha errors, revisa la configuració de `GestorBbDd` o el connector MySQL.  

2. **Llistat d’Activitats Sostenibles:**  
   - Es mostra una taula amb totes les activitats inserides. Si no n'hi ha, verifica:  
     - Que s'han inserit dades correctament a la base de dades.  
     - Que la connexió amb la base de dades està activa i ben configurada.  

3. **Afegir Activitat Manualment:**  
   - Selecciona l'opció "Afegir Activitat" al menú.  
   - Omple tots els camps obligatoris. El càlcul de CO₂ es fa automàticament.  
   - Prem "Guardar" per confirmar o "Cancel·lar" per descartar.  

4. **Afegir Activitat des d’Arxiu:**  
   - Selecciona l'opció "Afegir des d’Arxiu".  
   - Introdueix la ruta absoluta de l'arxiu CSV amb les dades.  
   - L'aplicació validarà el format i la ruta abans de inserir les dades.  

5. **Eliminar o Modificar Activitat:**  
   - Selecciona l'opció "Eliminar/Modificar".  
   - Introdueix l'ID de l'activitat i selecciona l'acció desitjada.  
   - Per a modificacions, els camps es mostraran amb els valors actuals per editar-los.  

Amb això ja pots utilitzar l'aplicació de manera eficient. Gaudeix de l'experiència!  


## Resum del Testing  

### Test Case 1 (TC01): Consultar gràfica de CO2 estalviat  
- **Objectiu:** Verificar que es pot consultar correctament la gràfica de CO₂ estalviat.  
- **Resultat:** Tots els passos es van completar amb èxit (Pass).  
- **Tester:** Raul  
- **Data:** 20/05/2025  

---

### Test Case 2 (TC02): Afegir activitat sostenible  
- **Objectiu:** Afegir una activitat sostenible correctament.  
- **Dades de prova:**  
  - Nom de l'activitat: "Menjar Tofu"  
  - Categoria: "Alimentació"  
- **Resultat:** Tots els passos es van completar amb èxit (Pass).  
- **Tester:** Aleix  
- **Data:** 20/05/2025  

---

### Test Case 3 (TC03): Afegir dades amb arxiu CSV  
- **Objectiu:** Afegir dades externes mitjançant un arxiu CSV.  
- **Prerequisits:** Tenir un arxiu CSV amb dades compatibles.  
- **Resultat:** Tots els passos es van completar amb èxit (Pass).  
- **Tester:** Aleix  
- **Data:** 20/05/2025  

---

### Test Case 4 (TC04): Eliminar activitat sostenible  
- **Objectiu:** Eliminar una activitat sostenible correctament.  
- **Dades de prova:**  
  - ID: 45  
  - Tipus: "eliminar"  
- **Resultat:** Tots els passos es van completar amb èxit (Pass).  
- **Tester:** Raul  
- **Data:** 20/05/2025  

---

### Conclusió  
Tots els casos de prova es van executar amb èxit (Pass), verificant les funcionalitats clau del sistema, incloent:  
- Consulta de gràfiques.  
- Addició i eliminació d'activitats.  
- Importació de dades des d'un arxiu CSV.  

Els testers **Raul** i **Aleix** van completar les proves el **20/05/2025** sense trobar errors.

### Enllaç: https://docs.google.com/spreadsheets/d/1Fusw4FJXLbf5FSt3fTccSEtuSUcpt1WF/edit?usp=sharing&ouid=108173395315781493335&rtpof=true&sd=true
