package staffUI;

import Utils.Data;
import app.BookingSystem;
import app.Observer;
import org.omg.CORBA.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class DeleteWindow extends initWindow implements ActionListener {
    public JTable jTable;
    public JPanel jSouth;
    public JButton searchButton=new JButton("搜索");
    public JButton deleteButton=new JButton("删除");
    public DeleteWindow(String s){
        super();
        setTitle(s);
        initTbj();
        Data.resDeob=Data.emptyOb;
        jTable=new JTable(Data.resDeob,Data.resColu);
        jCenter.add(new JScrollPane(jTable));
        jSouth=new JPanel();
        searchButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jSouth.add(searchButton);
        jSouth.add(deleteButton);
        jSouth.add(cancelButton);
        add(baseBox,BorderLayout.NORTH);
        add(jCenter,BorderLayout.CENTER);
        add(jSouth,BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("搜索")) {
            BookingSystem.notifyObservers();
        }else{
            //删除
            int []deRows=jTable.getSelectedRows();
            if(deRows.length==0 || Data.resDeob==Data.emptyOb){
                JOptionPane.showMessageDialog(this,"没有选中要删除的行","提醒",JOptionPane.WARNING_MESSAGE);
                return ;
            }
            int []deReId=new int[deRows.length];
            for(int i=0;i<deRows.length;i++)
                deReId[i]=(int)jTable.getValueAt(deRows[i],0);
            Data.deleteReservations(deReId);
            BookingSystem.removeObserver(this);
            Data.setresGetob();   //确保MainWindow显示出所有的预约
            BookingSystem.notifyObservers();
            dispose();
        }
    }


    public void update(){
        jCenter.removeAll();
        Data.deleteReservation=initInformation();
        Data.setResDeob();
        jTable=new JTable(
                Data.resDeob,Data.resColu);
        jCenter.add(new JScrollPane(jTable));
        setVisible(true);
        pack();
    }
}
