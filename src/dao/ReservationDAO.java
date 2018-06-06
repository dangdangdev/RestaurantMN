package dao;

import Utils.DBUtil;
import Utils.DateUtil;
import bean.Customer;
import bean.Reservation;
import bean.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public Reservation get(int id){
        Reservation re=null;
        String sql="select * from reservation where id="+id;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            re=new Reservation();
            if(rs.next()){
                re.setId(id);
                re.setTable(new TableDAO().get(rs.getInt("table_id")));
                re.setCustomer(new CustomerDAO().get(rs.getInt("customer_id")));
                re.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setleftTime(DateUtil.t2d(rs.getTimestamp("leftTime")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return re;
    }
    public List<Reservation> getAll(){
        List<Reservation> res=null;
        String sql="select * from reservation order by id desc";
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                if(res==null) res=new ArrayList<>();
                Reservation re=new Reservation();
                re.setId(rs.getInt("id"));
                re.setTable(new TableDAO().get(rs.getInt("table_id")));
                re.setCustomer(new CustomerDAO().get(rs.getInt("customer_id")));
                System.out.println(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setleftTime(DateUtil.t2d(rs.getTimestamp("leftTime")));
                res.add(re);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public List<Reservation> get(Customer customer){
        List<Reservation> res=null;
        String sql="select * from reservation where customer_id="+customer.getId()+" order by id desc";
       // String sql="select * from reservation where date_format(arriveTime,'%Y-%m-%d %H:%i:%s') like ? and date_format(leftTime,'%Y-%m-%d %H:%i:%s') like ?";
        try(Connection c=DBUtil.getConnection();Statement ps=c.createStatement()){
            ResultSet rs=ps.executeQuery(sql);
            while(rs.next()){
                if(res==null) res=new ArrayList<>();
                Reservation re=new Reservation();
                re.setId(rs.getInt("id"));
                re.setleftTime(DateUtil.t2d(rs.getTimestamp("leftTime")));
                re.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setTable(new TableDAO().get(rs.getInt("table_id")));
                re.setCustomer(new CustomerDAO().get(rs.getInt("customer_id")));
                res.add(re);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public List<Reservation> get(String firstDate,String lastDate){
        List<Reservation> res=null;
       // String sql="select * from reservation where convert(varchar(20),arriveTime,120) like ? and convert(varchar(20),leftTime,120) like ?";
        String sql="select * from reservation where date_format(arriveTime,'%Y-%m-%d %H:%i:%s') like ? and date_format(leftTime,'%Y-%m-%d %H:%i:%s') like ? order by id desc";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,firstDate);
            ps.setString(2,lastDate);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(res==null) res=new ArrayList<>();
                Reservation re=new Reservation();
                re.setId(rs.getInt("id"));
                re.setleftTime(DateUtil.t2d(rs.getTimestamp("leftTime")));
                re.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setTable(new TableDAO().get(rs.getInt("table_id")));
                re.setCustomer(new CustomerDAO().get(rs.getInt("customer_id")));
                res.add(re);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public List<Reservation> get(List<Customer> cs,String firstDate,String lastDate){
        List<Reservation> res=null;
        String sql="select * from reservation where date_format(arriveTime,'%Y-%m-%d %H:%i:%s') like ? and date_format(leftTime,'%Y-%m-%d %H:%i:%s') like ? and customer_id in (";
        for(int i=0;i<cs.size()-1;i++)
            sql+="?,";
        sql+="?) order by id desc";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,firstDate);
            ps.setString(2,lastDate);
            int i=3;
            for(Customer cu:cs){
                ps.setInt(i,cu.getId());
                i++;
            }
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(res==null) res=new ArrayList<>();
                Reservation re=new Reservation();
                re.setId(rs.getInt("id"));
                re.setleftTime(DateUtil.t2d(rs.getTimestamp("leftTime")));
                re.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                re.setTable(new TableDAO().get(rs.getInt("table_id")));
                re.setCustomer(new CustomerDAO().get(rs.getInt("customer_id")));
                res.add(re);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public void add(Reservation re){
        String sql="insert into reservation values(null,?,?,?,?)";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,re.getCustomer().getId());
            ps.setTimestamp(2,DateUtil.d2t(re.getArriveTime()));
            ps.setTimestamp(3,DateUtil.d2t(re.getleftTime()));
            ps.setInt(4,re.getTable().getId());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                re.setId(rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(int id){
        //先改变桌子的状态，这些工作不在这里进行，在Data里面进行
       /* Reservation re=get(id);
        re.getTable().setStatus_("empty");
        new TableDAO().update(re.getTable());  */
        String sql="delete from reservation where id="+id;
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            s.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(Reservation re){
        /*先释放原有的所占的餐桌,在Data中进行
        Reservation oldRe=new ReservationDAO().get(re.getId());
        Table t=oldRe.getTable();
        t.setStatus_("empty");
        new TableDAO().update(t); */
        String sql="update reservation set leftTime=?,arriveTime=?,table_id=?,customer_id=? where id=?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setTimestamp(1,DateUtil.d2t(re.getleftTime()));
            ps.setTimestamp(2,DateUtil.d2t(re.getArriveTime()));
            ps.setInt(3,re.getTable().getId());
            ps.setInt(4,re.getCustomer().getId());
            ps.setInt(5,re.getId());
            ps.execute();
          /*  Table t1=re.getTable();
            t1.setStatus_("cover");
            new TableDAO().update(t);  */
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
