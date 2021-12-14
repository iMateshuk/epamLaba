package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.ErrorDTO;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.Mapper;
import com.epam.esm.service.util.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Order
 * Use for business logic of APP
 *
 * @author Ivan Matsiashuk
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderDAO orderDAO;
  private final GiftCertificateDAO certificateDAO;
  private final UserDAO userDAO;
  private final Validator validator;
  private final Mapper mapper;

  @Override
  public OrderDTO findById(Integer id) {
    OrderEntity orderEntity = orderDAO.findById(id);
    if (orderEntity == null) {
      throw new ServiceException(new ErrorDTO("order.search.error", id), 401);
    }
    return mapper.toTarget(orderEntity, OrderDTO.class);
  }

  @Transactional
  @Override
  public OrderDTO insert(PurchaseDTO purchaseDTO) {
    Integer certId = purchaseDTO.getCertId();
    GiftCertificateEntity certificateEntity = certificateDAO.findById(certId);

    Integer userId = purchaseDTO.getUserId();
    UserEntity userEntity = userDAO.findById(userId);

    validator.validateEntitiesOfPurchaseDto(certificateEntity, certId, userEntity, userId);

    OrderEntity orderEntity = OrderEntity.builder()
        .certificate(certificateEntity).cost(certificateEntity.getPrice()).user(userEntity).build();
    return mapper.toTarget(orderDAO.insert(orderEntity), OrderDTO.class);
  }
}
