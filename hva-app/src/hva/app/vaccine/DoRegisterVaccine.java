package hva.app.vaccine;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;

import hva.Hotel;
import hva.exceptions.DuplicateVaccineException;
import hva.exceptions.UnknownSpecieException;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
        addStringField("vaccineKey", Prompt.vaccineKey());
        addStringField("vaccineName", Prompt.vaccineName());
        addStringField("listOfSpeciesKeys", Prompt.listOfSpeciesKeys());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.registerVaccine(new String[] {
                "VACINA", stringField("vaccineKey"), stringField("vaccineName"),
                stringField("listOfSpeciesKeys")
            });
        } catch (DuplicateVaccineException e) {
            throw new DuplicateVaccineKeyException(stringField("vaccineKey"));
        } catch (UnknownSpecieException e) {
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }
}

