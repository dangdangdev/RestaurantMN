package staffUI;

import Utils.Data;
import app.BookingSystem;
import app.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWindow extends initWindow implements ActionListener {

    public GetWindow(String s){
        super();
        setTitle(s);
        initTbj();
        jCenter.add(confirmButton);
        jCenter.add(cancelButton);
        confirmButton.addActionListener(this);
        add(baseBox,BorderLayout.NORTH);
        add(jCenter,BorderLayout.CENTER);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Data.displayReservation=initInformation();
        Data.setResObCondition();
        BookingSystem.notifyObservers();
        dispose();
    }


    public void update(){

    }
}
