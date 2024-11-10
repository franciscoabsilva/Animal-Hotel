package hva.app.habitat;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import hva.Hotel;
import hva.exceptions.UnknownHabitatException;
import hva.exceptions.UnknownSpecieException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        String[] validInfluenceTypes = { "POS", "NEG", "NEU" };
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("specieKey", hva.app.animal.Prompt.speciesKey());
        addOptionField("influence", Prompt.habitatInfluence(), validInfluenceTypes);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.changeHabitatInfluence(new String[] {
                stringField("habitatKey"), stringField("specieKey"), optionField("influence")
            });
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        } catch (UnknownSpecieException e) { 
            throw new UnknownSpeciesKeyException(stringField("specieKey"));
        }
    }

}
