package vvr.mybatis.first;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import vvr.mybatis.mapper.UserMapper;
import vvr.mybatis.pojo.User;
import vvr.mybatis.pojo.UserCustomer;
import vvr.mybatis.pojo.UserQueryVo;

public class Test1 {
	
	//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	//创建工厂
	@Before
	public void init() throws IOException {
		//配置文件
		String resource = "SqlMapConfig.xml";
		
		//加载配置文件到输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		//创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 通过指定用户ID查询用户,得到单挑记录
	 */
	@Test
	public void findById() {
		
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = null;
		try {
			//通过sqlSession操作数据库
			//第一个参数：statement的位置，等于namespace+statement的id
			//第二个参数：传入的参数
			user = sqlSession.selectOne("test.findById", 10);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭sqlSession
			sqlSession.close();
		}
		System.out.println(user);
	}
	
	/**
	 * 根据用户名模糊查询
	 */
	@Test
	public void findByName() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> list = null;
		try {
			list = sqlSession.selectList("test.findByName", "小明");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		for(User user : list) {
			System.out.println(user);
		}
	}
	
	/**
	 * 添加用户
	 */
	@Test
	public void insertUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = new User();
		user.setUsername("笨张张");
		user.setSex("2");
		user.setBirthday(new Date());
		user.setAddress("庄河市");
		
		try {
			//在User.xml配置文件中，接收的参数类型是User对象，所以这里传入一个user即可
			sqlSession.insert("test.insertUser", user);
			
			//事务提交
			sqlSession.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		System.out.println(user.getId());
	}
	
	/**
	 * 用户修改
	 */
	@Test
	public void updateUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = new User();
		user.setId(28);
		user.setUsername("笨张张张张");
		user.setSex("2");
		user.setBirthday(new Date());
		user.setAddress("庄河市");
		
		try {
			sqlSession.update("test.updateUser", user);
			sqlSession.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	
	/**
	 * 用户删除
	 */
	@Test
	public void deleteUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			sqlSession.delete("test.deleteUser", 28);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
}
