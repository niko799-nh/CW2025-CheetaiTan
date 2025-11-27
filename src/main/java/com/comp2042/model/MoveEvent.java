package com.comp2042.model;

/**
 * This class basically keeps track of what kind of move the player or system is doing.
 * Very simple only lah — just store the type of movement and who triggered it.
 */
public final class MoveEvent {

    /** The type of move */
    private final EventType eventType;

    /** Whether the move came from user input or the game thread. */
    private final EventSource eventSource;

    /**
     * Create a new MoveEvent.
     * @param eventType The movement type, like LEFT or ROTATE.
     * @param eventSource Who triggered it — user press key or system auto drop.
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Get what move this event is about.
     * @return The EventType.
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Get who triggered this event.
     * @return The EventSource (USER or THREAD).
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
