package com.project.notes.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.project.notes.models.Note;
import com.project.notes.models.User;
import com.project.notes.repositories.NoteRepository;

@Service
public class NoteService {
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserService userService;
	
	public List<Note> getNotesXUser(Long idUser,String archived) {
		User user = userService.getUserXid(idUser);
		
		return noteRepository.findByUserAndArchivedOrderByCreatedAtDesc(user,archived);
	}
	
	
	public Note createNote(Note note,BindingResult result) {
		if(result.hasErrors()) {
			return null;
		}
		
		User user = userService.getUserXid(note.getIdUser());
		
		if(user == null) {
			result.rejectValue("note", "user_no_existe", "El usuario no existe");
			return null;
		}
		note.setUser(user);
		note.setArchived("0");
		return noteRepository.save(note);
	}
	
	public Note updateNote(Note note,BindingResult result) {
		if(result.hasErrors()) {
		    return null;
		}
		
		Note noteAux = getNoteXid(note.getId());
		noteAux.setTitle(note.getTitle());
		noteAux.setContent(note.getContent());
		return noteRepository.save(noteAux);
	}
	
	public Note updateArchiveStatusNote(Note note,BindingResult result) {
		if(note.getId() == 0) {
			result.rejectValue("id", "note_id_invalid", "El ID de la nota debe ser valida");
			return null;
		}
		
		if(note.getArchived() == "") {
			result.rejectValue("archived", "note_status_archived", "El valor de archived debe ser valido");
			return null;
		}
		
		Note noteAux = getNoteXid(note.getId());
		
		if(noteAux == null) {
			result.rejectValue("id", "note_not_exists", "La nota no existe");
			return null;
		}
		
		noteAux.setArchived(note.getArchived());
		return noteRepository.save(noteAux);
	}
	
	public Note getNoteXid(Long id) {
		Optional<Note> note = noteRepository.findById(id);
		if(note.isPresent()) return note.get();
		else return null;
	}
	
	public void deleteNoteXid(Note note, BindingResult result) {
		Note noteAux = getNoteXid(note.getId());
		
		if(noteAux == null) {
			result.rejectValue("id", "note_not_exists", "La nota no existe");
		} else {
			noteRepository.deleteById(note.getId());
		}
	}
}
