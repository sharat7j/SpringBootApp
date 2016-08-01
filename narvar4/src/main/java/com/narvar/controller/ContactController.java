package com.narvar.controller;
import com.narvar.domain.Contacts;
import com.narvar.domain.ContactRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository repository;

    @RequestMapping(value="", method=RequestMethod.GET)
    @Cacheable("contactsCache")
    public String listPosts(Model model) {
        model.addAttribute("contacts", repository.findAll());
        return "contacts/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/contacts");
    }
    
    @RequestMapping(value = "/deleteUser/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteUser(@PathVariable String name) {
    	List<Contacts> contacts = repository.findByName(name);
        if(contacts!=null){
        	Contacts contact=contacts.get(0);
        
        	repository.delete(contact);
        	return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "contacts/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("profession") String profession) {
    	repository.save(new Contacts(name,email,profession));
        return new ModelAndView("redirect:/contacts");
    }
    //for getting json response
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity<?> createGet(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("profession") String profession) {
    	Contacts contact=new Contacts(name,email,profession);
    	repository.save(contact);
    	return new ResponseEntity<>(repository.save(contact), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("contact_id") long id,
    		@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("profession") String profession) {
        Contacts contacts = repository.findOne(id);
        contacts.setEmail(email);
        contacts.setName(name);
        contacts.setProfession(profession);
        repository.save(contacts);
        return new ModelAndView("redirect:/contacts");
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(
    		@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("profession") String profession) {
        List<Contacts> contacts = repository.findByName(name);
        Contacts contact=contacts.get(0);
        if(contacts!=null){
        if(email!=null){
        	contact.setEmail(email);
        }
        if(name!=null){
        	contact.setName(name);
        }
        if(profession!=null){
        	contact.setProfession(profession);
        }
        return new ResponseEntity<>(repository.save(contact), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    @Cacheable("contactsCache")
    public String edit(@PathVariable long id,
                       Model model) {
        Contacts contact = repository.findOne(id);
        model.addAttribute("contact", contact);
        return "contacts/edit";
    }


}

