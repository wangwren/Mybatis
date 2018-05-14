package vvr.mybatis.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vvr.mybatis.mapper.ItemsMapper;
import vvr.mybatis.pojo.Items;
import vvr.mybatis.pojo.ItemsExample;

public class ItemsMapperTest {

	
	private ApplicationContext applicationContext;
	@Before
	public void setUp() throws Exception {
		//创建spring容器
		
		applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		
	}
	
	/**
	 * 根据主键查询
	 */
	@Test
	public void testSelectByPrimaryKey() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		Items items = itemsMapper.selectByPrimaryKey(1);
		System.out.println(items.getName());
	}
	
	/**
	 * 自定义查询条件
	 */
	@Test
	public void testSelectByExample() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		
		//创建example，这里是items的，为了定义查询条件
		ItemsExample itemsExample = new ItemsExample();
		
		//定义查询条件
		ItemsExample.Criteria criteria = itemsExample.createCriteria();
		criteria.andNameEqualTo("笔记本");
		
		//在items表中还有字段detail，该字段是一个大文本字段，这条查询语句不包含查询大文本
		List<Items> list = itemsMapper.selectByExample(itemsExample);
		
		//将大文本也查询出来
		//List<Items> list = itemsMapper.selectByExampleWithBLOBs(itemsExample);
		
		for(Items items : list) {
			System.out.println(items.getName());
		}
	}
	
	/**
	 * 插入
	 */
	@Test
	public void testInsert() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		
		Date time = new Date();
		
		Items items = new Items();
		items.setName("手机");
		items.setPrice(3000f);
		items.setCreatetime(time);
		
		itemsMapper.insert(items);
		
	}

	/**
	 * 根据主键删除
	 */
	@Test
	public void testdeleteByPrimaryKey() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		
		itemsMapper.deleteByPrimaryKey(4);
		
		//自定义条件删除
		//itemsMapper.deleteByExample(example);
		
	}
	
	/**
	 * 根据主键更新，全部字段更新，先查询再更新
	 * 控制台打出的SQL语句
	 * update items set name = ?, price = ?, pic = ?, createtime = ? where id = ? 
	 */
	@Test
	public void testUpdateByPrimaryKey() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		
		//先查询
		Items items = itemsMapper.selectByPrimaryKey(1);
		items.setName("大台式机");
		//更新
		itemsMapper.updateByPrimaryKey(items);
	}
	
	/**
	 * 如果更新对象的属性不为空才更新到数据库
	 * 常用于指定字段更新，不用先查询出，直接new一个对象，但是需要指定主键
	 * 控制台输出语句
	 * update items SET name = ? where id = ? 
	 */
	@Test
	public void testupdateByPrimaryKeySelective() {
		
		ItemsMapper itemsMapper = (ItemsMapper) applicationContext.getBean("itemsMapper");
		
		Items items = new Items();
		items.setId(1);
		items.setName("小台式机");
		
		//只更新不为空的字段
		itemsMapper.updateByPrimaryKeySelective(items);
		
		/**
		 * 自定义条件更新
		 * record:指定的更新对象
		 * example:设置对象
		 */
		//itemsMapper.updateByExample(record, example);
		
		//可以更新大文本字段
		//itemsMapper.updateByExampleWithBLOBs(record, example);
		
	}
	
}
