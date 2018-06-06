package actionListener;

import app.BookingSystem;
import staffUI.UpdateWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResbuttonUpdateListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        BookingSystem.addObserver(new UpdateWindow(e.getActionCommand()));
    }
}
