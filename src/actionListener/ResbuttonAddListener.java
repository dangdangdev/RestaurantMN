package actionListener;

import app.BookingSystem;
import staffUI.AddWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResbuttonAddListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e){
        BookingSystem.addObserver(new AddWindow(e.getActionCommand()));
    }
}
