package com.project.notes.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.notes.models.Login;
import com.project.notes.models.User;
import com.project.notes.services.UserService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
    private HttpSession httpSession;

	@RequestMapping(value="/register",method=RequestMethod.POST)
	public Object apiAddUser(@Valid @RequestBody User newUser,BindingResult result,HttpSession session) {
		try {
			User user = userService.createUser(newUser,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-1);
		        error.setMessage("Create user Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
		    	user.setConfirmPassword("");
		    	session.setAttribute("id", user.getId());
				session.setAttribute("name", user.getName());
		    	return new ResponseEntity<>(user,HttpStatus.CREATED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object apiLogin(@Valid @RequestBody Login login,BindingResult result,HttpSession session) {
		try {
			User userAux = userService.Login(login,result);
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-2);
		        error.setMessage("Invalid credentials");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
		    }
		    else
		    {
		    	httpSession.setAttribute("id", userAux.getId());
		    	httpSession.setAttribute("name", userAux.getName());
				System.out.println(session.getAttribute("id"));
		    	return new ResponseEntity<>(userAux,HttpStatus.ACCEPTED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public Object apiLogout(HttpSession session) {
		try {
			session.removeAttribute("id");
			session.removeAttribute("name");
			return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
}
