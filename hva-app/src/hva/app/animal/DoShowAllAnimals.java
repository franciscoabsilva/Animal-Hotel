package hva.app.animal;

import pt.tecnico.uilib.menus.Command;

import hva.Hotel;

class DoShowAllAnimals extends Command<Hotel> {

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        String animals = _receiver.showAllAnimals();
        _display.popup(animals);
    }

}
