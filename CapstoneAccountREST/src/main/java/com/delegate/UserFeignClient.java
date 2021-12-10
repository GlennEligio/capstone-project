package com.delegate;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.User;

import feign.HeaderMap;

@FeignClient(name="user-service")
public interface UserFeignClient {

	@RequestMapping(method=RequestMethod.GET, value="/users/user/{username}")
	public ResponseEntity<User> findUser(@PathVariable String username, @RequestHeader("Authorization") String header);
}
