package hva.app.employee;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownEmployeeKeyException;

import hva.Hotel;
import hva.exceptions.UnknownEmployeeException;


class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
        addStringField("employeeKey", Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String employeeSatisfaction = _receiver.showSatisfactionOfEmployee(new String[] {
                stringField("employeeKey")
            });
            _display.popup(employeeSatisfaction); 
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(stringField("employeeKey"));
        }
    }
}
