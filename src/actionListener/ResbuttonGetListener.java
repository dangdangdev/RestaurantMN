package actionListener;

import app.BookingSystem;
import staffUI.GetWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResbuttonGetListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
        BookingSystem.addObserver(new GetWindow(e.getActionCommand()));
    }
}
