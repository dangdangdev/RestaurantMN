package bean;

import javax.xml.crypto.Data;
import java.util.Date;

public class WalkIn {
    public int id;
    public Date arriveTime;
    public Table table;
    public WalkIn(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
