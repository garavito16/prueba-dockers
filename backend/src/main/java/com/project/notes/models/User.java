package com.project.notes.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * @author lex_9
 *
 */
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@NotNull
	@NotEmpty(message="¡Nombre del usuario es requerido!")
	@Size(min=3,max=80,message="El nombre debe tener entre 3 y 80 caracteres")
	private String name;
	
	@NotNull
	@NotEmpty(message="¡Correo electrónico es requerido!")
	@Email(message="Ingrese un correo electrónico valido")
	private String email;
	
	@NotNull
	@NotEmpty(message="¡Password es requerido!")
	@Size(min=8,max=80,message="La password debe tener mas de 8 caracteres")
	private String password;
	
	@Transient
	@NotEmpty(message="¡La confirmación de password es requerida!")
	@Size(min=8,max=80,message="La confirmacion de password debe tener mas de 8 caracteres")
	private String confirmPassword;
	
	@JsonManagedReference
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private List<Note> listNotes;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	public User() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<Note> getListNotes() {
		return listNotes;
	}

	public void setListNotes(List<Note> listNotes) {
		this.listNotes = listNotes;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
