package hva.satisfaction;

import java.io.Serial;
import java.io.Serializable;

import hva.animal.Animal;
import hva.habitat.Habitat;

public class AnimalSatisfactionCalculator implements SatisfactionCalculator<Animal> {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    @Override
    public double calculateSatisfaction(Animal animal) {
        Habitat habitat = animal.getHabitat();
        
        int sameSpecieAnimals = habitat.sameSpecieAnimals(animal);        
        int differentSpecieAnimals = habitat.differentSpecieAnimals(animal);
        int area = habitat.getArea();
        int population = habitat.getPopulation();
        int habitatInfluence = habitat.getHabitatInfluence(animal);

        return 20 + (3 * sameSpecieAnimals) - (2 * differentSpecieAnimals) +
        (area / population) + habitatInfluence;
    }
}
