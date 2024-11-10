package hva.app.habitat;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;

import hva.Hotel;
import hva.exceptions.DuplicateTreeException;
import hva.exceptions.UnknownHabitatException;



class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
        String[] validTreeTypes = { "PERENE", "CADUCA" };
        addStringField("habitatKey", Prompt.habitatKey());
        addStringField("treeKey", Prompt.treeKey());
        addStringField("treeName", Prompt.treeName());
        addStringField("treeAge", Prompt.treeAge());
        addStringField("treeDificulty", Prompt.treeDifficulty());
        addOptionField("treeType", Prompt.treeType(), validTreeTypes);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            _receiver.addTreeToHabitat(new String[] {
                stringField("habitatKey"), stringField("treeKey"), stringField("treeName"),
                stringField("treeAge"), stringField("treeDificulty"), stringField("treeType")
            });
            String tree = _receiver.showTree(stringField("treeKey"));
            _display.popup(tree);
        } catch (UnknownHabitatException e) {
            throw new UnknownHabitatKeyException(stringField("habitatKey"));
        } catch (DuplicateTreeException e) {
            throw new DuplicateTreeKeyException(stringField("treeKey"));
        }
    }

}
