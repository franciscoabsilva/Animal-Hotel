package hva;

import java.io.*;

import hva.exceptions.ImportFileException;
import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.UnavailableFileException;

/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** The hotel manager. */
    private String _filename = "";

    /** The current hotel. */
    private Hotel _hotel = new Hotel();

    /**
     * Saves the serialized application's state into the file associated to the current hotel.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current hotel does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        if (!changed()) return;
        if (_filename == null || _filename.equals(""))
            throw new MissingFileAssociationException();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                                      new FileOutputStream(_filename)))) {
            oos.writeObject(_hotel);
            _hotel.setChanged(false);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current hotel.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException,
                                               MissingFileAssociationException,
                                               IOException {
        _filename = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException {
        _filename = filename;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(
                                     new FileInputStream(filename)))) {
            _hotel = (Hotel) ois.readObject();
            _hotel.setChanged(false);
        } catch (FileNotFoundException e) {
            throw new UnavailableFileException(filename);
        } catch (IOException e) {
            throw new UnavailableFileException(filename);
        } catch (ClassNotFoundException e) {
            throw new UnavailableFileException(filename);

        }
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    /**
    * @return filename
    */
    public String getFilename() {
        return _filename;
    }

    /**
    * @param filename
    */
     public void setFilename(String filename) {
        _filename = filename;
    }

    /**
    * @return changed?
    */
    public boolean changed() {
        return _hotel.hasChanged();
    }

    /**
    * @return hotel
    */
    public Hotel getHotel() {
        return _hotel;
    }

    /**
    * Create a new hotel.
    */
    public void reset() {
        _hotel = new Hotel();
        _filename = null;
    }

    public String showGlobalSatisfaction() {
        return _hotel.showGlobalSatisfaction();
    }

    public void advanceSeason() {
        _hotel.advanceSeason();
    }

    public String showCurrentSeason() {
        return _hotel.showCurrentSeason();
    }

}
