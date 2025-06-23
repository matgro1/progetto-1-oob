package org.example.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Utente {

    // Getters
    @Getter
    private String login;
    @Getter
    private String password;

    private ArrayList<Bacheca> bacheche = new ArrayList<>();
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
        if (successoAccesso &&!bacheche.isEmpty()) {
                for (Bacheca b : bacheche) {
                    if (b.getNome().equals(nome)) {
                        bacheche.remove(b);
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

    public void changeUser(String username, String pss){
        login = username;
        password = pss;
    }
    public List<Bacheca> getBacheche() {
        return bacheche;
    }

}
