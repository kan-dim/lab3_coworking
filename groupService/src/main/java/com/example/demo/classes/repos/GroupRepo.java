
package com.example.demo.classes.repos;

import com.example.demo.classes.excursion.Сluster;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface GroupRepo extends CrudRepository<Сluster, String> {
    List<Сluster> getGroupByManager(UUID id);

    List<Сluster> getById(UUID id);

    List<Сluster> getGroupByExcursion(UUID id);

    List<Сluster> getExcursionById(UUID id);

    void deleteById(UUID id);

}