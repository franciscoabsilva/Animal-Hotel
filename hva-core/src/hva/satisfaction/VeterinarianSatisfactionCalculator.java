package hva.satisfaction;

import java.io.Serial;
import java.io.Serializable;

import hva.employee.Veterinarian;
import hva.animal.Specie;

public class VeterinarianSatisfactionCalculator implements SatisfactionCalculator<Veterinarian> {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;
        
    @Override
    public double calculateSatisfaction(Veterinarian veterinarian) {        
        double work = 0.0;
        for (Specie specie : veterinarian.getSpecies()) {
            int population = specie.getPopulation();
            int nVeterinarians = specie.countVeterinarians();
            work += (population / nVeterinarians);
        }
        return 20 - work;
    }
}
