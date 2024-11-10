package hva.app.habitat;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.app.exceptions.UnknownTreeKeyException;

import hva.Hotel;
import hva.exceptions.DuplicateHabitatException;
import hva.exceptions.UnknownTreeException;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("habitatName", Prompt.habitatName());
        addStringField("habitatArea", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.registerHabitat(new String[] {
                "HABITAT", stringField("habitatKey"), stringField("habitatName"),
                stringField("habitatArea")
            });
        } catch (DuplicateHabitatException e) {
            throw new DuplicateHabitatKeyException(stringField("habitatKey"));
        } catch (UnknownTreeException e) {
            throw new UnknownTreeKeyException(e.getKey());
        }
    }


}
