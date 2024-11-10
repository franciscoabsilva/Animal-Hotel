package hva.satisfaction;

import java.io.Serializable;

import javax.sound.midi.Receiver;

public interface SatisfactionCalculator<Receiver> extends Serializable{
    public double calculateSatisfaction(Receiver receiver);
}
