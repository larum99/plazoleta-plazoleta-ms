package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign;

import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.UserResponse;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = FeignConstants.NAME_SERVICE, url = FeignConstants.URL_SERVICE)
public interface UserFeignClient {

    @GetMapping(FeignConstants.GET_BY_EMAIL_PATH)
    UserResponse getUserByEmail(@RequestParam(FeignConstants.EMAIL_PARAM) String email);
    @GetMapping(FeignConstants.GET_BY_ID_PATH)
    UserResponse getUserById(@PathVariable(FeignConstants.ID_PATH_VARIABLE) Long id);

}
