package mytest;

import com.jia.home.mapper.BedBuildingMapper;
import com.jia.home.model.BedBuilding;
import com.jia.home.utils.MailConstants;
import com.jia.home.model.User;
import com.jia.home.utils.UUIDUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class BlogWebApplicationTests {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    BedBuildingMapper buildingMapper;

    @Test
    void contextLoads() {
        String uuid = UUIDUtils.uuid();
        User user = new User();
        user.setUserName("jiawei");
        rabbitTemplate.convertAndSend(MailConstants.RABBIT_EXCHANGE_NAME,MailConstants.RABBIT_EXCHANGE_NAME,user,new CorrelationData(uuid));
        System.out.println("-------------");
    }

    @Test
    void getBuilding(){
        List<BedBuilding> bedBuildings = buildingMapper.selectList(null);
        bedBuildings.forEach(System.out::println);
    }


}
