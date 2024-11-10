package hva.employee;

import java.io.Serial;
import java.io.Serializable;

/**
 * An abstract class representing an employee in the hotel system.
 * This class serves as a base for specific types of employees, such as 
 * handlers and veterinarians.
 */
public abstract class Employee<Receiver> implements Serializable {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    /** The unique key identifier for the employee. */
    private final String _employeeKey;

    /** The name of the employee. */
    private String _employeeName;
            
    /**
     * Constructs a new Employee with the specified key and name.
     * 
     * @param employeeKey The unique key identifier for the employee.
     * @param employeeName The name of the employee.
     */
    public Employee(String employeeKey, String employeeName) {
        _employeeKey = employeeKey;
        _employeeName = employeeName;
    }

    public String getKey() {
        return _employeeKey;
    }

    public abstract void addResponsability(Receiver responsability);
    public abstract void removeResponsability(Receiver responsability);
    public abstract boolean hasResponsability(Receiver responsability);
    public abstract double calculateSatisfaction();

    @Override
    // Format: id|nome
    public String toString() {
        return _employeeKey + "|" + _employeeName;
    }
}
