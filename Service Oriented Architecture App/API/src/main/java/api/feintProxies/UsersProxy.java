package api.feintProxies;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import api.dtos.UserDto;

@org.springframework.cloud.openfeign.FeignClient("users-service")
public interface UsersProxy {
	
	  @GetMapping("/users/email/{email}")
	  public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email);
}
