package com.project.notes.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="notes")
public class Note {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	@NotNull
	@NotEmpty(message="¡Titulo de la nota es requerido!")
	@Size(min=3,max=80,message="El titulo de la nota debe tener entre 3 y 80 caracteres")
	private String title;
	
	@NotNull
	@NotEmpty(message="Contenido de la nota es requerido!")
	@Size(min=3,max=2000,message="El contenido de la nota debe tener entre 3 y 60000 caracteres")
	private String content;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Size(min=1,max=1,message="El valor de archived debe ser de 1 caracter")
	private String archived;
	
	@Transient
	//@NotEmpty(message="¡El id de Note es requerida!")
	private Long idUser;
	
	@JsonManagedReference
	@OneToMany(mappedBy="note",fetch=FetchType.LAZY)
	private List<Categorie> listCategories;
	
	@Column(updatable=false)
	private Date createdAt;
	    
	private Date updatedAt;
	 
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
		
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	public Note() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getArchived() {
		return archived;
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}

	public List<Categorie> getListCategories() {
		return listCategories;
	}

	public void setListCategories(List<Categorie> listCategories) {
		this.listCategories = listCategories;
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

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	
	
}
