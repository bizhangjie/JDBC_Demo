package com.zj.jdbc_demo;

import com.zj.jdbc_demo.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
class JdbcDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void selectAll(){
        String sql = "select * from user";

        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection2();
            prst = conn.prepareStatement(sql);
            rs = prst.executeQuery();
            int row = 0;

            while (rs.next()){
                row++;
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int age = rs.getInt(3);

                System.out.println("第" + row + "行: " + id + "," + name + "," + age);
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn,prst,rs);
        }
    }

    @Test
    public void insert() throws Exception{
        // 1.获取数据库链接
        Connection connection = DBUtil.getConnection2();
        // 2.定义SQL
        String sql = "insert into tb_brand value(?,?,?,?,?,?)";
        // 3.获取pstmt对象
        PreparedStatement pstmt = connection.prepareStatement(sql);
        // 4.设置参数
        pstmt.setInt(1,4);
        pstmt.setString(2,"香飘飘");
        pstmt.setString(3,"香飘飘");
        pstmt.setInt(4,1);
        pstmt.setString(5,"一圈");
        pstmt.setInt(6,1);
        // 5.执行SQL
        int count = pstmt.executeUpdate();
        // 6.处理函数
        System.out.println(count > 0);
        // 7.释放资源
        DBUtil.close(connection,pstmt, pstmt.getResultSet());
    }

    @Test
    public void deleteById() throws Exception{
        // 1.获取链接
        Connection connection = DBUtil.getConnection1();
        // 2.编写SQL
        String sql = "delete from tb_brand where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,4);
        boolean execute = preparedStatement.execute();
        if (!execute){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
        DBUtil.close(connection,preparedStatement,preparedStatement.getResultSet());
    }

    @Test
    public void update() throws Exception{
        // 1.获取链接
        Connection connection = DBUtil.getConnection1();
        // 2.编写SQL
        String sql = "update tb_brand\n" +
                "     set brand_name=?,\n" +
                "     company_name=?,\n" +
                "     ordered=?,\n" +
                "     description=?,\n" +
                "     status =?\n" +
                "     where id =?";
        // 3.pstmt对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"sada");
        preparedStatement.setString(2,"q123");
        preparedStatement.setInt(3,2);
        preparedStatement.setString(4,"ada");
        preparedStatement.setInt(5,0);
        preparedStatement.setInt(6,4);
        // 4.执行
        int count = preparedStatement.executeUpdate();
        System.out.println(count > 0);
        DBUtil.close(connection,preparedStatement,preparedStatement.getResultSet());
    }

}


