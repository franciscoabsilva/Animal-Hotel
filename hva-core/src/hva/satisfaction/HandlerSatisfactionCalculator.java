package hva.satisfaction;

import java.io.Serial;
import java.io.Serializable;

import hva.employee.Handler;
import hva.habitat.Habitat;

public class HandlerSatisfactionCalculator implements SatisfactionCalculator<Handler>{

    /** Class serial number. */
    @Serial
    private static final long serialVersionUID = 202407081733L;

    @Override
    public double calculateSatisfaction(Handler handler) {
        double work = 0.0;
        for (Habitat habitat : handler.getHabitats()) {
            double workInHabitat = habitat.workInHabitat();
            int nHandlers = habitat.countHandlers();
            work += (workInHabitat / nHandlers);
        }
        return 300 - work;
    }
}
