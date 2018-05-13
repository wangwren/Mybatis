package vvr.mybatis.pojo;
/**
 * 定义orders的扩展类
 * @author wwr
 *
 */
public class OrdersCustom extends Orders{

	//定义查询订单信息时需要显示的用户信息字段
	
	private String username;
	private String address;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
