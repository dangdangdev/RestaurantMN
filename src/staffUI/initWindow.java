package staffUI;

import Utils.Data;
import app.BookingSystem;
import app.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class initWindow extends JFrame implements Observer {
    public  JTextField customer;
    public  JTextField phoneNumber;
    public  JComboBox []jcb;
    public  JComboBox tbj;   //桌子的下拉列表
    public  Box baseBox,box1,box2;
    public JPanel jCenter;
    JButton confirmButton;
    JButton cancelButton;
    public void initTbj(){
        if(this.getTitle().equals("增加预约")) {
            List<Integer> ins = BookingSystem.getEmptyTables();
            if (ins != null) {
                for (Integer i : ins)
                    tbj.addItem(i.intValue());
            }
        }else{
            List<Integer> ins=BookingSystem.getAllTables();
            if (ins != null) {
                for (Integer i : ins)
                    tbj.addItem(i.intValue());
            }
        }
    }
    public void initJcb1(){
        if(jcb[0].getSelectedItem().toString().isEmpty()) {
            jcb[1].removeAllItems();
            jcb[1].addItem("");
            for(int i=1;i<=31;i++) jcb[1].addItem(i);
            return ;
        }
        int select=Integer.parseInt(jcb[0].getSelectedItem().toString());
        jcb[1].removeAllItems();
        jcb[1].addItem("");
        switch (select){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                for(int i=1;i<=31;i++) jcb[1].addItem(i);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                for(int i=1;i<=30;i++) jcb[1].addItem(i);
                break;
            case 2:
                for(int i=1;i<=28;i++) jcb[1].addItem(i);
                break;
            default:
        }
    }

    public void initJcb(){
        for(int i=1;i<=12;i++) jcb[0].addItem(i);
        jcb[0].addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                initJcb1();
            }
        });
        for(int i=0;i<=23;i++) {
            jcb[2].addItem(i);
            jcb[4].addItem(i);
        }
        for(int i=0;i<=59;i++){
            jcb[3].addItem(i);
            jcb[5].addItem(i);
        }
    }

    public initWindow(){
        setBounds(60,150,188,108);
        customer=new JTextField(10);
        phoneNumber=new JTextField(10);
        jcb=new JComboBox[6];
        for(int i=0;i<6;i++){
            jcb[i]=new JComboBox();
            jcb[i].addItem("");
        }
        tbj=new JComboBox();   //桌子的下拉列表
        tbj.addItem("");
        //initTbj();必须在子类中处理这条语句，因为这里窗口的标题还没有被设置
        initJcb();
        initJcb1();
        box1=Box.createVerticalBox();
        box1.add(Box.createVerticalStrut(5));
        box1.add(new JLabel("顾客姓名"));
        box1.add(Box.createVerticalStrut(30));
        box1.add(new JLabel("顾客电话"));
        box1.add(Box.createVerticalStrut(30));
        box1.add(new JLabel("桌子编号"));
        box1.add(Box.createVerticalStrut(40));
        box1.add(new JLabel("日期"));
        box2=Box.createVerticalBox();
        box2.add(Box.createVerticalStrut(25));
        box2.add(customer);
        box2.add(Box.createVerticalStrut(25));
        box2.add(phoneNumber);
        box2.add(Box.createVerticalStrut(25));
        box2.add(tbj);
        box2.add(Box.createVerticalStrut(25));
        JPanel jDate=new JPanel();
        jDate.add(jcb[0]);
        jDate.add(new JLabel("月"));
        jDate.add(jcb[1]);
        jDate.add(new JLabel("日"));
        jDate.add(jcb[2]);
        jDate.add(new JLabel("H:"));
        jDate.add(jcb[3]);
        jDate.add(new JLabel("M -- "));
        jDate.add(jcb[4]);
        jDate.add(new JLabel("H:"));
        jDate.add(jcb[5]);
        jDate.add(new JLabel("M"));
        box2.add(jDate);
        baseBox=Box.createHorizontalBox();
        baseBox.add(box1);
        baseBox.add(Box.createHorizontalStrut(16));
        baseBox.add(box2);
        baseBox.add(Box.createHorizontalStrut(16));
        confirmButton=new JButton("确认");
        cancelButton=new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jCenter=new JPanel();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
                dispose();
            }
        });
       // jCenter.add(confirmButton);
       // jCenter.add(cancelButton);
      //  add(baseBox,BorderLayout.NORTH);
      //  add(jCenter,BorderLayout.CENTER);
    }
    public void close(){
        BookingSystem.removeObserver(this);
    }
    public Map<String,String> initInformation(){
        Map<String,String> map=new HashMap<>();
        map.put("name",customer.getText());
        map.put("phoneNumber",phoneNumber.getText());
        map.put("table_id",tbj.getSelectedItem().toString());
        String firstDate="2018-";
        String lastDate="2018-";
        String firstTemp="";
        String lastTemp="";
        if(!jcb[0].getSelectedItem().toString().isEmpty()){
            if(jcb[0].getSelectedItem().toString().length()==1){
                firstTemp+="0";
                lastTemp+="0";
            }
            firstTemp+=jcb[0].getSelectedItem().toString();
            lastTemp+=jcb[0].getSelectedItem().toString();
        }else{
            firstTemp+="__";
            lastTemp+="__";
        }
        if(!jcb[1].getSelectedItem().toString().isEmpty()){
            firstTemp+="-";
            lastTemp+="-";
            if(jcb[1].getSelectedItem().toString().length()==1){
                firstTemp+="0";
                lastTemp+="0";
            }
            firstTemp+=jcb[1].getSelectedItem().toString();
            lastTemp+=jcb[1].getSelectedItem().toString();
        }else{
            firstTemp+="-__";
            lastTemp+="-__";
        }
        firstTemp+=" ";
        lastTemp+=" ";
        if(!jcb[2].getSelectedItem().toString().isEmpty()){
            if(jcb[2].getSelectedItem().toString().length()==1){
                firstTemp+="0";
            }
            firstTemp+=jcb[2].getSelectedItem().toString();
        }else
            firstTemp+="__";
        firstTemp+=":";
        if(!jcb[3].getSelectedItem().toString().isEmpty()){
            if(jcb[3].getSelectedItem().toString().length()==1){
                firstTemp+="0";
            }
            firstTemp+=jcb[3].getSelectedItem().toString();
        }else
            firstTemp+="__";

        if(!jcb[4].getSelectedItem().toString().isEmpty()){
            if(jcb[4].getSelectedItem().toString().length()==1){
                lastTemp+="0";
            }
            lastTemp+=jcb[4].getSelectedItem().toString();
        }else
            lastTemp+="__";
        lastTemp+=":";
        if(!jcb[5].getSelectedItem().toString().isEmpty()){
            if(jcb[5].getSelectedItem().toString().length()==1){
                lastTemp+="0";
            }
            lastTemp+=jcb[5].getSelectedItem().toString();
        }else
            lastTemp+="__";
        firstTemp+=":00";
        lastTemp+=":00";
        firstDate+=firstTemp;
        lastDate+=lastTemp;
        map.put("firstDate",firstDate);
        map.put("lastDate",lastDate);
        return map;
    }
    @Override
    public abstract void update();
}
