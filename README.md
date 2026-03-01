# Gestore ToDo – Progetto Java GUI

## Autore
**Matteo Grottola** 

**Matricola:** N86005687

## Descrizione del progetto
Il progetto consiste nello sviluppo di un'applicazione per la gestione di attività (*ToDo*) attraverso un software desktop realizzato in **Java** con la libreria grafica **Swing** e garantisce il salvataggio dei dati tramite un database **PostgreSQL**.

Il progetto segue il modello **BCE + DAO**, separando la presentazione, la logica e l'accesso ai dati.

---

## Funzionalità principali

### Autenticazione
- Sistema di Login e Registrazione con credenziali salvate su database.

### Gestione Dinamica Bacheche
- Creazione, modifica e rimozione di bacheche.
- **Eliminazione a cascata:** La rimozione di una bacheca comporta l'eliminazione automatica di tutti i ToDo e dei relativi item della checklist associati.

### Gestione ToDo e Checklist
- **Pianificazione:** Creazione di ToDo con Titolo e Data di Scadenza.
- **Stati Dinamici:** Visualizzazione filtrata automaticamente per *Completati*, *Non Completati* e *Scaduti*.
- **Sotto-attività:** Ogni ToDo può contenere una Checklist; il completamento della checklist aggiorna automaticamente il ToDo come "Completato".

### Condivisione Multi-utente
- Possibilità di condividere un'attività con altri utenti registrati nel sistema.
- **Tracciamento metadati:** Gestione di utente creatore, utente destinatario, ultimo modificatore e data di condivisione.

---

## Diagrammi di Progetto

### Class Diagram

![class diagram.jpg](documentazione/class%20diagram.jpg)

### Sequence Diagram

![sequence diagram .png](documentazione/sequence%20diagram%20.png)

---

## Struttura del Modello Dati
Il modello riflette fedelmente la struttura del database:

- **Utente:** `id` (INT), `login` (TEXT), `password` (TEXT).
- **Bacheca:** `id` (INT), `titolo` (TEXT), `descrizione` (TEXT), `utenteId` (INT).
- **ToDo:** `id` (INT), `titolo` (TEXT), `dataScadenza` (DATE), `completato` (BOOLEAN), `bachecaId` (INT).
- **ChecklistItem:** `id` (INT), `descrizione` (TEXT), `stato` (BOOLEAN), `todoId` (INT).
- **ToDoCondiviso (Eredita da ToDo):** aggiunge `utenteCreatoreId`, `utenteCondivisoId`, `ultimoModificatoreId`, `dataCondivisione`.

---

## Scelte Progettuali

1.  **Gerarchia dei Dati:** Ho progettato il sistema in maniera tale che ogni Bacheca appartenga a un Utente, mentre ogni ToDo sia collegato a una Bacheca. Tale approccio garantisce l'integrità referenziale e rende efficienti le operazioni di pulizia dei dati (cancellazione a cascata).
2.  **Superamento delle Enumerazioni:** Ho scelto di non vincolare i nomi delle bacheche a dei tipi specifici (es. Università, Lavoro). Ogni bacheca è un'istanza dinamica, permettendo all'utente di personalizzare completamente lo spazio di lavoro.
3.  **Generalizzazione (Ereditarietà):** La classe `ToDoCondiviso` eredita da `ToDo`. Questo design consente di gestire i ToDo condivisi come normali ToDo nella UI, estendendoli con i metadati necessari per i permessi e la cronologia.
4.  **Logica degli Stati a Runtime:** Lo stato **"Scaduto"** non è salvato fisicamente nel Database. Viene calcolato dal Controller confrontando la data di scadenza del ToDo con la data odierna. Questo evita ridondanze e garantisce dati sempre aggiornati "online" all'apertura dell'app.