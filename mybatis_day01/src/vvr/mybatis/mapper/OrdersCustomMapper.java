package vvr.mybatis.mapper;

import java.util.List;

import vvr.mybatis.pojo.Orders;
import vvr.mybatis.pojo.OrdersCustom;
import vvr.mybatis.pojo.User;

public interface OrdersCustomMapper {

	/**
	 * 使用resultType完成一对一查询
	 * @return
	 * @throws Exception
	 */
	public List<OrdersCustom> findOrdersAndUserResultType() throws Exception;
	
	/**
	 * 使用resultMap完成一对一查询
	 * @return
	 * @throws Exception
	 */
	public List<Orders> findOrdersAndUserResultMap() throws Exception;
	
	/**
	 * 使用resultMap完成一对多查询
	 * @return
	 * @throws Exception
	 */
	public List<Orders> findOrdersAndOrderdetailResultMap() throws Exception;
	
	/**
	 * 使用resultMap完成复杂的一对多查询
	 * @return
	 * @throws Exception
	 */
	public List<User> findUserOrderDetail() throws Exception;
}
