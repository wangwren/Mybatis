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
import vvr.mybatis.pojo.Orderdetail;
import vvr.mybatis.pojo.Orders;
import vvr.mybatis.pojo.OrdersCustom;
import vvr.mybatis.pojo.User;

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
	
	/**
	 * 使用resultMap完成一对一映射
	 * @throws Exception
	 */
	@Test
	public void findOrdersAndUserResultMap() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<Orders> list = ordersCustomMapper.findOrdersAndUserResultMap();
		for(Orders o : list) {
			System.out.println(o.getId());
			System.out.println(o.getUser_Id());
			System.out.println(o.getNumber());
			System.out.println(o.getCreatetime());
			System.out.println(o.getUser().getUsername());
			System.out.println(o.getUser().getAddress());
		}
	}
	
	/**
	 * 使用resultMap实现一对多查询
	 * 使用resultMap可以忽略重复数据，比如下面代码数据库查询是四条，有两条订单信息是重复的
	 * 如果使用resultType实现一对多查询，就会出现重复查询，因为resultType还要写扩展类，需要把要查询的字段都定义出来
	 * @throws Exception
	 */
	@Test
	public void findOrdersAndOrderdetailResultMap() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<Orders> list = ordersCustomMapper.findOrdersAndOrderdetailResultMap();
		System.out.println(list);
	}
	
	/**
	 * 复杂一对多查询
	 * @throws Exception
	 */
	@Test
	public void findUserOrderDetail() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<User> list = ordersCustomMapper.findUserOrderDetail();
		System.out.println(list);
	}
	
	/**
	 * 一对一延迟加载
	 * @throws Exception 
	 */
	@Test
	public void findOrdersAndUserLazyLoading() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<Orders> list = ordersCustomMapper.findOrdersAndUserLazyLoading();
		
		//在getUser()时才会延迟加载，即执行延迟加载语句
		User user = list.get(0).getUser();
		
		for(Orders od : list) {
			
			System.out.println(od.getUser().getId());
			System.out.println("==========");
		}
	}
	
	/**
	 * 一对多延迟加载
	 * @throws Exception
	 */
	@Test
	public void findOrdersAndOrderdetailLazyLoading() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		OrdersCustomMapper ordersCustomMapper = sqlSession.getMapper(OrdersCustomMapper.class);
		List<Orders> list = ordersCustomMapper.findOrdersAndOrderdetailLazyLoading();
		
		for(Orders or : list) {
			List<Orderdetail> delist = or.getOrderdetails();
			System.out.println("哈哈哈" + delist.get(0).getItemsNum());
		}
		
		System.out.println(list);
		//list.get(0).getOrderdetails().get(0).getOrdersId();
	}
}
