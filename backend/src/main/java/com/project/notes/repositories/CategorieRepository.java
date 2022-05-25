package com.project.notes.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.project.notes.models.Categorie;
import com.project.notes.models.Note;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie,Long>{
	List<Categorie> findAll();
	
	@Transactional
	void deleteByNote(Note note);
}
