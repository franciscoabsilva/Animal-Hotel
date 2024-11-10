package hva;

import java.io.Serial;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hva.animal.*;
import hva.employee.*;
import hva.habitat.*;
import hva.season.*;
import hva.vaccine.*;

import hva.exceptions.DuplicateAnimalException;
import hva.exceptions.DuplicateEmployeeException;
import hva.exceptions.DuplicateHabitatException;
import hva.exceptions.DuplicateVaccineException;
import hva.exceptions.DuplicateTreeException;
import hva.exceptions.ImportFileException;
import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpecieException;
import hva.exceptions.UnknownTreeException;
import hva.exceptions.UnknownVaccineException;
import hva.exceptions.UnknownVeterinarianException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VeterinarianCantVaccinateException;
import hva.exceptions.WrongVaccineException;

/**
 * A Hotel has species, animals, habitats, employees, vaccines, and trees.
 */
public class Hotel implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** Species. */
    private Map<String, Specie> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Animals. */
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Habitats. */
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Employees. */
    private Map<String, Employee> _employees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Vaccines. */
    private Map<String, Vaccine> _vaccines = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /** Trees. */
    private Map<String, Tree> _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private List<Vaccination> _vaccinationHistory = new ArrayList<>();

    private List<Vaccination> _incorrectVaccinationsHistory = new ArrayList<>();

    private Season _currentSeason = new Spring();

    /** Hotel object has been changed. */ 
    private boolean _changed = false;

    /**
     * Registers a new species.
     * 
     * @param fields
     */
    public void registerSpecie(String... fields) {
        String key = fields[1];
        String name = fields[2];

        Specie specie = new Specie(key, name);

        addSpecie(key, specie);
        changed();
    }

    /**
     * Adds a species to the collection.
     * 
     * @param key
     * @param specie
     */
    public void addSpecie(String key, Specie specie) {
        _species.put(key, specie);
    }

    /**
     * Retrieves a species by its key.
     * 
     * @param key
     * @return The specie associated with the key.
     */
    public Specie getSpecie(String key) {
        return _species.get(key);
    }

    /**
     * Registers a new animal with the given details.
     * 
     * @param fields
     * @throws DuplicateAnimalException If a animal with the same key already exists.
     */

    public void registerAnimal(String... fields) throws DuplicateAnimalException,
                                                        UnknownHabitatException,
                                                        UnknownSpecieException {
        String key = fields[1];
        String name = fields[2];
        String specieKey = fields[3];
        String habitatKey = fields[4];

        Specie specie = getSpecie(specieKey);
        if (specie == null) {
            throw new UnknownSpecieException(specieKey);
        }
        
        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        }

        assertNewAnimal(key);

        Animal animal = new Animal(key, name, specie, habitat);

        addAnimal(key, animal);
        addAnimalToSpecie(specie, animal);
        addAnimalToHabitat(habitat, animal);
        changed();
    }

    /**
     * Checks if an animal with the given key already exists in the collection.
     * 
     * @param key
     * @throws DuplicateAnimalException
     */
    public void assertNewAnimal(String key) throws DuplicateAnimalException {
        if (_animals.containsKey(key))
            throw new DuplicateAnimalException(key);
    }

    /**
     * Adds an animal to the collection.
     * 
     * @param key
     * @param animal
     */
    public void addAnimal(String key, Animal animal) {
        _animals.put(key, animal);
    }

    /**
     * Adds an animal to the specified specie.
     * 
     * @param specie
     * @param animal 
     */
    public void addAnimalToSpecie(Specie specie, Animal animal) {
        specie.addAnimal(animal);
    }

    /**
     * Adds an animal to the specified habitat.
     * 
     * @param habitat
     * @param animal 
     */
    public void addAnimalToHabitat(Habitat habitat, Animal animal) {
        habitat.addAnimal(animal);
    }

    /**
     * Displays all animals in the collection.
     * 
     * @return A string representation of all animals, each on a new line.
     */
    public String showAllAnimals() {
        return _animals.values().stream()
                .map(Animal::toString)
                .collect(Collectors.joining("\n"));
    }

    public void transferAnimalToHabitat(String... fields) throws UnknownAnimalException,
                                                                 UnknownHabitatException {
        String animalKey = fields[0];
        String habitatKey = fields[1];

        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new UnknownAnimalException(animalKey);
        }

        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        }

        Habitat currentHabitat = animal.getHabitat();
        currentHabitat.removeAnimal(animal);

        animal.setHabitat(habitat);
        habitat.addAnimal(animal);
        
        changed();
    }

    public Animal getAnimal(String key) {
        return _animals.get(key);
    }

    public String showSatisfactionOfAnimal(String... fields) throws UnknownAnimalException {
        String animalKey = fields[0];

        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new UnknownAnimalException(animalKey);
        }

        return String.valueOf(Math.round(animal.calculateSatisfaction()));
    }

    /**
     * Registers a new habitat with the given details.
     * 
     * @param fields The details of the habitat to be registered (key, name, area).
     * @throws DuplicateHabitatException If a habitat with the same key already exists.
     * @throws UnknownTreeException     If any of the specified trees do not exist.
     */
    public void registerHabitat(String... fields) throws DuplicateHabitatException,
                                                         UnknownTreeException {
        String key = fields[1];
        String name = fields[2];
        int area = Integer.parseInt(fields[3]);

        assertNewHabitat(key);

        Habitat habitat;

        // If there are trees associated with the habitat
        if (fields.length == 5) {
            String[] treeKeys = fields[4].replaceAll("\\s+", "").split(",");
            Tree[] trees = new Tree[treeKeys.length];
            
            for (int i = 0; i < treeKeys.length; i++) {
                trees[i] = getTree(treeKeys[i]);
                if (trees[i] == null) {
                    throw new UnknownTreeException(treeKeys[i]);
                }
            }

            habitat = new Habitat(key, name, area, trees);
        } else {
            habitat = new Habitat(key, name, area);
        }

        addHabitat(key, habitat);
        changed();
    }

    /**
     * Checks if a habitat with the given key already exists in the collection.
     * 
     * @param key 
     * @throws DuplicateHabitatException
     */
    public void assertNewHabitat(String key) throws DuplicateHabitatException {
        if (_habitats.containsKey(key))
            throw new DuplicateHabitatException(key);
    }

    /**
     * Adds a habitat to the collection.
     * 
     * @param key 
     * @param habitat
     */
    public void addHabitat(String key, Habitat habitat) {
        _habitats.put(key, habitat);
    }

    /**
     * Displays all habitats in the collection.
     * 
     * @return A string representation of all habitats, each on a new line.
     */
    public String showAllHabitats() {
        return _habitats.values().stream()
                .map(Habitat::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Retrieves the habitat associated with the given key.
     * 
     * @param key 
     * @return The habitat associated with the given key.
     */
    public Habitat getHabitat(String key) {
        return _habitats.get(key);
    }

    public void changeHabitatInfluence(String... fields) throws UnknownHabitatException,
                                                                UnknownSpecieException {
        String habitatKey = fields[0];
        String specieKey = fields[1];
        String influence = fields[2];


        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        }

        Specie specie = getSpecie(specieKey);
        if (specie == null) {
            throw new UnknownSpecieException(specieKey);
        }

        habitat.removeInfluenceSpecie(specie);
        
        switch (influence) {
            case "POS" -> habitat.addPositiveInfluenceSpecie(specie);
            case "NEG" -> habitat.addNegativeInfluenceSpecie(specie);
        }
        
        changed();
    }

    public void changeHabitatArea(String... fields) throws UnknownHabitatException {
        String key = fields[0];
        int area = Integer.parseInt(fields[1]);

        Habitat habitat = getHabitat(key);
        if (habitat == null) {
            throw new UnknownHabitatException(key);
        }

        habitat.setArea(area);
        
        changed();
    }


    /**
     * Registers a new tree with the given details.
     * 
     * @param fields The details of the tree (key, name, age, difficulty, and type).
     * @throws DuplicateTreeException If a tree with the same key already exists.
     */
    public void registerTree(String... fields) throws DuplicateTreeException {
        String key = fields[1];
        String type = fields[5];

        assertNewTree(key);

        switch (type) {
            case "PERENE" -> registerEvergreen(fields);
            case "CADUCA" -> registerDeciduous(fields);
        }

        changed();
    }

    /**
     * Checks if a tree with the given key already exists in the collection.
     * 
     * @param key
     * @throws DuplicateTreeException
     */
    public void assertNewTree(String key) throws DuplicateTreeException {
        if (_trees.containsKey(key))
            throw new DuplicateTreeException(key);
    }

    /**
     * Adds a tree to the collection.
     * 
     * @param key 
     * @param tree
     */
    public void addTree(String key, Tree tree) {
        _trees.put(key, tree);
    }

    /**
     * Retrieves a tree by its key.
     * 
     * @param key
     * @return The tree associated with the key.
     */
    public Tree getTree(String key) {
        return _trees.get(key);
    }

    public void registerEvergreen(String... fields) {
        String key = fields[1];
        String name = fields[2];
        int age = Integer.parseInt(fields[3]);
        int difficulty = Integer.parseInt(fields[4]);

        Evergreen tree = new Evergreen(key, name, age, difficulty);

        tree.setCurrentSeason(_currentSeason);

        addTree(key, tree);
    }

    public void registerDeciduous(String... fields) {
        String key = fields[1];
        String name = fields[2];
        int age = Integer.parseInt(fields[3]);
        int difficulty = Integer.parseInt(fields[4]);

        Deciduous tree = new Deciduous(key, name, age, difficulty);

        tree.setCurrentSeason(_currentSeason);

        addTree(key, tree);
    }

    public void addTreeToHabitat(String... fields) throws UnknownHabitatException,
                                                          DuplicateTreeException {
        String habitatKey = fields[0];
        String key = fields[1];

        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        }

        registerTree(fields);

        Tree tree = getTree(key);
        
        habitat.addTree(tree);
    }

    public String showTree(String key) {
        Tree tree = getTree(key);
        return tree.toString();
    }

    public String showAllTreesInHabitat(String... fields) throws UnknownHabitatException {
        String habitatKey = fields[0];

        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        }

        return habitat.showAllTrees();
    }

    /**
     * Registers a new vaccine with the given details.
     * 
     * @param fields The details of the vaccine to be registered 
     *              (key, name, and optionally a comma-separated list of species).
     * @throws DuplicateVaccineException If a vaccine with the same key already exists.
     * @throws UnknownSpecieException    If any of the specified species do not exist.
     */
    public void registerVaccine(String... fields) throws DuplicateVaccineException,
                                                         UnknownSpecieException {
        String key = fields[1];
        String name = fields[2];

        assertNewVaccine(key);

        Vaccine vaccine;
        
        // If there are species associated with the vaccine
        if (fields.length == 4) {
            String[] speciesKeys = fields[3].replaceAll("\\s+", "").split(",");
            Specie[] vaccinableSpecies = new Specie[speciesKeys.length];
            
            for (int i = 0; i < speciesKeys.length; i++) {
                vaccinableSpecies[i] = getSpecie(speciesKeys[i]);
                if (vaccinableSpecies[i] == null) {
                    throw new UnknownSpecieException(speciesKeys[i]);
                }
            }

            vaccine = new Vaccine(key, name, vaccinableSpecies);
        } else {
            vaccine = new Vaccine(key, name);
        }
        
        addVaccine(key, vaccine);
        changed();
    }

    /**
     * Checks if a vaccine with the given key already exists in the collection.
     * 
     * @param key 
     * @throws DuplicateVaccineException 
     */
    public void assertNewVaccine(String key) throws DuplicateVaccineException {
        if (_vaccines.containsKey(key))
            throw new DuplicateVaccineException(key);
    }

    /**
     * Adds a vaccine to the collection.
     * 
     * @param key    
     * @param vaccine 
     */
    public void addVaccine(String key, Vaccine vaccine) {
        _vaccines.put(key, vaccine);
    }

    /**
     * Displays all vaccines in the collection.
     * 
     * @return A string representation of all vaccines, each on a new line.
     */
    public String showAllVaccines() {
        return _vaccines.values().stream()
                .map(Vaccine::toString)
                .collect(Collectors.joining("\n"));
    }

    public void vaccinateAnimal(String... fields) throws UnknownVaccineException,
                                                         UnknownEmployeeException,
                                                         UnknownAnimalException,
                                                         UnknownVeterinarianException,
                                                         VeterinarianCantVaccinateException,
                                                         WrongVaccineException {
        String vaccineKey = fields[0];
        String veterinarianKey = fields[1];
        String animalKey = fields[2];

        Vaccine vaccine = getVaccine(vaccineKey);
        if (vaccine == null) {
            throw new UnknownVaccineException(vaccineKey);
        }

        Employee employee = getEmployee(veterinarianKey);
        if (employee == null) {
            throw new UnknownEmployeeException(veterinarianKey);
        }

        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new UnknownAnimalException(animalKey);
        }

        if (!(employee instanceof Veterinarian)) {
            throw new UnknownVeterinarianException(veterinarianKey);
        }
        Veterinarian veterinarian = (Veterinarian) employee;

        Specie specie = animal.getSpecie();
        if (!veterinarian.hasResponsability(specie)) {
            throw new VeterinarianCantVaccinateException(veterinarianKey, specie.getKey());
        }

        changed();

        Vaccination vaccination = new Vaccination(vaccine, veterinarian, specie);

        if (vaccine.canVaccinate(specie)) {
            animal.addCorrectVaccination(vaccination);
            vaccine.addVaccination(vaccination);
            veterinarian.addVaccination(vaccination);
            _vaccinationHistory.add(vaccination);
        } else {
            int damage = calculateDamage(vaccine, animal);
            animal.addIncorrectVaccination(vaccination, damage);
            vaccine.addVaccination(vaccination);
            veterinarian.addVaccination(vaccination);
            _vaccinationHistory.add(vaccination);
            _incorrectVaccinationsHistory.add(vaccination);
            throw new WrongVaccineException(vaccineKey, animalKey);
        }
    }

    public int calculateDamage(Vaccine vaccine, Animal animal) {
        int damage = 0;
        for (Specie specie : vaccine.getVaccinableSpecies()) {
            int commonChars = commonChars(animal.getSpecie().getName(), specie.getName());
            int maxChars = Math.max(specie.getName().length(), animal.getSpecie().getName().length());
            int specieDamage = maxChars - commonChars;
            if (specieDamage > damage) {
                damage = specieDamage;
            }
        }
        return damage;
    }

    public int commonChars(String s1, String s2) {
        int[] charCount1 = new int[256];
        int[] charCount2 = new int[256];
    
        for (int i = 0; i < s1.length(); i++) {
            charCount1[s1.charAt(i)]++;
        }
    
        for (int i = 0; i < s2.length(); i++) {
            charCount2[s2.charAt(i)]++;
        }
    
        int common = 0;
        for (int i = 0; i < 256; i++) {
            common += Math.min(charCount1[i], charCount2[i]);
        }
    
        return common;
    }

    public Vaccine getVaccine(String key) {
        return _vaccines.get(key);
    }

    public String showVaccinations() {
        return _vaccinationHistory.stream()
                .map(Vaccination::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Registers a new employee with the given details.
     * 
     * @param fields The details of the employee 
     *               (employee type, key, name, 
     *               and optionally a comma-separated list of habitats or species).
     * @throws DuplicateEmployeeException If an employee with the same key already exists.
     * @throws UnknownSpecieException     If a specified specie does not exist.
     * @throws UnknownHabitatException    If a specified habitat does not exist.
     */
    public void registerEmployee(String... fields) throws DuplicateEmployeeException,
                                                          UnknownSpecieException,
                                                          UnknownHabitatException {
        String type = fields[0];
        String key = fields[1];

        assertNewEmployee(key);

        switch (type) {
            case "TRT", "TRATADOR" -> registerHandler(fields);
            case "VET", "VETERINÁRIO" -> registerVeterinarian(fields);
        }        

        changed();
    }

    /**
     * Checks if an employee with the given key already exists in the collection.
     * 
     * @param key
     * @throws DuplicateEmployeeException
     */
    public void assertNewEmployee(String key) throws DuplicateEmployeeException {
        if (_employees.containsKey(key))
            throw new DuplicateEmployeeException(key);
    }

    /**
     * Adds an employee to the collection.
     * 
     * @param key
     * @param employee
     */
    public void addEmployee(String key, Employee employee) {
        _employees.put(key, employee);
    }

    /**
     * Displays all employees in the collection.
     * 
     * @return A string representation of all employees, each on a new line.
     */
    public String showAllEmployees() {
        return _employees.values().stream()
                .map(Employee::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addEmployeeResponsability(String... fields) throws UnknownEmployeeException,
                                                                   UnknownHabitatException,
                                                                   UnknownSpecieException {
        String employeeKey = fields[0];
        String responsabilityKey = fields[1];

        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new UnknownEmployeeException(employeeKey);
        }

        if (employee instanceof Handler) {
            Handler handler = (Handler) employee;
            Habitat responsability = getHabitat(responsabilityKey);
            if (responsability == null) {
                throw new UnknownHabitatException(responsabilityKey);
            }
            if (!handler.hasResponsability(responsability)) {
                handler.addResponsability(responsability);
                responsability.addHandler(handler);
            }
        } else if (employee instanceof Veterinarian) {
            Veterinarian veterinarian = (Veterinarian) employee;
            Specie responsability = getSpecie(responsabilityKey);
            if (responsability == null) {
                throw new UnknownSpecieException(responsabilityKey);
            }
            if (!veterinarian.hasResponsability(responsability)) {
                veterinarian.addResponsability(responsability);
                responsability.addVeterinarian(veterinarian);
            }
        }

        changed();
    }



    public void removeEmployeeResponsability(String... fields) throws UnknownEmployeeException,
                                                                      UnknownHabitatException,
                                                                      UnknownSpecieException {
        String employeeKey = fields[0];
        String responsabilityKey = fields[1];

        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new UnknownEmployeeException(employeeKey);
        }

        if (employee instanceof Handler) {
            Handler handler = (Handler) employee;
            Habitat responsability = getHabitat(responsabilityKey);
            if (responsability == null || !handler.hasResponsability(responsability)) {
                throw new UnknownHabitatException(responsabilityKey);
            }
            handler.removeResponsability(responsability);
            responsability.removeHandler(handler);
        } else if (employee instanceof Veterinarian) {
            Veterinarian veterinarian = (Veterinarian) employee;
            Specie responsability = getSpecie(responsabilityKey);
            if (responsability == null || !veterinarian.hasResponsability(responsability)) {
                throw new UnknownSpecieException(responsabilityKey);
            }
            veterinarian.removeResponsability(responsability);
            responsability.removeVeterinarian(veterinarian);
        }

        changed();
    }

    public Employee getEmployee(String key) {
        return _employees.get(key);
    }

    public String showSatisfactionOfEmployee(String... fields) throws UnknownEmployeeException {
        String employeeKey = fields[0];

        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new UnknownEmployeeException(employeeKey);
        }

        return String.valueOf(Math.round(employee.calculateSatisfaction()));
    }


    /**
     * Registers a new handler with the given details.
     * 
     * @param fields The details of the handler 
     *               (key, name, and optionally a comma-separated list of habitats).
     * @throws UnknownHabitatException If any of the specified habitats do not exist.
     */
    public void registerHandler(String... fields) throws UnknownHabitatException {
        String key = fields[1];
        String name = fields[2];

        Handler handler;

        // If there are habitats associated with the handler
        if (fields.length == 4) {
            String[] habitatKeys = fields[3].replaceAll("\\s+", "").split(",");
            Habitat[] habitats = new Habitat[habitatKeys.length];

            for (int i = 0; i < habitatKeys.length; i++) {
                habitats[i] = getHabitat(habitatKeys[i]);
                if (habitats[i] == null) {
                    throw new UnknownHabitatException(habitatKeys[i]);
                }
            }

            handler = new Handler(key, name, habitats);

            for (Habitat habitat : habitats) {
                habitat.addHandler(handler);
            }
        } else {
            handler = new Handler(key, name);
        }

        addEmployee(key, handler);

    }

    /**
     * Registers a new veterinarian with the given details.
     * 
     * @param fields The details of the veterinarian 
     *               (key, name, and optionally a comma-separated list of species).
     * @throws UnknownSpecieException If any of the specified species do not exist.
     */
    public void registerVeterinarian(String... fields) throws UnknownSpecieException {
        String key = fields[1];
        String name = fields[2];

        Veterinarian veterinarian;

        // If there are species associated with the veterinarian
        if (fields.length == 4) {
            String[] specieKeys = fields[3].replaceAll("\\s+", "").split(",");
            Specie[] species = new Specie[specieKeys.length];

            for (int i = 0; i < specieKeys.length; i++) {
                species[i] = getSpecie(specieKeys[i]);
                if (species[i] == null) {
                    throw new UnknownSpecieException(specieKeys[i]);
                }
            }

            veterinarian = new Veterinarian(key, name, species);

            for (Specie specie : species) {
                specie.addVeterinarian(veterinarian);
            }
        } else {
            veterinarian = new Veterinarian(key, name);
        }

        addEmployee(key, veterinarian);
    }

    public String showAllAnimalsInHabitat(String... fields) throws UnknownHabitatException {
        String habitatKey = fields[0];

        Habitat habitat = getHabitat(habitatKey);
        if (habitat == null) {
            throw new UnknownHabitatException(habitatKey);
        } 
                        
        return habitat.showAllAnimals();
    }

    public String showMedicalActsByVeterinarian(String... fields) throws UnknownEmployeeException,
                                                                   UnknownVeterinarianException {
        String employeeKey = fields[0];
        
        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new UnknownEmployeeException(employeeKey);
        }

        if (!(employee instanceof Veterinarian)) {
            throw new UnknownVeterinarianException(employeeKey);
        }

        Veterinarian veterinarian = (Veterinarian) employee;

        return veterinarian.showMedicalActs();
    }

    public String showMedicalActsOnAnimal(String... fields) throws UnknownAnimalException {
        String animalKey = fields[0];

        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new UnknownAnimalException(animalKey);
        }

        return animal.showVaccinationHistory();
    }


    public String showWrongVaccinations() {
        return _incorrectVaccinationsHistory.stream()
                .map(Vaccination::toString)
                .collect(Collectors.joining("\n"));
    }

    public String showCurrentSeason(){
        return _currentSeason.toString();        
    }

    public void advanceSeason() {
        _currentSeason = _currentSeason.nextSeason();

        for (Tree tree : _trees.values()) {
            tree.nextSeason();
        }

        changed();
    }

    public String showGlobalSatisfaction(){
        double satisfaction = 0;
        for (Animal animal : _animals.values()) {
            satisfaction += animal.calculateSatisfaction();
        }
        for (Employee employee : _employees.values()) {
            satisfaction += employee.calculateSatisfaction();
        }
        return String.valueOf(Math.round(satisfaction));
    }

    /**
     * Set changed.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * @return changed
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * @param changed
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * Register an entry in the hotel.
     *
     * @param fields
     * @throws DuplicateAnimalException
     * @throws DuplicateEmployeeException
     * @throws DuplicateHabitatException
     * @throws DuplicateVaccineException
     * @throws DuplicateTreeException
     * @throws UnknownHabitatException
     * @throws UnknownSpecieException
     * @throws UnrecognizedTreeException
     * @throws UnrecognizedEntryException
     */
    public void registerEntry(String... fields) throws DuplicateHabitatException, DuplicateAnimalException,
                                                       UnknownHabitatException, UnknownSpecieException,
                                                       DuplicateEmployeeException, DuplicateVaccineException,
                                                       DuplicateTreeException, UnrecognizedEntryException,
                                                       UnknownTreeException {
        String type = fields[0];
        switch (type) {
            case "ESPÉCIE" -> registerSpecie(fields);
            case "ÁRVORE" -> registerTree(fields);
            case "HABITAT" -> registerHabitat(fields);
            case "ANIMAL" -> registerAnimal(fields);
            case "TRATADOR", "VETERINÁRIO" -> registerEmployee(fields);
            case "VACINA" -> registerVaccine(fields);
            default -> throw new UnrecognizedEntryException(type);
        }
        
    }

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    void importFile(String filename) throws ImportFileException {
	try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split("\\|");
                    try {
                        registerEntry(fields);
                    } catch (UnrecognizedEntryException | DuplicateAnimalException
                           | DuplicateEmployeeException | DuplicateHabitatException
                           | DuplicateTreeException | DuplicateVaccineException
                           | UnknownHabitatException | UnknownSpecieException
                           | UnknownTreeException e) {
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IOException e1) {
            throw new ImportFileException(filename, e1);
        }
    }
}