package com.lagou.test;

import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Author wsl
 * @Date 2021/10/30 22:53
 * @Project_Name mybatis_quickstart
 */
public class MybatisTest {

    /**
     * 1.测试查询方法
     * @throws IOException
     */
    @Test
    public void mybatisQuickStart() throws IOException {

        //1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        //2.获取sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3.获取sqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.执行sql参数 statementid : namespace.id
        List<User> users = sqlSession.selectList("userMapper.findAll");

        //5.遍历打印结果
        for (User user : users) {
            System.out.println(user);
        }

        //6.关闭资源
        sqlSession.close();

    }

    /**
     * 2.测试新增用户的方法
     */
    @Test
    public void saveTest() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        //创建User实体对象
        User user = new User();
        user.setUsername("自动提交事务"); //openSession(true);
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("上海");
        sqlSession.insert("userMapper.saveUser",user);

        //DML语句，手动提交事务
        //sqlSession.commit();

        System.out.println("添加数据成功！！！");
        sqlSession.close();
    }

    /**
     * 3.测试更新用户的方法
     */
    @Test
    public void updateTest() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        //创建User实体对象
        User user = new User();
        user.setId(4);
        user.setUsername("luck");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("上海");

        sqlSession.update("userMapper.updateUser",user);

        //DML语句，手动提交事务
        sqlSession.commit();

        System.out.println("更新用户数据成功！！！");

        sqlSession.close();
    }

    /**
     * 4.测试删除用户的方法
     */
    @Test
    public void deleteTest() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("userMapper.deleteUser", 6);

        //DML语句，手动提交事务
        sqlSession.commit();

        System.out.println("删除用户数据成功！！！");

        sqlSession.close();
    }
}
