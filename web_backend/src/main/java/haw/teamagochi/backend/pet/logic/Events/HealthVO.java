package haw.teamagochi.backend.pet.logic.Events;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

@ApplicationScoped
public class HealthVO extends PetAttributeVO implements Deterioratable {

    public HealthVO() {
        super(0, 100);
    }

    public int deteriorate(int health) {
        //health decrease is random based
        Random randomNum = new Random();
        if (randomNum.nextInt(10)==1) {health -= 5;}
        return boundaryCheck(health);
    }


    public int dispatch(int health, PetEvents event) {
        if (event == PetEvents.MEDICATE) health += 10;
        return boundaryCheck(health);
    }

}
