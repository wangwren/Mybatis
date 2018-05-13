package vvr.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import vvr.mybatis.pojo.User;

/**
 * 
 * @author wwr
 *SqlSessionDaoSupport与hibernate的hibernateDaoSupport一样
 */
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	@Override
	public User findUserById(int id) throws Exception {

		SqlSession sqlSession = this.getSqlSession();
		
		//selectOne("test.findById",id)，第二个参数代表想要查询的用户id，必须写，否则查不到数据
		User user = sqlSession.selectOne("test.findById",id);

		return user;

	}

}
