package haw.teamagochi.backend.pet.logic.Events;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class PetAttributeVO {
    private final int MAX_VALUE;
    private final int MIN_VALUE;

    public int getMax() {return this.MAX_VALUE;}
    public int getMin() {return this.MIN_VALUE;}

    public PetAttributeVO(int min, int max) {
        MIN_VALUE = min;
        MAX_VALUE = max;
    }
    public int boundaryCheck(int attributeValue) {
        return min(this.MAX_VALUE, max(this.MIN_VALUE, attributeValue));
    }





}
