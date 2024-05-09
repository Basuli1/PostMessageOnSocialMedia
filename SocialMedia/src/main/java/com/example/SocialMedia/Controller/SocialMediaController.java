package com.example.SocialMedia.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SocialMedia.Exception.DuplicateUsernameException;
import com.example.SocialMedia.Exception.InvalidRegistrationException;
import com.example.SocialMedia.Exception.UserNotFoundException;
import com.example.SocialMedia.Service.AccountService;
import com.example.SocialMedia.Service.MessageService;
import com.example.SocialMedia.entity.Account;
import com.example.SocialMedia.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService,MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }



    //register user handler
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        try {
            Account newAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (InvalidRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    //log in user handler
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        Account authenticatedAccount = accountService.authenticateAccount(account);
        if (authenticatedAccount != null) {
            return ResponseEntity.ok(authenticatedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // handler  for message

    //create message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody String requestbody) throws JsonMappingException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(requestbody,Message.class);
        int postedBy=message.getPostedBy();

        try {
            Message newMessage = messageService.createMessage(message,postedBy);
            return ResponseEntity.ok(newMessage);
        }catch(UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
       
    }


    //Retrieve all message handler
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    //Retrieve message by messageId handler
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }


    // Retrieve all messages for for specific user handler
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok(messages);
    }


    //delete by messageId handler
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        int rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted==1) {
            return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(rowsDeleted));
        } else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }


    //update by messageId handler
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessageText(@PathVariable Integer messageId, @RequestBody String newMessageText) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(newMessageText);
        String messageText = jsonNode.get("messageText").asText();

        try {
            int  updated = messageService.updateMessageText(messageId, messageText);
            if (updated==1) {
                return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(updated));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
