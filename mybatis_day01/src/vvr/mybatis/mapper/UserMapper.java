package vvr.mybatis.mapper;

import java.util.List;

import vvr.mybatis.pojo.User;

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
}
