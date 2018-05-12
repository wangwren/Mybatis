package vvr.mybaits.test;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import vvr.mybatis.mapper.UserMapper;
import vvr.mybatis.pojo.User;

/**
 * 二级缓存测试
 * @author wwr
 *
 */
public class CacheTest {

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
	public void cacheTest() throws Exception {
		
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		SqlSession sqlSession3 = sqlSessionFactory.openSession();
		
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		
		//记得关sqlSession，否则不是从二级缓存中查
		
		//第一次查询id为1的用户
		User user1 = userMapper1.findById(1);
		System.out.println(user1);
		sqlSession1.close();
		
		//中间修改用户，要清空二级缓存，为了防止出现脏数据
		user1.setUsername("王六");
		userMapper3.updateUser(user1);
		sqlSession3.commit();
		sqlSession3.close();
		
		
		
		//第二次查询id为1的用户，在没有执行增加、删除、修改操作前，通过二级缓存查
		User user2 = userMapper2.findById(1);
		System.out.println(user2);
		sqlSession2.close();
	}
}
