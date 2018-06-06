package Utils;

import bean.Customer;
import bean.Reservation;
import bean.Table;
import dao.CustomerDAO;
import dao.ReservationDAO;
import dao.TableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class Data {
    public static Object [][]resGetob;
    public static Object [][]resUpob;
    public  static Object [][]resDeob;
    public static Object [][]emptyOb;
    public static Object []resColu={"ID","顾客名字","顾客电话","到达时间","离开时间","餐桌编号"};
    //public static Object [][]walkOb=null;
    //public static Object []walkColu={"ID","到来时间","餐桌编号"};
    public static Map<String,String> newReservation;
    public static Map<String,String> updateReservation;
    public static Map<String,String> deleteReservation;
    public static Map<String,String> displayReservation;
    static{
        newReservation=null;
        updateReservation=null;
        deleteReservation=null;
        emptyOb=new Object[1][6];
        for(int i=0;i<6;i++) emptyOb[0][i]="null";
    }
    public static void setresGetob(){    //全部查询
        resGetob=null;
        List<Reservation> res=new ReservationDAO().getAll();
        if(res!=null) resGetob=new Object[res.size()][6];
        else {
            resGetob=emptyOb;
            return ;
        }
        int i=0;
        for(Reservation re:res) {
            resGetob[i][0] = re.getId();
            resGetob[i][1] = re.getCustomer().getName();
            resGetob[i][2] = re.getCustomer().getPhoneNumber();
            resGetob[i][3] = re.getArriveTime().toString();
            resGetob[i][4] = re.getleftTime().toString();
            resGetob[i][5] = re.getTable().getId();
            i++;
        }
    }
    public static void setResObCondition(){
        resGetob=null;
        String name=displayReservation.get("name");
        String phoneNumber=displayReservation.get("phoneNumber");
        String table_id=displayReservation.get("table_id");
        String firstDate=displayReservation.get("firstDate");
        String lastDate=displayReservation.get("lastDate");
        List<Customer> cs=null;
        if(!name.isEmpty() && !phoneNumber.isEmpty()) {
            cs=new CustomerDAO().get(name,phoneNumber);
        }
        List<Reservation> res=null;
        if(cs==null)
            res=new ReservationDAO().get(firstDate,lastDate);
        else
            res=new ReservationDAO().get(cs,firstDate,lastDate);
        if(res!=null) resGetob=new Object[res.size()][6];
        else {
            resGetob=emptyOb;
            return ;
        }
        int i=0;
        for(Reservation re:res){
            resGetob[i][0]=re.getId();
            resGetob[i][1]=re.getCustomer().getName();
            resGetob[i][2]=re.getCustomer().getPhoneNumber();
            resGetob[i][3]=re.getArriveTime().toString();
            resGetob[i][4]=re.getleftTime().toString();
            resGetob[i][5]=re.getTable().getId();
            i++;
        }
    }
    public static void setResDeob(){
        resDeob=null;
        String name=deleteReservation.get("name");
        String phoneNumber=deleteReservation.get("phoneNumber");
        String table_id=deleteReservation.get("table_id");
        String firstDate=deleteReservation.get("firstDate");
        String lastDate=deleteReservation.get("lastDate");
        List<Customer> cs=null;
        if(!name.isEmpty() && !phoneNumber.isEmpty()) {
            cs=new CustomerDAO().get(name,phoneNumber);
        }
        List<Reservation> res=null;
        if(cs==null)
            res=new ReservationDAO().get(firstDate,lastDate);
        else
            res=new ReservationDAO().get(cs,firstDate,lastDate);
        if(res!=null) resDeob=new Object[res.size()][6];
        else {
            resDeob=emptyOb;
            return ;
        }
        int i=0;
        for(Reservation re:res){
            resDeob[i][0]=re.getId();
            resDeob[i][1]=re.getCustomer().getName();
            resDeob[i][2]=re.getCustomer().getPhoneNumber();
            resDeob[i][3]=re.getArriveTime().toString();
            resDeob[i][4]=re.getleftTime().toString();
            resDeob[i][5]=re.getTable().getId();
            i++;
        }
    }
    public static void addReservation(){
        String name=newReservation.get("name");
        String phoneNumber=newReservation.get("phoneNumber");
        String table_id=newReservation.get("table_id");
        String firstDate=newReservation.get("firstDate");
        String lastDate=newReservation.get("lastDate");
        Reservation re=new Reservation();
        Customer c=new Customer();
        c.setName(name);
        c.setPhoneNumber(phoneNumber);
        new CustomerDAO().add(c);
        re.setCustomer(c);
        re.setTable(new TableDAO().get(Integer.parseInt(table_id)));
        re.setArriveTime(DateUtil.convert(firstDate));
        re.setleftTime(DateUtil.convert(lastDate));
        new ReservationDAO().add(re);
    }
    public static void deleteReservations(int []deReId){
        ReservationDAO red=new ReservationDAO();
        TableDAO td=new TableDAO();
        for(int de:deReId){
            Reservation re=red.get(de);  //释放所占餐桌
            Table t=re.getTable();
            t.setStatus_("empty");
            td.update(t);
            red.delete(de);
        }
    }
    public static void setResUpob(){
        resUpob=null;
        String name=updateReservation.get("name");
        String phoneNumber=updateReservation.get("phoneNumber");
        String table_id=updateReservation.get("table_id");
        String firstDate=updateReservation.get("firstDate");
        String lastDate=updateReservation.get("lastDate");
        List<Reservation> res=null;
        List<Customer> cs=null;
        if(!name.isEmpty() && !phoneNumber.isEmpty()) {
            cs=new CustomerDAO().get(name,phoneNumber);
        }
        if(cs==null)
            res=new ReservationDAO().get(firstDate,lastDate);
        else
            res=new ReservationDAO().get(cs,firstDate,lastDate);
        if(res!=null) resUpob=new Object[res.size()][6];
        else {
            resUpob=emptyOb;
            return ;
        }
        int i=0;
        for(Reservation re:res){
            resUpob[i][0]=re.getId();
            resUpob[i][1]=re.getCustomer().getName();
            resUpob[i][2]=re.getCustomer().getPhoneNumber();
            resUpob[i][3]=re.getArriveTime().toString();
            resUpob[i][4]=re.getleftTime().toString();
            resUpob[i][5]=re.getTable().getId();
            i++;
        }
    }
}
