package hva.app.vaccine;

import pt.tecnico.uilib.menus.Command;

import hva.Hotel;

class DoShowAllVaccines extends Command<Hotel> {

    DoShowAllVaccines(Hotel receiver) {
        super(Label.SHOW_ALL_VACCINES, receiver);
    }

    @Override
    protected final void execute() {
        String vaccines = _receiver.showAllVaccines();
        _display.popup(vaccines);
    }
}
