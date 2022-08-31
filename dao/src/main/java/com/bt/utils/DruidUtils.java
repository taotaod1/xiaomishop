package com.bt.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 86155
 * @version v1.0
 * @project botao_xiaomi
 * @data 2022/8/24 10:00
 **/
public class DruidUtils {
//    创建连接池
    private  static DruidDataSource dataSource;
    private  static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    static {
        Properties properties=new Properties();
        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
            is.close();
            dataSource=(DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
//         日志
            System.out.println("初始化链接池成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池失败");
        }
    }

//    返回连接池
    public static DataSource getDataSource(){
        return  dataSource;
    }
    public  static  void closeAll(ResultSet rs, Statement st, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //    返回链接
    public static  Connection getConnection(){
        Connection connection= null;
        try {
            connection = threadLocal.get();
            if(connection==null){
                    connection=dataSource.getConnection();
                    threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  void begin() throws SQLException {
        Connection conn=getConnection();
        if(conn!=null){
            conn.setAutoCommit(false);
        }
    }
    public static  void rollback() throws SQLException {
        Connection conn=getConnection();
        if(conn!=null){
            conn.rollback();
        }
    }
    public static  void commit() throws SQLException {
        Connection conn=getConnection();
        if(conn!=null){
            conn.commit();
        }
    }
    public static  void close() throws SQLException {
        Connection conn=getConnection();
        if(conn!=null){
            conn.close();
            threadLocal.remove();
        }
    }
}
