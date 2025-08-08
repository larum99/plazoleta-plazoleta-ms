package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign;

import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.PhoneNumberRequest;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = FeignConstants.MESSAGING_NAME_SERVICE, url = FeignConstants.MESSAGING_URL_SERVICE)
public interface MessagingFeignClient {

    @PostMapping(FeignConstants.SEND_ORDER_READY_MESSAGE_PATH)
    void sendOrderReadyMessage(@RequestBody PhoneNumberRequest request);

}
