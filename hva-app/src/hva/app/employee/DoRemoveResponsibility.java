package hva.app.employee;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;

import hva.Hotel;
import hva.exceptions.UnknownSpecieException;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownHabitatException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
        addStringField("employeeKey", Prompt.employeeKey());
        addStringField("responsibilityKey", Prompt.responsibilityKey());
    }

    @Override   
    protected void execute() throws CommandException {
        try {
            _receiver.removeEmployeeResponsability(new String[] {
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
