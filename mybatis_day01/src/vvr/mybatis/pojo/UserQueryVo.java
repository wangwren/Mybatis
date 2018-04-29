package vvr.mybatis.pojo;
/**
 * User的包装类型
 * @author wwr
 *
 */
public class UserQueryVo {

	//用户信息
	private User user;
	
	//自定义user的扩展对象
	private UserCustomer userCustomer;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}
	
	
}
