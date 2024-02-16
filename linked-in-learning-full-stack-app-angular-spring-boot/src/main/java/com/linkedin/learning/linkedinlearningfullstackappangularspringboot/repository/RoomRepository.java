package com.linkedin.learning.linkedinlearningfullstackappangularspringboot.repository;

import com.linkedin.learning.linkedinlearningfullstackappangularspringboot.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoomRepository extends CrudRepository<RoomEntity, Long> {

   RoomEntity findALLById(Long id);
}
