package hva.app.animal;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownAnimalKeyException;

import hva.Hotel;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownAnimalException;

class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
        addStringField("animalKey", Prompt.animalKey());
        addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.transferAnimalToHabitat(new String[] {
                stringField("animalKey"), stringField("habitatKey")
                });
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(stringField("animalKey"));
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        }
    }
}
