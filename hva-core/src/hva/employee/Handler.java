package hva.employee;

import java.io.Serial;
import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;

import hva.habitat.Habitat;
import hva.satisfaction.HandlerSatisfactionCalculator;
import hva.satisfaction.SatisfactionCalculator;

/**
 * Represents a handler within the hotel system, extending the Employee class.
 * Handlers are responsible for managing habitats.
 */
public class Handler extends Employee<Habitat> {

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final SatisfactionCalculator _satisfactionCalculator = new HandlerSatisfactionCalculator();

    /** A map of habitats managed by the handler, case insensitive. */
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Constructs a new Handler with the specified key and name.
     * 
     * @param handlerKey The unique key identifier for the handler.
     * @param handlerName The name of the handler.
     */
    public Handler(String handlerKey, String handlerName){
        super(handlerKey, handlerName);
    }

    /**
     * Constructs a new Handler with the specified key, name, and an array of habitats.
     * 
     * @param handlerKey The unique key identifier for the handler.
     * @param handlerName The name of the handler.
     * @param habitats An array of habitats managed by the handler.
     */
    public Handler(String handlerKey, String handlerName, Habitat[] habitats){
        super(handlerKey, handlerName);
        for(Habitat habitat : habitats){
            _habitats.put(habitat.getKey(), habitat);
        }
    }

    public void addResponsability(Habitat responsability) {
        _habitats.put(responsability.getKey(), responsability);
    }

    public void removeResponsability(Habitat responsability) {
        _habitats.remove(responsability.getKey());
    }

    public boolean hasResponsability(Habitat responsability) {
        return _habitats.containsKey(responsability.getKey());
    }

    public Habitat[] getHabitats(){
        return _habitats.values().toArray(new Habitat[0]);
    }

    public double calculateSatisfaction() {
        return _satisfactionCalculator.calculateSatisfaction(this);
    }

    /**
     * Returns a string representation of the Handler.
     * 
     * @return A formatted string representing the handler, including their habitats if any.
     */
    @Override
    // Format: TRT|id|nome|idResponsabilidades
    public String toString(){
        if (_habitats.isEmpty())
            return "TRT|" + super.toString();
        else
            return "TRT|" + super.toString() + "|" + String.join(",", _habitats.keySet());
    }
}
