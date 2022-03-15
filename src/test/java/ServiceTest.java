import com.CloudHu.MyBatisPlus.Mapper.UserMapper;
import com.CloudHu.MyBatisPlus.POJO.User;
import com.CloudHu.MyBatisPlus.Service.Impl.UserServiceImpl;
import com.CloudHu.MyBatisPlus.Service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

public class ServiceTest {

    @Test
    //获取总数
    public void testGetCount() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = ac.getBean(UserService.class);
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    //批量插入
    public void testSaveBatch() {
        // SQL长度有限制，海量数据插入单条SQL无法实行
        // 因此MP将批量插入放在了通用Service中实现，而不是通用Mapper
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = ac.getBean(UserService.class);
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword(""+i);
            users.add(user);
        }
        //SQL:INSERT INTO t_user ( username, age ) VALUES ( ?, ? ) userService.saveBatch(users);
        userService.saveBatch(users);
    }
}
