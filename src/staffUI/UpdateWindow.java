package staffUI;

import Utils.DBUtil;
import Utils.Data;
import Utils.DateUtil;
import app.BookingSystem;
import app.Observer;
import bean.Customer;
import bean.Reservation;
import bean.Table;
import dao.CustomerDAO;
import dao.ReservationDAO;
import dao.TableDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.activation.ActivationInstantiator;
import java.util.*;
import java.util.List;

public class UpdateWindow extends initWindow implements ActionListener {
    public JTable jTable;
    public JPanel jSouth;
    public JButton searchButton=new JButton("搜索");
    public JButton updateButton=new JButton("修改");
    private String warn;
    public UpdateWindow(String s){
        super();
        setTitle(s);
        initTbj();
        Data.resUpob=Data.emptyOb;
        jTable=new JTable(Data.resUpob,Data.resColu);
        jCenter.add(new JScrollPane(jTable));
        jSouth=new JPanel();
        searchButton.addActionListener(this);
        updateButton.addActionListener(this);
        cancelButton.addActionListener(this);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    displayOldReservation();
                }
            }
        });
        jSouth.add(searchButton);
        jSouth.add(updateButton);
        jSouth.add(cancelButton);
        add(baseBox,BorderLayout.NORTH);
        add(jCenter,BorderLayout.CENTER);
        add(jSouth,BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("取消")){
            BookingSystem.removeObserver(this);
            dispose();
            return ;
        }
        if(e.getActionCommand().equals("搜索")) {
            BookingSystem.notifyObservers();
        }else{
            //修改
            int []deRows=jTable.getSelectedRows();
            if(deRows.length==0 || Data.resUpob==Data.emptyOb){
                JOptionPane.showMessageDialog(this,"没有选中要修改的行","提醒",JOptionPane.WARNING_MESSAGE);
                return ;
            }
            if(!doFore()){
                JOptionPane.showMessageDialog(this,warn,"提醒",JOptionPane.WARNING_MESSAGE);
                return ;
            }
            int deReId=(int)jTable.getValueAt(deRows[0],0);
            Data.updateReservation=initInformation();   //用来获得日期
            Reservation re=new ReservationDAO().get(deReId);
            Customer c=re.getCustomer();
            c.setName(customer.getText().toString());
            c.setPhoneNumber(phoneNumber.getText().toString());
            Table tOld=re.getTable();
            tOld.setStatus_("empty");
            String a=tbj.getSelectedItem().toString();
            Table tNew=new TableDAO().get(Integer.parseInt(tbj.getSelectedItem().toString()));
            tNew.setStatus_("cover");
            //获取日期
            re.setCustomer(c);
            re.setTable(tNew);
            re.setArriveTime(DateUtil.convert(Data.updateReservation.get("firstDate")));
            re.setleftTime(DateUtil.convert(Data.updateReservation.get("lastDate")));
            new CustomerDAO().update(c);
            new TableDAO().update(tOld);
            new TableDAO().update(tNew);
            new ReservationDAO().update(re);
            BookingSystem.removeObserver(this);
            Data.setresGetob();   //确保MainWindow显示出所有的预约
            BookingSystem.removeObserver(this);
            BookingSystem.notifyObservers();
            dispose();
        }
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

    public void update() {
        jCenter.removeAll();
        Data.updateReservation=initInformation();
        Data.setResUpob();
        jTable=new JTable(
                Data.resUpob,Data.resColu);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    displayOldReservation();
                }
            }
        });
        jCenter.add(new JScrollPane(jTable));
        setVisible(true);
        pack();
    }

    public void displayOldReservation(){
        int []deRows=jTable.getSelectedRows();
        if(deRows.length==0 || Data.resUpob==Data.emptyOb){
            JOptionPane.showMessageDialog(this,"没有选中要修改的行","提醒",JOptionPane.WARNING_MESSAGE);
            return ;
        }
        if(deRows.length>1){
            JOptionPane.showMessageDialog(this,"只能选中一行","提醒",JOptionPane.WARNING_MESSAGE);
            return ;
        }
        int deReId=(int)jTable.getValueAt(deRows[0],0);
        Reservation reOld=new ReservationDAO().get(deReId);
        Customer cold=reOld.getCustomer();
        customer.setText(cold.getName());
        phoneNumber.setText(cold.getPhoneNumber());
        List<Integer> ins = BookingSystem.getEmptyTables();
        ins.add(reOld.getTable().getId());
        ins.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        tbj.removeAllItems();
        tbj.addItem("");
        if (ins != null) {
            for (Integer i : ins)
                tbj.addItem(i.intValue());
        }
        tbj.setSelectedItem(reOld.getTable().getId());
        Date arriveTime=reOld.getArriveTime();
        Date leftTime=reOld.getleftTime();
        Calendar c1=Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c1.setTime(arriveTime);
        jcb[0].setSelectedItem(c1.get(Calendar.MONTH)+1);
        jcb[1].setSelectedItem(c1.get(Calendar.DAY_OF_MONTH));
        jcb[2].setSelectedItem(c1.get(Calendar.HOUR_OF_DAY));
        jcb[3].setSelectedItem(c1.get(Calendar.MINUTE));
        c1.setTime(leftTime);
        jcb[4].setSelectedItem(c1.get(Calendar.HOUR_OF_DAY));
        jcb[5].setSelectedItem(c1.get(Calendar.MINUTE));
    }
}