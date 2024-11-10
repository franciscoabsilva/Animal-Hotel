package hva.app.search;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;

import hva.Hotel;
import hva.exceptions.UnknownHabitatException;
    
class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
        addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String animalsInHabitat = _receiver.showAllAnimalsInHabitat(new String[] {
                stringField("habitatKey")
            });
            _display.popup(animalsInHabitat); 
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        }
    }

}
