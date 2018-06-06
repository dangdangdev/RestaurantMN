package dao;

import Utils.DBUtil;
import bean.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Customer get(int id){
        Customer ct=null;
        String sql="select * from customer where id="+id;
        try(Connection c=DBUtil.getConnection(); Statement ps=c.createStatement()){
            ResultSet rs=ps.executeQuery(sql);
            if(rs.next()){
                ct=new Customer();
                ct.setId(id);
                ct.setName(rs.getString("cname"));
                ct.setPhoneNumber(rs.getString("phoneNumber"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ct;
    }
    public List<Customer> get(String name, String phoneNumber){
        List<Customer> cs=null;
        Customer ct=null;
        String sql="select * from customer where cname='"+name+"' and phoneNumber='"+phoneNumber+"'";
        try(Connection c=DBUtil.getConnection(); Statement ps=c.createStatement()){
            ResultSet rs=ps.executeQuery(sql);
            while(rs.next()){
                if(cs==null) cs=new ArrayList<>();
                ct=new Customer();
                ct.setId(rs.getInt("id"));
                ct.setName(name);
                ct.setPhoneNumber(phoneNumber);
                cs.add(ct);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cs;
    }
    public void add(Customer ct){
        String sql="insert into customer values(null,?,?)";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,ct.getName());
            ps.setString(2,ct.getPhoneNumber());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                ct.setId(rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(Customer cu){
        String sql="update customer set cname=?,phoneNumber=? where id=?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,cu.getName());
            ps.setString(2,cu.getPhoneNumber());
            ps.setInt(3,cu.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
