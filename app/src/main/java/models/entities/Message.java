package models.entities;

import java.util.UUID;

import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;

public class Message extends BaseEntity {

    private User sender;
    private User receiver;
    private String text;
    private boolean isRead;

    // Create new Message
    public Message(User sender, User receiver, String text) {
        super();
        setSender(sender);
        setReceiver(receiver);
        setText(text);
        isRead = false;
    }

    // Import an existing Message
    public Message(UUID id, User sender, User receiver, String text, boolean isRead) {
        super.id = id;
        setSender(sender);
        setReceiver(receiver);
        setText(text);
        this.isRead = isRead;
    }

    // OPERATIONS

    

    // GETTERS
    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getText() {
        isRead = true;
        return text;
    }

    public boolean isRead() {
        return isRead;
    }



    // SETTERS
    private String setSender(User sender) {
        try {
            validateUser(sender);
            this.sender = sender;
            return String.format(SuccessMessages.MESSAGE_SET_SENDER, sender.getNickname());
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.MESSAGE_INVALID_SENDER, sender.getNickname());
        }
    }

    private String setReceiver(User receiver) {
        try {
            validateUser(receiver);
            this.receiver = receiver;
            return String.format(SuccessMessages.MESSAGE_SET_RECEIVER, receiver.getNickname());
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.MESSAGE_INVALID_RECEIVER, receiver.getNickname());
        }
    }

    private String setText(String text) {
        try {
            validateText(text);
            this.text = text;
            return String.format(SuccessMessages.MESSAGE_SET_TEXT);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.MESSAGE_INVALID_TEXT_LENGTH);
        }
    }



    // VALIDATORS
    private void validateText(String text) throws InvalidLengthException {
        Validator.validateStringLength(text, Variables.MIN_MESSAGE_TEXT_LENGTH,
                Variables.MAX_MESSAGE_TEXT_LENGTH);
    }

    private void validateUser(User user) throws InvalidInstanceException {
        Validator.validateExists(user);
    }
}
