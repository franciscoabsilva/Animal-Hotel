package hva.app.employee;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.Hotel;

class DoShowAllEmployees extends Command<Hotel> {

    DoShowAllEmployees(Hotel receiver) {
        super(Label.SHOW_ALL_EMPLOYEES, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        String employees = _receiver.showAllEmployees();
        _display.popup(employees);
    }

}
