package vvr.mybaits.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import vvr.mybatis.mapper.OrdersCustomMapper;
import vvr.mybatis.pojo.OrdersCustom;

public class OrdersCustomMapperTest {

	//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws Exception {
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/***
	 * 使用resultType完成一对一查询
	 * 通过主要查询的类的扩展类
	 * @throws Exception
	 */
	@Test
	public void findOrdersAndUserResultType() throws Exception {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<OrdersCustom> list = ordersCustomMapper.findOrdersAndUserResultType();
		for(OrdersCustom oc : list) {
			System.out.println(oc.getId());
			System.out.println(oc.getUser_Id());
			System.out.println(oc.getNumber());
			System.out.println(oc.getCreatetime());
			System.out.println(oc.getUsername());
			System.out.println(oc.getAddress());
			System.out.println("------------------------------");
		}
	}
}
