package com.worldinfolinks.repository;


import com.worldinfolinks.domain.ToDo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ToDoRepository extends MongoRepository<ToDo, String> {    
}