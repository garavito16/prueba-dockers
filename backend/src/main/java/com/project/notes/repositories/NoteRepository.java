package com.project.notes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.notes.models.Note;
import com.project.notes.models.User;

@Repository
public interface NoteRepository extends CrudRepository<Note,Long>{
	List<Note> findAll();
	
	List<Note> findByUser(User user);
	
	List<Note> findByUserAndArchivedOrderByCreatedAtDesc(User user,String archived);
}
