package hva.app.vaccine;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;

import hva.Hotel;
import hva.exceptions.UnknownAnimalException;
import hva.exceptions.UnknownEmployeeException;
import hva.exceptions.UnknownVaccineException;
import hva.exceptions.UnknownVeterinarianException;
import hva.exceptions.VeterinarianCantVaccinateException;
import hva.exceptions.WrongVaccineException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
        addStringField("vaccineKey", Prompt.vaccineKey());
        addStringField("veterinarianKey", Prompt.veterinarianKey());
        addStringField("animalKey", hva.app.animal.Prompt.animalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.vaccinateAnimal(new String[] {
                stringField("vaccineKey"), stringField("veterinarianKey"), stringField("animalKey")
            });
        } catch (UnknownVaccineException e) {
            throw new UnknownVaccineKeyException(stringField("vaccineKey"));
        } catch (UnknownEmployeeException e) {
            throw new UnknownEmployeeKeyException(stringField("veterinarianKey"));
        } catch (UnknownAnimalException e) {
            throw new UnknownAnimalKeyException(stringField("animalKey"));
        } catch (UnknownVeterinarianException e) {
            throw new UnknownVeterinarianKeyException(stringField("veterinarianKey"));
        } catch (VeterinarianCantVaccinateException e) {
            throw new VeterinarianNotAuthorizedException(stringField("veterinarianKey"),
            e.getSpecieKey());
        } catch (WrongVaccineException e) {
            _display.popup(Message.wrongVaccine(stringField("vaccineKey"),
            stringField("animalKey")));
        }
    }
}
