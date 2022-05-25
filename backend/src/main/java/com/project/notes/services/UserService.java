package com.project.notes.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.project.notes.models.User;
import com.project.notes.models.Login;
import com.project.notes.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User createUser(User user,BindingResult result) {
		
		Optional<User> userAux = userRepository.findByEmail(user.getEmail());
		if(userAux.isPresent()) {
			result.rejectValue("email", "email_repeat", "El correo electronico ya se encuentra registrado");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "notmatches", "La confirmación de contraseña no coincide");
		}
		
		if(result.hasErrors()) {
		    return null;
		}
		
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return userRepository.save(user);
	}
	
	public User Login(Login login,BindingResult result) {
		Optional<User> userAux = userRepository.findByEmail(login.getEmail());
		
		if(!userAux.isPresent()) {
			result.rejectValue("email", "email_no_existe", "El correo electronico no existe");
			return null;
		}
		
		User user = userAux.get();
		
		if(!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
		    result.rejectValue("password", "Matches", "Password es invalida");
		    return null;
		}
		
		return user;
	}
	
	public User getUserXid(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) return user.get();
		else return null;
	}
	
}
