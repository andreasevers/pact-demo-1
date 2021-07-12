package com.example.pactprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class ZooService {
    private List<Animal> animals = Collections.emptyList();

	public ZooService() {
	}

	@Autowired
	public ZooService(@Value( "${animals.default}" ) boolean defaultAnimal) {
		if (defaultAnimal) {
			animals = new ArrayList<>();
			animals.add(new Animal("Dog"));
		}
	}

	public List<Animal> listAnimals() {
        return animals;
    }

    public void setAnimalNames(String... animalNames) {
        List<Animal> newAnimals = new ArrayList<>();

        for (String name : animalNames) {
            newAnimals.add(new Animal(name));
        }

        this.animals = newAnimals;
    }

}
