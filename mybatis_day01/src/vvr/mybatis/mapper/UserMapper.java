package vvr.mybatis.mapper;

import java.util.List;

import vvr.mybatis.pojo.User;
import vvr.mybatis.pojo.UserQueryVo;

public interface UserMapper {

	/**
	 * 查询指定用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findById(int id) throws Exception;
	
	/**
	 * 根据用户名模糊查询
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<User> findByName(String username) throws Exception;
	
	/**
	 * 添加用户
	 * @param user
	 * @throws Exception
	 */
	public void insertUser(User user) throws Exception;
	
	/**
	 * 删除指定用户
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(int id) throws Exception;
	
	/**
	 * 修改指定用户
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception;
	
	/**
	 * 自定义查询条件查询用户信息
	 * 通过user用户的包装类型
	 * @param userQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<User> findUserList(UserQueryVo userQueryVo) throws Exception;
	
	/**
	 * 自定义查询条件，返回满足查询条件的个数
	 * @param userQueryVo
	 * @return
	 * @throws Exception
	 */
	public int findUserCount(UserQueryVo userQueryVo) throws Exception;
	
	/**
	 * 使用resultMap
	 * 此处方法的返回值为定义resultMap时指定的type类型，这里模糊查询，查询多个用户
	 * @param userQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<User> findUserListResultMap(UserQueryVo userQueryVo) throws Exception;
	
	/**
	 * 动态sql
	 * @param userQueryVo
	 * @return
	 * @throws Exception
	 */
	public List<User> findUserListBySQL(UserQueryVo userQueryVo) throws Exception;
}
