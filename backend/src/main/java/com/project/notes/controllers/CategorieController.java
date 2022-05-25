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
import com.project.notes.services.CategorieService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.POST,RequestMethod.DELETE})
@RequestMapping("/api/categorie")
public class CategorieController {
	@Autowired
	private CategorieService categorieService;
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public Object apiAddCategorie(@Valid @RequestBody Categorie newCategorie,BindingResult result, HttpSession session) {
		try {
			Categorie categorie = categorieService.createCategorie(newCategorie,result);
			
			if (result.hasErrors()) {
		        List<FieldError> errors = result.getFieldErrors();
		        Error error = new Error();
		        error.setCode(-5);
		        error.setMessage("Create categorie Failed");
		        for (FieldError e : errors){
		            error.addCause(e.getDefaultMessage());
		        }
		        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
		    }
		    else
		    {
		    	return new ResponseEntity<>(categorie,HttpStatus.CREATED);
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
	public Object apiDeleteCategorie(@RequestBody Categorie deleteCategorie,BindingResult result, HttpSession session) {
		try {
			
			categorieService.deleteCategorieXid(deleteCategorie,result);
			
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
