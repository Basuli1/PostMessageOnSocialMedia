package com.example.SocialMedia.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SocialMedia.Exception.UserNotFoundException;
import com.example.SocialMedia.Repository.MessageRepository;
import com.example.SocialMedia.entity.Message;




@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository,AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService=accountService;
    }


    //create message service
    public Message createMessage(Message message,int postedBy) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot be blank and must be 255 characters or less.");
        }

        // Account acc = accountService.findAccount(postedBy);
        // System.out.print(acc);

        // if(accountService.findAccount(postedBy)==null){
        //     throw new UserNotFoundException("user not found");
        // }

        try {
            accountService.findAccount(postedBy);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("user not found");
        }
        
        return messageRepository.save(message);
    }


    //retreive all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }


    //retreive message by id
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }


    //retreieve message for specific user
    public List<Message> getMessagesByUserId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }


     //update message by id  
    public int updateMessageText(Integer messageId, String newMessageText) {
        if (newMessageText.isEmpty() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("Message text cannot be blank and must be 255 characters or less.");
        }

        if (messageRepository.existsById(messageId)) {
            Message message = messageRepository.findById(messageId).orElse(null);
            if (message != null) {
                message.setMessageText(newMessageText);
                messageRepository.save(message);
                return 1;
            }
        }
        return 0;
    }



    //delete message by id
    public int deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

}
