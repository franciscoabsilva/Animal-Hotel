package hva.app.animal;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownAnimalKeyException;

import hva.Hotel;
import hva.exceptions.UnknownAnimalException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
        addStringField("animalKey", Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String animalSatisfaction = _receiver.showSatisfactionOfAnimal(new String[] {
                stringField("animalKey")
            });
            _display.popup(animalSatisfaction); 
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(stringField("animalKey"));
        }
    }
}
