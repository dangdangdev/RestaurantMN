package dao;

import Utils.DBUtil;
import bean.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    public Table get(int id){
        Table t=null;
        String sql="select * from table_ where id="+id;
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            if(rs.next()){
                t=new Table();
                t.setId(id);
                t.setNumber(rs.getInt("number"));
                t.setStatus_(rs.getString("status_"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }
    public List<Table> getEmpty(){
        List<Table> ts=null;
        String sql="select * from table_ where status_='empty'";
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                if(ts==null) ts=new ArrayList<>();
                Table t=new Table();
                t.setId(rs.getInt("id"));
                t.setNumber(rs.getInt("number"));
                t.setStatus_(rs.getString("status_"));
                ts.add(t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ts;
    }
    public List<Table> getAll(){
        List<Table> ts=null;
        String sql="select * from table_";
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                if(ts==null) ts=new ArrayList<>();
                Table t=new Table();
                t.setId(rs.getInt("id"));
                t.setNumber(rs.getInt("number"));
                t.setStatus_(rs.getString("status_"));
                ts.add(t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ts;
    }
    public void update(Table t){
        String sql="update table_ set status_=? where id=?";
        try(Connection c=DBUtil.getConnection(); PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,t.getStatus_());
            ps.setInt(2,t.getId());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void add(int start,int end){
        String sql="insert into table_ values ";
        for(int i=start;i<=end;i++)
            sql+="("+i+","+i+",'empty'"+"),";
        sql=sql.substring(0,sql.length()-1);
        try(Connection c=DBUtil.getConnection();Statement s=c.createStatement()){
            s.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
