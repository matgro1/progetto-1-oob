package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bacheca {
    private String titolo;
    private String descrizione;
    private ArrayList<ToDo> todo = new ArrayList<ToDo>();

    public Bacheca(String t, String desc) {
        titolo = t;
        descrizione = desc;
    }

    public void removeToDo(String nome, LocalDate dataScadenza) {
        int i = 0;
        for (ToDo td : todo) {
            if (td.getId().equals(td.getClass().toString() + "-" + dataScadenza.toString() + "-" + nome.replaceAll("\\s+", "_"))) {
                todo.remove(i);
            }
            i++;
        }
    }

    public String getNome() {
        return titolo;
    }

    public void aggiungiToDo(ToDo toDo) {
        todo.add(toDo);
    }

    public ArrayList<ToDo> getToDo() {
        return todo;
    }
}
