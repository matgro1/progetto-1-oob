package org.example.controller;

import javax.swing.*;

public class ControllerFather {

    protected JFrame getFrame() {
        return SessionManager.getInstance().getMainFrame();
    }

    protected ControllerFather() {}
}