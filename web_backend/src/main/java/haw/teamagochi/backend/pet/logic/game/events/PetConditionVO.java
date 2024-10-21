package haw.teamagochi.backend.pet.logic.game.events;

public abstract class PetConditionVO extends PetAttributeVO{

    public PetConditionVO(int min, int max) {
        super(min, max);
    }

    abstract protected int dispatch(int attributeValue, PetEvents event);
}
