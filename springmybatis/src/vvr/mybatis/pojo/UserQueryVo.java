package vvr.mybatis.pojo;

import java.util.List;

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
	
	//用户id集合
	private List<Integer> ids;
	
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

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
