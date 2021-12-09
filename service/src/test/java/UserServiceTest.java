import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.UserDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.service.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {UserServiceImpl.class})
public class UserServiceTest {

  @Autowired
  private UserServiceImpl mockUserService;

  @MockBean
  private UserDAO mockUserDAO;

  @MockBean
  private OrderDAO mockOrderDAO;

  @MockBean
  private Mapper mockMapper;

  @Test
  public void findByIdTest() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1);
    UserDTO userDTO = UserDTO.builder().id(1).build();

    when(mockUserDAO.isUserExist(1)).thenReturn(true);
    when(mockUserDAO.findById(1)).thenReturn(userEntity);

    when(mockMapper.toTarget(userEntity, UserDTO.class)).thenReturn(userDTO);
    assertEquals(1, mockUserService.findById(1).getId());

    when(mockUserDAO.isUserExist(1)).thenReturn(false);
    assertThrows(ServiceException.class, () -> mockUserService.findById(1));
  }

  @Test
  public void findByIdOrderTest() {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId(1);
    OrderDTO orderDTO = OrderDTO.builder().id(1).build();

    when(mockUserDAO.isUserExist(1)).thenReturn(true);
    when(mockOrderDAO.findById(1)).thenReturn(orderEntity);

    when(mockMapper.toTarget(orderEntity, OrderDTO.class)).thenReturn(orderDTO);
    assertEquals(1, mockUserService.findUserOrderById( 1,1).getId());

    when(mockUserDAO.isUserExist(1)).thenReturn(false);
    assertThrows(ServiceException.class, () -> mockUserService.findUserOrderById( 1,1));
  }
}
