package org.springframework.samples.petclinic.repo.DataJPA.Specialty;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataSpecialtyRepositoryImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.swing.text.html.parser.Entity;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"postgresql,spring-data-jpa"})
public class SpecialtyDataJPATest {

    EntityManager em;

    SpecialtyRepository repo;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:9.6.0"))
        .withUsername("petclinic")
        .withPassword("petclinic")
        .withDatabaseName("postgres-petclinic");

    @Test
    @Sql(value = {"classpath:import-specialty.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteSpecialty(){
        Specialty specialty = repo.findById(1);
        repo.delete(specialty);
        assertNotEquals(specialty, repo.findById(1));
    }


}
