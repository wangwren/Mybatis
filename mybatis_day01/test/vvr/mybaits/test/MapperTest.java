package vvr.mybaits.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

public class MapperTest {

	//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws Exception {
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 通过ID查询指定用户
	 * @throws Exception 
	 */
	@Test
	public void findById() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//此处重点，由于UserMapper是接口，也没有实现类，所以不能用new的方式，依靠mybatis生成代理对象来实现
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.findById(1);
		System.out.println(user);
		sqlSession.close();
	}
	
	/**
	 * 根据用户名模糊查询
	 * @throws Exception
	 */
	@Test
	public void findByName() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		List<User> list = userMapper.findByName("小明");
		for(User user : list) {
			System.out.println(user);
		}
		sqlSession.close();
	}
	
	/**
	 * 添加用户
	 * @throws Exception
	 */
	@Test
	public void insertUser() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = new User();
		user.setUsername("李屹勃");
		user.setSex("1");
		user.setBirthday(new Date());
		user.setAddress("鞍山");
		userMapper.insertUser(user);
		sqlSession.commit();
		sqlSession.close();
	}
	
	/**
	 * 修改指定用户
	 * @throws Exception
	 */
	@Test
	public void updateUser() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = new User();
		user.setId(30);
		user.setUsername("勃勃勃");
		user.setSex("1");
		user.setBirthday(new Date());
		user.setAddress("鞍山");
		userMapper.updateUser(user);
		sqlSession.commit();
		sqlSession.close();
	}
	
	/**
	 * 删除指定用户
	 * @throws Exception
	 */
	@Test
	public void deleteUser() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.deleteUser(30);
		sqlSession.commit();
		sqlSession.close();
	}
	
	/**
	 * 自定义查询条件查询用户信息
	 * @throws Exception 
	 */
	@Test
	public void findUserList() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		UserQueryVo userQueryVo = new UserQueryVo();
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setUsername("小明");
		userQueryVo.setUserCustomer(userCustomer);
		List<User> list = userMapper.findUserList(userQueryVo);
		for(User user : list) {
			System.out.println(user);
		}
		sqlSession.close();
	}
	
	/**
	 * 返回简单类型
	 * 自定义查询条件，查询满足条件的个数
	 * @return
	 * @throws Exception
	 */
	@Test
	public void findUserCount() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		UserQueryVo userQueryVo = new UserQueryVo();
		UserCustomer userCustomer = new UserCustomer();
		userCustomer.setUsername("小明");
		userQueryVo.setUserCustomer(userCustomer);
		
		int count = userMapper.findUserCount(userQueryVo);
		System.out.println(count);
	}
	
	/**
	 * 使用resultMap
	 * @throws Exception 
	 */
	@Test
	public void findUserListResultMap() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		UserQueryVo userQueryVo = new UserQueryVo();
		User user = new User();
		user.setUsername("小明");
		userQueryVo.setUser(user);
		
		List<User> list = userMapper.findUserListResultMap(userQueryVo);
		
		for(User u : list) {
			System.out.println(u);
		}
	}
	
	/**
	 * 动态sql
	 * @throws Exception
	 */
	@Test
	public void findUserListBySQL() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		UserQueryVo userQueryVo = new UserQueryVo();
		/*User user = new User();
		user.setUsername("小明");
		user.setSex("1");
		userQueryVo.setUser(user);*/
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(24);
		ids.add(25);
		ids.add(26);
		userQueryVo.setIds(ids);
		
		List<User> list = userMapper.findUserListBySQL(userQueryVo);
		for(User u : list) {
			System.out.println(u);
		}
	}
}
