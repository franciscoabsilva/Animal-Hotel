package hva.app.search;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownVeterinarianKeyException;

import hva.Hotel;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownVeterinarianException;

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
        addStringField("employeeKey", hva.app.employee.Prompt.employeeKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String medicalActsByVeterinarian = _receiver.showMedicalActsByVeterinarian(new String[] {
                stringField("employeeKey")
            });
            _display.popup(medicalActsByVeterinarian); 
        } catch (UnknownEmployeeException | UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(stringField("employeeKey"));
        }      
    }
}
