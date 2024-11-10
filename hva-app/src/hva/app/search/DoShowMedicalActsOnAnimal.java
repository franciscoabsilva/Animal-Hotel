package hva.app.search;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownAnimalKeyException;

import hva.Hotel;
import hva.exceptions.UnknownAnimalException;

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
        addStringField("animalKey", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String medicalActsOnAnimal = _receiver.showMedicalActsOnAnimal(new String[] {
                stringField("animalKey")
            });
            _display.popup(medicalActsOnAnimal); 
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(stringField("animalKey"));
        }
    }

}
