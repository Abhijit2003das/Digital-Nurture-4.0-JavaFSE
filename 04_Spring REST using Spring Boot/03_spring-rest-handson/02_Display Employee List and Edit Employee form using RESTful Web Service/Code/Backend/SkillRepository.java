package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Skill; // Import your Skill entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import Optional

@Repository // Marks this interface as a Spring Data JPA repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    // JpaRepository<Entity, IdType>
    // Skill: The entity class this repository manages
    // Integer: The data type of the primary key (ID) of the Skill entity (since we changed it to int)

    /**
     * Custom method to find a Skill entity by its name.
     * Spring Data JPA will automatically implement this method based on the method name.
     *
     * @param name The name of the skill to find.
     * @return An Optional containing the Skill if found, or an empty Optional if not.
     */
    Optional<Skill> findByName(String name);
}