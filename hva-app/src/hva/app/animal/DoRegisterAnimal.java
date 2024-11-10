package hva.app.animal;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import hva.Hotel;
import hva.exceptions.DuplicateAnimalException;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpecieException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
        addStringField("animalKey", Prompt.animalKey());
        addStringField("animalName", Prompt.animalName());
        addStringField("speciesKey", Prompt.speciesKey());
        addStringField("habitatKey", hva.app.habitat.Prompt.habitatKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            try {
                _receiver.registerAnimal(new String[] {
                    "ANIMAL", stringField("animalKey"), stringField("animalName"),
                    stringField("speciesKey"), stringField("habitatKey")
                    });
            } catch (UnknownSpecieException e1) {
                _receiver.registerSpecie(new String[] {
                    "ESPÉCIE", stringField("speciesKey"), Form.requestString(Prompt.speciesName())
                    });
                _receiver.registerAnimal(new String[] {
                    "ANIMAL", stringField("animalKey"), stringField("animalName"),
                    stringField("speciesKey"), stringField("habitatKey")
                    });
            }
        } catch (DuplicateAnimalException e) {
            throw new DuplicateAnimalKeyException(stringField("animalKey"));
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        } catch (UnknownSpecieException e) {
            throw new UnknownSpeciesKeyException(stringField("speciesKey"));
        }
    }
}
