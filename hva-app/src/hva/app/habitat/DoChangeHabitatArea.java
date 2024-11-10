package hva.app.habitat;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;

import hva.Hotel;
import hva.exceptions.UnknownHabitatException;


class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("habitatArea", Prompt.habitatArea());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.changeHabitatArea(new String[] {
                stringField("habitatKey"), stringField("habitatArea")
            });
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        }
    }
}