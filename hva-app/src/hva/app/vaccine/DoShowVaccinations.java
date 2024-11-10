package hva.app.vaccine;

import pt.tecnico.uilib.menus.Command;

import hva.Hotel;

class DoShowVaccinations extends Command<Hotel> {

    DoShowVaccinations(Hotel receiver) {
        super(Label.SHOW_VACCINATIONS, receiver);
    }

    @Override
    protected final void execute() {
        String vaccinations = _receiver.showVaccinations();
        _display.popup(vaccinations);
    }
}
