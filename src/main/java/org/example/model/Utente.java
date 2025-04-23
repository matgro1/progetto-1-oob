package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Utente {

    private String login;
    private String password;
    private ArrayList<Bacheca> bacheche = new ArrayList<Bacheca>();
    private boolean successoAccesso = false;

    public Utente(String loginIniz, String passwordIniz) {
        login = loginIniz;
        password = passwordIniz;
    }

    public void accesso(String loginM, String passwordM) {
        if (login.equals(loginM) && password.equals(passwordM)) {
            successoAccesso = true;
        }
    }

    public void logout() {
        successoAccesso = false;
    }

    public void creaBacheca(String nome, String desc) {
        if (successoAccesso) {
            bacheche.add(new Bacheca(nome, desc));
        }
    }

    public void eliminaBacheca(String nome) {
        if (successoAccesso) {
            if (!bacheche.isEmpty()) {
                for (Bacheca b : bacheche) {
                    if (b.getNome().equals(nome)) {
                        bacheche.remove(b);
                        break;
                    }
                }
            }
        }
    }


    public void cancellaToDo(String nomeB, String nome, LocalDate dataScadenza) {
        if (!bacheche.isEmpty()) {
            for (Bacheca b : bacheche) {
                if (b.getNome().equals(nomeB)) {
                    b.removeToDo(nome, dataScadenza);
                    break;
                }
            }
        }
    }

    public void aggiungiChecklistItem(String nomeB, String nomeToDo, ChecklistItem item) {
        for (Bacheca b : bacheche) {
            if (b.getNome().equals(nomeB)) {
                for (ToDo td : b.getToDo()) {
                    if (td.getTitolo().equals(nomeToDo)) {
                        td.aggiungiChecklistItem(item);
                        td.verificaChecklist(); // Verifica se la checklist è completata
                    }
                }
            }
        }
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Bacheca> getBacheche() {
        return bacheche;
    }

    public boolean isAccesso() {
        return successoAccesso;
    }
}
