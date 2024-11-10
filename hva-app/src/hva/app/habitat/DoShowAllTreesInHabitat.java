package hva.app.habitat;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownHabitatKeyException;

import hva.Hotel;
import hva.exceptions.UnknownHabitatException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        addStringField("habitatKey", Prompt.habitatKey());
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String treesInHabitat = _receiver.showAllTreesInHabitat(new String[] {
                stringField("habitatKey")
            });
            _display.popup(treesInHabitat);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        }
    }
}
