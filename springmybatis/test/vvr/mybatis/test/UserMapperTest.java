package vvr.mybatis.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vvr.mybatis.dao.UserDao;
import vvr.mybatis.mapper.UserMapper;
import vvr.mybatis.pojo.User;

public class UserMapperTest {

	private ApplicationContext applicationContext;
	@Before
	public void setUp() throws Exception {
		//创建spring容器
		
		applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		
	}
	
	@Test
	public void findUserById() throws Exception {
		
		UserMapper userMapper = (UserMapper) applicationContext.getBean("userMapper");
		User user = userMapper.findById(10);
		System.out.println(user);
	}
}
