package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.messaging;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderNotificationPort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.MessagingFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.UserFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.response.CodeVerificationResponse;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.response.UserResponse;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.PhoneNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderNotificationAdapter implements OrderNotificationPort {

    private final MessagingFeignClient messagingFeignClient;
    private final UserFeignClient userFeignClient;

    @Override
    public String notifyClientOrderReady(OrderModel order) {
        UserResponse user = userFeignClient.getUserById(order.getClientId());
        String phoneNumber = user.phoneNumber();
        CodeVerificationResponse response = messagingFeignClient.sendOrderReadyMessage(new PhoneNumberRequest(phoneNumber));
        return response.codeVerification();
    }

    @Override
    public void notifyClientCannotCancel(OrderModel order) {
        UserResponse user = userFeignClient.getUserById(order.getClientId());
        String phoneNumber = user.phoneNumber();
        messagingFeignClient.sendCannotCancelMessage(new PhoneNumberRequest(phoneNumber));
    }
}
