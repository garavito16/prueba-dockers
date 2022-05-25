package com.project.notes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.project.notes.models.Categorie;
import com.project.notes.models.Note;
import com.project.notes.repositories.CategorieRepository;

@Service
public class CategorieService {
	
	@Autowired
	private CategorieRepository categorieRepository;
	
	@Autowired
	private NoteService noteService;
	
	public Categorie createCategorie(Categorie categorie,BindingResult result) {
		Note note = noteService.getNoteXid(categorie.getIdNote());
		if(note == null) {
			result.rejectValue("note", "note_no_existe", "La nota no existe");
			return null;
		}
		
		categorie.setNote(note);
		return categorieRepository.save(categorie);
	}
	
	public Categorie getCategorieXid(Long id) {
		Optional<Categorie> categorie = categorieRepository.findById(id);
		if(categorie.isPresent()) return categorie.get();
		else return null;
	}
	
	public void deleteCategorieXnote(Note note) {
		categorieRepository.deleteByNote(note);
	}
	
	public void deleteCategorieXid(Categorie categorie, BindingResult result) {
		Categorie categorieAux = getCategorieXid(categorie.getId());
		
		if(categorieAux == null) {
			result.rejectValue("id", "categorie_not_exists", "La categoria no existe");
		} else {
			categorieRepository.deleteById(categorie.getId());
		}
	}
}
