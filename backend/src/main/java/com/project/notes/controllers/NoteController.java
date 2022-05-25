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

import com.project.notes.models.Categorie;
import com.project.notes.models.Note;
import com.project.notes.services.CategorieService;
import com.project.notes.services.NoteService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RequestMapping("/api/note")
public class NoteController {
	@Autowired
	private NoteService noteService;

	@Autowired
	private CategorieService categorieService;
	
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public Object apiGetNotesXuser(@RequestBody Note note) {
		try {
			
			List<Note> notes = noteService.getNotesXUser(note.getId(),note.getArchived());
			return new ResponseEntity<>(notes,HttpStatus.OK);
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public Object apiGetNoteXid(@RequestBody Note note) {
		try {
			Note noteAux = noteService.getNoteXid(note.getId());
			return new ResponseEntity<>(noteAux,HttpStatus.OK);
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public Object apiAddNote(@Valid @RequestBody Note newNote,BindingResult result, HttpSession session) {
		try {
			Note note = noteService.createNote(newNote,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-3);
		        error.setMessage("Create note Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
				//tomar las categories
				for(int i =0;i<newNote.getListCategories().size();i++) {
					Categorie aux = new Categorie();
					aux.setName(note.getListCategories().get(i).getName());
					aux.setIdNote(note.getId());
					categorieService.createCategorie(aux,result);
				}
				
		    	return new ResponseEntity<>(note,HttpStatus.CREATED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public Object apiUpdateNote(@Valid @RequestBody Note editNote,BindingResult result, HttpSession session) {
		try {
			Note note = noteService.updateNote(editNote,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-3);
		        error.setMessage("Update note Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
		    	//eliminar las categorias
		    	categorieService.deleteCategorieXnote(note);
		    			
				//registrar las nuevas categories
				for(int i =0;i<editNote.getListCategories().size();i++) {
					Categorie aux = new Categorie();
					aux.setName(editNote.getListCategories().get(i).getName());
					aux.setIdNote(note.getId());
					categorieService.createCategorie(aux,result);
				}
				
		    	return new ResponseEntity<>(note,HttpStatus.ACCEPTED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="/updateArchiveStatus",method=RequestMethod.PUT)
	public Object apiUpdateArchiveStatusNote(@RequestBody Note editNote,BindingResult result) {
		try {
			Note note = noteService.updateArchiveStatusNote(editNote,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-3);
		        error.setMessage("Update note Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
		    	return new ResponseEntity<>(note,HttpStatus.ACCEPTED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public Object apiDeleteNote(@RequestBody Note deleteNote,BindingResult result) {
		try {
			
			//eliminar las categorias
	    	categorieService.deleteCategorieXnote(deleteNote);
	    	
			noteService.deleteNoteXid(deleteNote,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-4);
		        error.setMessage("Delete note Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
		    	return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
		    }
		} catch (Exception e) {
			Error error = new Error();
	        error.setCode(-10);
	        error.setMessage("A error has occurred");
	        error.addCause(e.getMessage());
	        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
}
