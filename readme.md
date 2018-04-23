# Mybaits
## 使用JDBC的问题
1. 数据库连接频繁的创建和关闭，缺点浪费数据库的资源，影响操作效率。设想：使用数据库连接池
2. sql语句是硬编码，如果需求变更需要修改sql，就需要修改java代码，需要重新编译，系统不易维护。设想：将sql语句 统一配置在文件中，修改sql不需要修改java代码。
3. 通过preparedStatement向占位符设置参数，存在硬编码（ 参数位置，参数）问题。系统不易维护。设想：将sql中的占位符及对应的参数类型配置在配置文件中，能够自动输入映射。
4. 遍历查询结果集存在硬编码（列名）。设想：自动进行sql查询结果向java对象的映射（输出映射）。
## Mybatis介绍
- MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis，实质上Mybatis对ibatis进行一些改进。 目前mybatis在github上托管。
- MyBatis是一个优秀的持久层框架，它对jdbc的操作数据库的过程进行封装，使开发者只需要关注 SQL 本身，而不需要花费精力去处理例如注册驱动、创建connection、创建statement、手动设置参数、结果集检索等jdbc繁杂的过程代码。
- Mybatis通过xml或注解的方式将要执行的各种statement（statement、preparedStatemnt、CallableStatement）配置起来，并通过java对象和statement中的sql进行映射生成最终执行的sql语句，最后由mybatis框架执行sql并将结果映射成java对象并返回。
## Mybatis架构

![](./_image/2018-04-23-17-18-46.jpg)

## Mybatis入门程序
- SqlMapConfig.xml(公用文件)
    - 通过SqlMapConfig.xml加载mybatis运行环境
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

	<!-- 和spring整合后 environments配置将废除-->
	<environments default="development">
		<environment id="development">
		<!-- 使用jdbc事务管理-->
			<transactionManager type="JDBC" />
		<!-- 数据库连接池-->
			<dataSource type="POOLED">
				<property name="driver" value="com.mysql.jdbc.Driver"/>
				<property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
				<property name="username" value="root"/>
				<property name="password" value="root"/>
			</dataSource>
		</environment>
	</environments>
	
	<!--加载mapper映射
	如果将和spring整合后，可以使用整合包中提供的mapper扫描器，此处的mappers不用配置了。
	 -->
	 <mappers>
	 	<mapper resource="sqlmap/User.xml"/>
	 </mappers>
	
</configuration>
```
- User.xml(重点)
    - 建议命名规则:表名+mapper.xml
    - 早期ibatis命名规则:表名.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 
	namespace命名空间，为了对sql语句进行隔离，方便管理 ，mapper开发dao方式，使用namespace有特殊作用
	mapper代理开发时将namespace指定为mapper接口的全限定名
 -->
<mapper namespace="test">

<!-- 在mapper.xml文件中配置很多的sql语句，执行每个sql语句时，封装为MappedStatement对象
mapper.xml以statement为单位管理sql语句
 -->
 
 	<!-- 根据id查询用户信息 -->
	<!-- 
		id：唯一标识 一个statement
		#{}：表示 一个占位符，如果#{}中传入简单类型的参数，#{}中的名称随意
		parameterType：输入 参数的类型，通过#{}接收parameterType输入 的参数
		resultType：输出结果 类型，不管返回是多条还是单条，指定单条记录映射的pojo类型
	 -->
	<select id="findById" parameterType="int" resultType="vvr.mybatis.pojo.User">
		select * from user where id = #{id}
	</select>
	
	<!-- 
		根据用户名称查询用户信息，可能返回多条
		${}：表示sql的拼接，通过${value}接收参数，将参数的内容不加任何修饰拼接在sql中。
		这种方式不能防止sql注入
	 -->
	<select id="findByName" parameterType="String" resultType="vvr.mybatis.pojo.User">
		select * from user where username like '%${value}%'
	</select>
</mapper>
```
- 编码
```java
//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	//创建工厂
	@Before
	public void init() throws IOException {
		//配置文件
		String resource = "SqlMapConfig.xml";
		
		//加载配置文件到输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		//创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	/**
	 * 通过指定用户ID查询用户,得到单挑记录
	 */
	@Test
	public void findById() {
		
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = null;
		try {
			//通过sqlSession操作数据库
			//第一个参数：statement的位置，等于namespace+statement的id
			//第二个参数：传入的参数
			user = sqlSession.selectOne("test.findById", 10);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭sqlSession
			sqlSession.close();
		}
		System.out.println(user);
	}
	
	/**
	 * 根据用户名模糊查询
	 */
	@Test
	public void findByName() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> list = null;
		try {
			list = sqlSession.selectList("test.findByName", "小明");
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		for(User user : list) {
			System.out.println(user);
		}
	}
```
### 开发过程
1. 编写SqlMapConfing.xml
2. 编写mapper.xml
    - 定义了statement(官方也没有给出名称，暂且叫statement)
3. 编程通过配置文件创建SqlSessionFactory
4. 通过SqlSessionFactory获取SqlSession
5. 通过SqlSession操作数据库
    - 如果执行添加、更新、删除需要调用SqlSession.commit()
6. SqlSesion使用完成要关闭
### Mybatis解决jdbc编程问题
1. 数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。
    - 解决：在SqlMapConfig.xml中配置数据链接池，使用连接池管理数据库链接。
2. Sql语句写在代码中造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。
    - 解决：将Sql语句配置在XXXXmapper.xml文件中与java代码分离。
3. 向sql语句传参数麻烦，因为sql语句的where条件不一定，可能多也可能少，占位符需要和参数一一对应。
    - 解决：Mybatis自动将java对象映射至sql语句，通过statement中的parameterType定义输入参数的类型。
4. 对结果集解析麻烦，sql变化导致解析代码变化，且解析前需要遍历，如果能将数据库记录封装成pojo对象解析比较方便。
    - 解决：Mybatis自动将sql执行结果映射至java对象，通过statement中的resultType定义输出结果的类型。
## 小总结
### SqlMapConfig.xml
- 是mybatis全局配置文件，只有一个，名称不固定的，主要mapper.xml，mapper.xml中配置 sql语句
### Mapper.xml
- mapper.xml是以statement为单位进行配置。（把一个sql称为一个statement），satatement中配置 sql语句、parameterType输入参数类型（完成输入映射）、resultType输出结果类型（完成输出映射）。
- 还提供了parameterMap配置输入参数类型（过期了，不推荐使用了）
- 还提供resultMap配置输出结果类型（完成输出映射)
### \#{}
- 表示一个占位符，向占位符输入参数，mybatis自动进行java类型和jdbc类型的转换。程序员不需要考虑参数的类型，比如：传入字符串，mybatis最终拼接好的sql就是参数两边加单引号。
- \#{}接收pojo数据，可以**使用OGNL解析出pojo的属性值**
### \#${}
- 表示sql的拼接，通过${}接收参数，将参数的内容不加任何修饰拼接在sql中。
- ${}也可以接收pojo数据，可以使用OGNL解析出pojo的属性值
- 缺点：不能防止sql注入。
### selectOne
- 用于查询单条记录，不能用于查询多条记录，否则异常：
```java
org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be returned by selectOne(), but found: 4
```
### selectList
- 用于查询多条记录，可以用于查询单条记录的。





