package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bacheca {
    private String titolo;
    @Override
    public String toString() {
        return getNome();
    }
    private String descrizione;
    private ArrayList<ToDo> todo = new ArrayList<>();

    public Bacheca(String t, String desc) {
        titolo = t;
        descrizione = desc;
    }

    public String getNome() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void removeToDo(String nome, LocalDate dataScadenza) {
        int i = 0;
        for (ToDo td : todo) {
            if (td.getId().equals(td.getClass() + "-" + dataScadenza.toString() + "-" + nome.replaceAll("\\s+", "_"))) {
                todo.remove(i);
            }
            i++;
        }
    }

    public void aggiungiToDo(ToDo toDo) {
        todo.add(toDo);
    }

    public List<ToDo> getToDo() {
        return todo;
    }
    public String getDescrizione(){
        return descrizione;
    }
}
