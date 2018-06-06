package staffUI;

import Utils.Data;
import app.BookingSystem;
import bean.Table;
import dao.TableDAO;
import sun.java2d.pipe.hw.AccelDeviceEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AddWindow extends initWindow implements ActionListener {
    public String warn;
    public AddWindow(String s){
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
        //预处理
        if(!doFore()){
            JOptionPane.showMessageDialog(this,warn,"提醒",JOptionPane.WARNING_MESSAGE);
            return ;
        }
        Data.newReservation=initInformation();
        Data.addReservation();
        Table t=new TableDAO().get(Integer.parseInt(tbj.getSelectedItem().toString()));
        t.setStatus_("cover");
        new TableDAO().update(t);
        Data.setresGetob();
        BookingSystem.notifyObservers();
        dispose();
    }
    public boolean doFore(){
        warn="";
        warn+="您没有填写:";
        boolean flag=true;
        if(customer.getText().isEmpty()){
            flag=false;
            warn+="顾客姓名 ";
        }
        if(phoneNumber.getText().isEmpty()){
            flag=false;
            warn+="顾客电话 ";
        }
        if(tbj.getSelectedItem().toString().isEmpty()){
            flag=false;
            warn+="餐桌编号 ";
        }
        for(int i=0;i<6;i++)
            if(jcb[i].getSelectedItem().toString().isEmpty()) {
                warn += "日期 ";
                flag=false;
                break;
            }
        return flag;
    }

    public void update(){

    }
}
