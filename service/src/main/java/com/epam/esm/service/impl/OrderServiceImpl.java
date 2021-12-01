package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.entity.GiftCertificateEntity;
import com.epam.esm.dao.entity.OrderEntity;
import com.epam.esm.dao.entity.UserEntity;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.dto.ErrorDto;
import com.epam.esm.service.dto.OrderConvertor;
import com.epam.esm.service.dto.OrderDTO;
import com.epam.esm.service.dto.PurchaseDTO;
import com.epam.esm.service.exception.ServiceListException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final GiftCertificateDAO certificateDAO;
  private final UserDAO userDAO;
  private final OrderDAO orderDAO;
  private final OrderConvertor orderConvertor;

  @Transactional
  @Override
  public OrderDTO insert(PurchaseDTO purchaseDTO) {
    OrderEntity orderEntity = new OrderEntity();
    List<ErrorDto> errors = new ArrayList<>();

    Integer certId = purchaseDTO.getCertId();
    GiftCertificateEntity certificateEntity = certificateDAO.findById(certId);
    if (certificateEntity == null) {
      errors.add(new ErrorDto("purchase.certid.notfound", certId));
    }
    Integer userId = purchaseDTO.getUserId();
    UserEntity userEntity = userDAO.findById(userId);
    if (userEntity == null) {
      errors.add(new ErrorDto("purchase.userid.notfound", userId));
    }
    if (!errors.isEmpty()) {
      throw new ServiceListException(errors, 410);
    }
    orderEntity.setCertificate(certificateEntity);
    orderEntity.setCost(certificateEntity.getPrice());
    orderEntity.setUser(userEntity);

    return orderConvertor.toDto(orderDAO.insert(orderEntity));
  }
}
