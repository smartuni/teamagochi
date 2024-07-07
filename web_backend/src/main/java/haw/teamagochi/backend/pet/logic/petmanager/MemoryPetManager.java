package haw.teamagochi.backend.pet.logic.petmanager;

import haw.teamagochi.backend.pet.dataaccess.model.PetEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * In-memory implementation of a {@link PetManager}.
  */
@ApplicationScoped
public class MemoryPetManager implements PetManager {

  //private static final Logger LOGGER = Logger.getLogger(MemoryPetManager.class);

  private static final int historyMaxLength = 10;

  Map<Long, InteractionStack> petInteractionMap;

  public MemoryPetManager() {
    petInteractionMap = new ConcurrentHashMap<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(Long petId) {
    return petInteractionMap.containsKey(petId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(PetEntity pet) {
    if (pet == null) {
      throw new IllegalArgumentException("Pet must not be null");
    }
    if (contains(pet.getId())) {
      return;
    }

    petInteractionMap.putIfAbsent(pet.getId(), initializeInteractionStack());
  }

  /**
   * Create new {@link InteractionStack} containing an empty element.
   */
  private InteractionStack initializeInteractionStack() {
    InteractionStack newStack = new InteractionStack(historyMaxLength);
    newStack.push(new InteractionRecord());
    return newStack;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addInteraction(Long petId, InteractionRecord record) {
    if (!contains(petId)) {
      throw new IllegalStateException("Pet must be know known to the manager");
    }

    petInteractionMap.get(petId).push(record);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InteractionRecord getLastInteraction(Long petId) {
    if (petId == null) {
      throw new IllegalArgumentException("Pet must not be null");
    }
    if (!contains(petId)) {
      throw new IllegalStateException("Pet must be know known to the manager");
    }

    return petInteractionMap.get(petId).peek();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public InteractionRecord getCurrentInteraction(Long petId) {
    try {
      InteractionRecord record = getLastInteraction(petId);
      if (record.isEvaluated()) {
        return null;
      }
      return record;
    } catch (IllegalStateException e) {
      return null;
    }
  }
}
