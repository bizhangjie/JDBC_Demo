package com.zj.jdbc_demo.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC数据库MySQL连接工具类
 *
 * @Author 章杰
 * @Date 2023/2/8 12:53
 * @Version 1.0.0
 */
@Configuration
public class DBUtil {

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/db01?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    // 连接数据库方式一 硬编码
    public static Connection getConnection1(){
        Connection conn = null;
        try {
            // 1. 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            }catch (SQLException e){
                System.out.println("数据库连接失败");
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e){
            System.out.println("注册驱动失败");
        }
        return conn;
    }

    // 连接数据库方式二 外部文件(解耦)
    public static Connection getConnection2() throws Exception{
        // 1.加载配置文件
        Properties props = new Properties();
        InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        props.load(inputStream);
        // 2.读取配置信息
        String driver = props.getProperty("driver");
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        // 3.加载驱动
        Class.forName(driver);
        // 4.获取连接
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

//    // 连接数据库方式三
//    public static Connection getConnection3() throws Exception{
//        Properties properties = new Properties();
//        InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
//        properties.load(inputStream);
//        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//        return dataSource.getConnection();
//    }

    // 关闭连接
    public static void close(Connection conn, Statement stat, ResultSet rs){
        if (null != conn){
            try {
                conn.close();
                System.out.println("释放Connection成功...");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (null != stat){
            try {
                stat.close();
                System.out.println("释放Statement成功...");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (null != rs){
            try {
                rs.close();
                System.out.println("释放ResultSet成功...");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
