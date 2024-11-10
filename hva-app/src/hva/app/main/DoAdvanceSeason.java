package hva.app.main;

import hva.HotelManager;
import pt.tecnico.uilib.menus.Command;

class DoAdvanceSeason extends Command<HotelManager> {
    DoAdvanceSeason(HotelManager receiver) {
        super(Label.ADVANCE_SEASON, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.advanceSeason();
        String season = _receiver.showCurrentSeason();
        _display.popup(season);
    }
}
