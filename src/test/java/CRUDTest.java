import com.CloudHu.MyBatisPlus.Mapper.UserMapper;
import com.CloudHu.MyBatisPlus.POJO.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDTest {

    @Test
    //插入用户信息
    public void testInsert() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        User user = new User();
        user.setUserName("李四");
        user.setPassword("321123");
        //INSERT INTO user ( id, user_name, password ) VALUES ( ?, ?, ? )
        mapper.insert(user);
        mapper.insert(user);
        mapper.insert(user);
        mapper.insert(user);
    }

    @Test
    //通过id删除用户信息
    public void testDeleteById() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        // DELETE FROM user WHERE id=?
        int result = mapper.deleteById(20);
        System.out.println("受影响行数：" + result);
    }

    @Test
    //通过多个id批量删除
    public void testDeleteBatchIds() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        ///DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(21L, 22L, 23L);
        int result = mapper.deleteBatchIds(idList);
        System.out.println("受影响行数：" + result);
    }

    @Test
    //根据map集合中所设置的条件删除记录
    public void testDeleteByMap() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("password", 321123);
        map.put("user_name", "李四");
        int result = mapper.deleteByMap(map);
        System.out.println("受影响行数：" + result);
    }

    @Test
    //修改
    public void testUpdateById() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);

        User user = new User();
        user.setId(20);
        user.setUserName("修改后的用户");
        user.setPassword("abccba");

        //UPDATE user SET name=?, age=? WHERE id=?
        int result = mapper.updateById(user);
        System.out.println("受影响行数：" + result);
    }

    @Test
    //根据id查询用户信息
    public void testSelectById() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,name,age,email FROM user WHERE id=?
        User user = mapper.selectById(20L);
        System.out.println(user);
    }

    @Test
    //根据多个id查询多个用户信息
    public void testSelectBatchIds() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> idList = Arrays.asList(1L, 20L);
        List<User> list = mapper.selectBatchIds(idList);
        list.forEach(System.out::println);
    }

    @Test
    //通过map条件查询用户信息
    public void testSelectByMap() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("password", "abccba");
        map.put("user_name", "修改后的用户");
        List<User> list = mapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    @Test
    //查询所有用户信息
    public void testSelectList() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);
        //SELECT id,name,age,email FROM user
        List<User> list = mapper.selectList(null);
        list.forEach(System.out::println);
    }

}
