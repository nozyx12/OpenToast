package io.github.nozyx12.opentoast;

/**
 * Exception thrown when there is an overflow of toast notifications.
 * This occurs when there is not enough space on the screen to display
 * additional notifications without overlapping existing ones.
 */
public class ToastStackOverflowException extends RuntimeException {

    /**
     * Constructs a new ToastStackOverflowException with no detail message.
     */
    public ToastStackOverflowException() {
        super();
    }
}
