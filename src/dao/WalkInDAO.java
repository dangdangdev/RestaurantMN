package dao;

import Utils.DBUtil;
import Utils.DateUtil;
import bean.Customer;
import bean.WalkIn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalkInDAO {
    public WalkIn get(int id){
        WalkIn w=null;
        String sql="select * from walkIn where id="+id;
        try(Connection c=DBUtil.getConnection(); Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                w=new WalkIn();
                w.setId(id);
                w.setTable(new TableDAO().get(rs.getInt("table_id")));
                w.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return w;
    }
    public List<WalkIn> get(String firstDate,String lastDate){
        List<WalkIn> ws=null;
        String sql="select * from walkIn where arriveTime between ? and ?";
        try(Connection c=DBUtil.getConnection();PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,firstDate);
            ps.setString(2,lastDate);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(ws==null) ws=new ArrayList<>();
                WalkIn w=new WalkIn();
                w.setId(rs.getInt("id"));
                w.setTable(new TableDAO().get(rs.getInt("table_id")));
                w.setArriveTime(DateUtil.t2d(rs.getTimestamp("arriveTime")));
                ws.add(w);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ws;
    }
    public void add(WalkIn w){
        String sql="insert into walkIn values(null,?,?)";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setTimestamp(1,DateUtil.d2t(w.getArriveTime()));
            ps.setInt(2,w.getTable().getId());
            ps.execute();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                w.setId(rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(int id){
        String sql="delete from walkIn where id="+id;
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            s.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
