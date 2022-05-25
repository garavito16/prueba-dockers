package com.project.notes.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Login {
	@NotEmpty(message="¡Email es requerido!")
	@Email(message="Por favor ingrese un email correcto")
	private String email;
	
	@NotEmpty(message="¡Contraseña es requerida!")
	@Size(min=8,max=80,message="La password debe tener mas de 8 caracteres")
	private String password;

	public String getEmail() {
		return email;
	}

	public Login() {
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
