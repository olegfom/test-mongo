package com.worldinfolinks.controller;

import com.worldinfolinks.domain.ToDo;
import com.worldinfolinks.domain.ToDoBuilder;
import com.worldinfolinks.repository.ToDoRepository;
import com.worldinfolinks.validation.ToDoValidationError;
import com.worldinfolinks.validation.ToDoValidationErrorBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.springframework.data.redis.core.RedisTemplate;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }    
    
    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getToDos(){
        return ResponseEntity.ok(listAll());
    }
    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id){
        Optional<ToDo> toDo = getById(id);
        if(toDo.isPresent())
            return ResponseEntity.ok(toDo.get());
        return ResponseEntity.notFound().build();    }
    
    @PostMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
        Optional<ToDo> toDo = getById(id);
        if(!toDo.isPresent())
            return ResponseEntity.notFound().build();
        ToDo result = toDo.get();
        result.setCompleted(true);
        result = saveOrUpdate(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header("Location",location.toString()).build();
    }
    //@RequestMapping(value="/todo", method={RequestMethod.POST,RequestMethod.PUT})
    @PostMapping("/todo")
    public ResponseEntity<?> createToDo(@Valid @RequestBody String description, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo result = ToDoBuilder.create().withDescription(description).build();
        result = saveOrUpdate(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
    	delete(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo){
    	delete(toDo.getId());
        return ResponseEntity.noContent().build();
    }
  @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
  
  
  
  
  public List<ToDo> listAll() {
      List<ToDo> products = new ArrayList<>();
      toDoRepository.findAll().forEach(products::add); //fun with Java 8
      return products;
  }

  
  public Optional<ToDo> getById(String id) {
      return toDoRepository.findById(id);
  }

  
  public ToDo saveOrUpdate(ToDo todo) {
      toDoRepository.save(todo);
      return todo;
  }

  
  public void delete(String id) {
      toDoRepository.deleteById(id);
  }

  
//  public ToDo saveOrUpdateProductForm(ToDo todo) {
//      ToDo savedProduct = saveOrUpdate(productFormToProduct.convert(productForm));
//
//      System.out.println("Saved Product Id: " + savedProduct.getId());
//      return savedProduct;
//  }
  
  
  
  
  
}