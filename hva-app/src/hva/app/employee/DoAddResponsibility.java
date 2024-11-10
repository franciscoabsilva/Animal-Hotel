package hva.app.employee;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;

import hva.Hotel;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpecieException;


class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);
        addStringField("employeeKey", Prompt.employeeKey());
        addStringField("responsibilityKey", Prompt.responsibilityKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.addEmployeeResponsability(new String[] {
                stringField("employeeKey"), stringField("responsibilityKey")
                });
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(stringField("employeeKey"));
        } catch (UnknownHabitatException | UnknownSpecieException e) {
            throw new NoResponsibilityException(stringField("employeeKey"),
            stringField("responsibilityKey"));
        }         
    }
}
