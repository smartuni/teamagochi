package haw.teamagochi.backend.pet.logic.petmanager;

import java.util.ArrayDeque;
import java.util.Deque;
import lombok.Getter;

/**
 * A sized stack for {@link InteractionRecord InteractionRecords}.
 */
public class InteractionStack {

  @Getter
  private final int maxSize;

  private final Deque<InteractionRecord> stack;

  public InteractionStack() {
    this(20);
  }

  public InteractionStack(int size) {
    this.maxSize = size;
    this.stack = new ArrayDeque<>(size);
  }

  /**
   * Pushes an item onto the top of this stack.
   */
  public void push(InteractionRecord record) {
    try {
      if (!stack.offerFirst(record)) {
        // TODO it could be checked if the removed element was ever evaluated
        stack.removeLast();
        stack.push(record);
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Record must not be null");
    }
  }

  /**
   * Looks at the object at the top of this stack without removing it from the stack.
   */
  public InteractionRecord peek() {
    return this.stack.peek();
  }
}
