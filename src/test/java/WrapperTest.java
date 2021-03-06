import com.CloudHu.MyBatisPlus.Mapper.UserMapper;
import com.CloudHu.MyBatisPlus.POJO.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperTest {
    @Test
    //QueryWrapper组装查询条件
    public void test01() {
        //查询用户名包含李，并且密码不为null的用户信息
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,user_name,password,is_deleted FROM user WHERE is_deleted=0 AND (user_name LIKE ? AND password IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "李").isNotNull("password");
        List<User> list = mapper.selectList(queryWrapper);
        list.forEach(item -> System.out.println(item));
    }

    @Test
    //QueryWrapper组装排序条件
    public void test02() {
        //按id升序查询用户，如果年龄相同则按user_name降序排列
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,user_name,password,is_deleted FROM user WHERE is_deleted=0 ORDER BY id ASC,user_name DESC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .orderByAsc("id")
                .orderByDesc("user_name");
        List<User> list = mapper.selectList(queryWrapper);
        list.forEach(item -> System.out.println(item));
    }

    @Test
    //QueryWrapper组装删除条件
    public void test03() {
        //删除password为空的用户
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,user_name,password,is_deleted FROM user WHERE is_deleted=0 AND (user_name LIKE ? AND password IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("password");
        //条件构造器也可以构建删除语句的条件
        int result = mapper.delete(queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    @Test
    //QueryWrapper条件的优先级
    public void test04() {
        //将（密码包含123并且用户名中包含有李）或用户名叫user1的用户信息修改
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("user_name", "李")
                .like("password", 123)
                .or()
                .eq("user_name", "user4");
        User user = new User();
        user.setUserName("QueryWrapper改变后的用户1");
        user.setPassword("333");
        int result = mapper.update(user, queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    @Test
    //QueryWrapper条件的优先级（lambda表达式）
    public void test05() {
        //将（id等于25或用户名中包含有李）且密码包含123的用户信息修改
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("password", "123")
                .and(i -> i.
                        eq("id", 25)
                        .or()
                        .like("user_name", "李"));
        User user = new User();
        user.setUserName("QueryWrapper改变后的用户2");
        user.setPassword("333");
        int result = mapper.update(user, queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    @Test
    //QueryWrapper组装select子句
    public void test06() {
        //查询用户信息的username和password字段
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name", "password");
        //selectMaps()返回Map集合列表，通常配合select()使用，避免User对象中没有被查询到的列值 为null
        List<Map<String, Object>> maps = mapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    @Test
    //QueryWrapper实现子查询
    public void test07() {
        //查询id小于等于3的用户信息
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from user where id <= 3");
        //selectObjs的使用场景：只返回一列
        List<Object> objects = mapper.selectObjs(queryWrapper);
        objects.forEach(item -> System.out.println(item.toString()));
    }

    @Test
    //UpdateWrapper
    public void test08() {
        //将id为1并且用户名中包含有张的用户信息修改

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //组装set子句以及修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //lambda表达式内的逻辑优先运算
        updateWrapper
                .set("user_name", "UpdateWrapper修改的用户")
                .set("password", "111111")
                .like("user_name", "张")
                .and(i -> i.eq("id", 1));
        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        //User user = new User();
        //user.setName("张三");
        //int result = userMapper.update(user, updateWrapper);
        //UPDATE t_user SET age=?,email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))
        int result = mapper.update(null, updateWrapper);
        System.out.println(result);

    }

    @Test
    //condition
    public void test09() {
        //定义查询条件，有可能为null（用户未输入或未选择）
        String username = null;
        String password = "333";

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //StringUtils.isNotBlank判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        queryWrapper
                .like(StringUtils.isNotBlank(username),"user_name",username)
                .like(StringUtils.isNotBlank(password),"password",password);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    //LambdaQueryWrapper
    public void test10() {
        //定义查询条件，有可能为null（用户未输入或未选择）
        String username = null;
        String password = "333";

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //StringUtils.isNotBlank判断某字符串是否为空或长度为0或由空白符(whitespace)构成
        queryWrapper
                .like(StringUtils.isNotBlank(username),User::getUserName,username)
                .like(StringUtils.isNotBlank(password),User::getPassword,password);
        List<User> users = mapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
