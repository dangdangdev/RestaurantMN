package app;

import bean.Customer;
import bean.Reservation;
import bean.Table;
import bean.WalkIn;
import dao.ReservationDAO;
import dao.TableDAO;
import dao.WalkInDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingSystem {
    protected static List<Observer> obs=new ArrayList<>();
    protected WalkInDAO walkInDAO=new WalkInDAO();
    protected ReservationDAO reservationDAO=new ReservationDAO();
    public static void addObserver(Observer ob){
        obs.add(ob);
    }
    public static void removeObserver(Observer ob){
        obs.remove(ob);
    }
    public static void notifyObservers(){
        for(Observer ob:obs){
            ob.update();
        }
    }
    public static List<Integer> getEmptyTables(){
        List<Table> ts=new TableDAO().getEmpty();
        List<Integer> ins=null;
        if(ts!=null){
            ins=new ArrayList<>();
            for(Table t:ts)
                ins.add(t.getId());
        }
        return ins;
    }
    public static List<Integer> getAllTables(){
        List<Table> ts=new TableDAO().getAll();
        List<Integer> ins=null;
        if(ts!=null){
            ins=new ArrayList<>();
            for(Table t:ts) ins.add(t.getId());
        }
        return ins;
    }
}
