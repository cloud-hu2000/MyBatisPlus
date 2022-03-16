import com.CloudHu.MyBatisPlus.Enum.SexEnum;
import com.CloudHu.MyBatisPlus.Mapper.UserMapper;
import com.CloudHu.MyBatisPlus.POJO.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnumTest {
    @Test
    public void testSexEnum() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = ac.getBean(UserMapper.class);

        User user = new User();
        user.setUserName("Enum");
        user.setSex(SexEnum.MALE);
        // INSERT INTO t_user ( username, age, sex ) VALUES ( ?, ?, ? )
        // Parameters: Enum(String), 20(Integer), 1(Integer)
        mapper.insert(user);
    }
}
