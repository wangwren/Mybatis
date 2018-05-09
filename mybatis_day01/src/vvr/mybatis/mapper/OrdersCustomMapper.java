package vvr.mybatis.mapper;

import java.util.List;

import vvr.mybatis.pojo.OrdersCustom;

public interface OrdersCustomMapper {

	/**
	 * 使用resultType完成一对一查询
	 * @return
	 * @throws Exception
	 */
	public List<OrdersCustom> findOrdersAndUserResultType() throws Exception;
}
