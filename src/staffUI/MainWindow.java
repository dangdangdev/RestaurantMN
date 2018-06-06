package staffUI;

import Utils.Data;
import actionListener.ResbuttonAddListener;
import actionListener.ResbuttonDeleteListener;
import actionListener.ResbuttonGetListener;
import actionListener.ResbuttonUpdateListener;
import app.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;

public class MainWindow extends JFrame implements Observer {
    JPanel pNorth;
    JPanel pCenter;
    JTable restable;
   // JTable walktable;
    JButton resbuttonGet;
    JButton resbuttonAdd;
    JButton resbuttonDelete;
    JButton resbuttonUpdate;
    /*JButton walkbuttonGet;
    JButton walkbuttonAdd;
    JButton walkbuttonDelete; */
    public MainWindow(String s){
        setTitle(s);
        setBounds(60,80,500,500);
        resbuttonGet=new JButton("显示预约");
        resbuttonAdd=new JButton("增加预约");
        resbuttonUpdate=new JButton("修改预约");
        resbuttonDelete=new JButton("取消预约");
        resbuttonGet.addActionListener(new ResbuttonGetListener());
        resbuttonAdd.addActionListener(new ResbuttonAddListener());
        resbuttonDelete.addActionListener(new ResbuttonDeleteListener());
        resbuttonUpdate.addActionListener(new ResbuttonUpdateListener());
        Data.setresGetob();
        restable=new JTable(Data.resGetob,Data.resColu);
        pNorth=new JPanel();
        pNorth.add(resbuttonGet);
        pNorth.add(resbuttonAdd);
        pNorth.add(resbuttonUpdate);
        pNorth.add(resbuttonDelete);
        pCenter=new JPanel();
        pCenter.add(new JScrollPane(restable));
        add(pNorth,BorderLayout.NORTH);
        add(pCenter,BorderLayout.CENTER);
        setVisible(true);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update(){
        pCenter.removeAll();
        restable=new JTable(
                Data.resGetob,Data.resColu);
        pCenter.add(new JScrollPane(restable));
        setVisible(true);
        pack();
    }
}
