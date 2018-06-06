package actionListener;

import app.BookingSystem;
import staffUI.DeleteWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResbuttonDeleteListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        BookingSystem.addObserver(new DeleteWindow(e.getActionCommand()));
    }
}
