package com.watermyplants.backend.Repositories;

import com.watermyplants.backend.Models.Plant;
import org.springframework.data.repository.CrudRepository;

public interface PlantRepository extends CrudRepository<Plant, Long>
{
}
