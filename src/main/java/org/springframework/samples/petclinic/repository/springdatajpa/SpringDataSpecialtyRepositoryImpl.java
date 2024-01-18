/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.repository.springdatajpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Fedoriv
 *
 */

@Profile("spring-data-jpa")
public class SpringDataSpecialtyRepositoryImpl implements SpecialtyRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

    @Autowired
    private VetRepository repositoryVet;

    @Override
    public void delete(Specialty specialty) {
        this.em.remove(this.em.contains(specialty) ? specialty : this.em.merge(specialty));
        Integer specId = specialty.getId();
        List<Vet> modVets = repositoryVet.findAll().stream().filter(v -> v.getSpecialties().contains(specialty)).toList();
        List<Specialty> unm;
        List<Specialty> toSave = new ArrayList<>();
        for (Vet v : modVets){
            unm = v.getSpecialties();
            toSave.addAll(unm);
            toSave.remove(specialty);
            v.clearSpecialties();
            toSave.forEach(v::addSpecialty);
        }
        this.em.createNativeQuery("DELETE FROM vet_specialties WHERE specialty_id=" + specId).executeUpdate();
        this.em.createQuery("DELETE FROM Specialty specialty WHERE id=" + specId).executeUpdate();
    }

}
