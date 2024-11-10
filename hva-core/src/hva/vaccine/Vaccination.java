package hva.vaccine;

import hva.animal.Specie;
import hva.employee.Veterinarian;

public class Vaccination {
    private Vaccine _vaccine;
    private Veterinarian _veterinarian;
    private Specie _specie;

    public Vaccination(Vaccine vaccine, Veterinarian veterinarian, Specie specie) {
        _vaccine = vaccine;
        _veterinarian = veterinarian;
        _specie = specie;
    }

    //REGISTO-VACINA|idVacina|idVeterinário|idEspécie
    @Override
    public String toString() {
        return "REGISTO-VACINA|" + _vaccine.getKey() + "|" + _veterinarian.getKey() +
        "|" + _specie.getKey();
    }
}