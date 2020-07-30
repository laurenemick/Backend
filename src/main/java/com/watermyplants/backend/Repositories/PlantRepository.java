package com.watermyplants.backend.Repositories;

import com.watermyplants.backend.Models.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Long>
{
}
