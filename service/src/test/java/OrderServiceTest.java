import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.dto.GiftCertificateDTO;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.util.ServiceConvertor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {OrderServiceImpl.class})
public class OrderServiceTest {

  @Autowired
  private OrderServiceImpl mockOrderService;

  @MockBean
  private UserDAO mockUserDAO;

  @MockBean
  private GiftCertificateDAO mockCertificateDAO;

  @MockBean
  private OrderDAO mockOrderDAO;

  @MockBean
  private ServiceConvertor mockConvertor;

  @Test
  public void insertByNameTest() {
    UserEntity userEntity = new UserEntity(1,"name","password", new ArrayList<>());
    GiftCertificateEntity certificateEntity = new GiftCertificateEntity();
    certificateEntity.setPrice(99F);
    PurchaseDTO purchaseDTO = PurchaseDTO.builder().certId(1).userId(1).build();

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setCertificate(certificateEntity);
    orderEntity.setCost(certificateEntity.getPrice());
    orderEntity.setUser(userEntity);

    OrderDTO orderDTO = OrderDTO.builder().id(1).cost(99F).certificate(new GiftCertificateDTO()).build();

    when(mockUserDAO.findById(purchaseDTO.getUserId())).thenReturn(userEntity);
    when(mockCertificateDAO.findById(purchaseDTO.getCertId())).thenReturn(certificateEntity);

    when(mockOrderDAO.insert(orderEntity)).thenReturn(orderEntity);
    when(mockConvertor.toTarget(orderEntity, OrderDTO.class)).thenReturn(orderDTO);

    assertEquals(orderDTO.getId(), mockOrderService.insert(purchaseDTO).getId());
    assertEquals(orderDTO.getCost(), mockOrderService.insert(purchaseDTO).getCost());
  }
}
