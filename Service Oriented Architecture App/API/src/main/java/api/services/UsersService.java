package api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;

import api.dtos.UserDto;

public interface UsersService {
	
	@GetMapping("/users")
	List<UserDto> getUsers();
	
	@GetMapping("/users/email/{email}")
	UserDto getUser(@PathVariable String email);
	
	@PostMapping("/users")
	ResponseEntity<?> createUser(@RequestBody UserDto dto, @RequestHeader("Authorization") String authorization);
	
	@PutMapping("/users")
	ResponseEntity<?> updateUser(@RequestBody UserDto dto, @RequestHeader("Authorization") String authorization);
	
	@DeleteMapping("/users/id/{id}")
	ResponseEntity<?> deleteUser(@PathVariable int id);

}