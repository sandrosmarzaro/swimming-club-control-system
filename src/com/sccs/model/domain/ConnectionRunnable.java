package com.sccs.model.domain;

import com.sccs.controller.ConnectionControl;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConnectionRunnable implements Runnable {
    
    @FXML
    private Label numberLabel;
    
    public ConnectionRunnable(Label numberLabel) {
        this.numberLabel = numberLabel;
    }
    
    @Override
    public void run() {
        
        final String ip = "34.125.13.172";
        final Integer port = 12345;
        try {
            Socket clientSocket = new Socket(ip, port);
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
            Integer updateNumber = (Integer) input.readObject();
            Platform.runLater(() -> numberLabel.setText(String.format("%05d", updateNumber)));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
