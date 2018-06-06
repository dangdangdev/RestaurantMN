package bean;

import java.util.Date;

public class Reservation {
    public int id;
    public Date leftTime;
    public Date arriveTime;
    public Table table;
    public Customer customer;
    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getleftTime() {
        return leftTime;
    }

    public void setleftTime(Date leftTime) {
        this.leftTime = leftTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
