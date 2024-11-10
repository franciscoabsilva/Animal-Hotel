package hva.app.employee;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.DuplicateEmployeeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import hva.Hotel;
import hva.exceptions.DuplicateEmployeeException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpecieException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
        String[] validEmployeeTypes = { "VET", "TRT" };
        addStringField("employeeKey", Prompt.employeeKey());
        addStringField("employeeName", Prompt.employeeName());
        addOptionField("employeeType", Prompt.employeeType(), validEmployeeTypes);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.registerEmployee(new String[] {
                stringField("employeeType"), stringField("employeeKey"),
                stringField("employeeName")
            });
        } catch (DuplicateEmployeeException e) {
            throw new DuplicateEmployeeKeyException(stringField("employeeKey"));
        } catch (UnknownSpecieException e) { 
            throw new UnknownSpeciesKeyException(e.getKey());
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }
}