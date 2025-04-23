package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bacheca {
    private String titolo;
    @Override
    public String toString() {
        return getNome();
    }
    private String descrizione;
    private ArrayList<ToDo> todo = new ArrayList<ToDo>();

    public Bacheca(String t, String desc) {
        titolo = t;
        descrizione = desc;
    }

    // Getter e Setter per il titolo e descrizione
    public String getNome() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // Metodo per rimuovere un ToDo dalla bacheca
    public void removeToDo(String nome, LocalDate dataScadenza) {
        int i = 0;
        for (ToDo td : todo) {
            if (td.getId().equals(td.getClass().toString() + "-" + dataScadenza.toString() + "-" + nome.replaceAll("\\s+", "_"))) {
                todo.remove(i);
            }
            i++;
        }
    }

    public void aggiungiToDo(ToDo toDo) {
        todo.add(toDo);
    }

    public ArrayList<ToDo> getToDo() {
        return todo;
    }
}
