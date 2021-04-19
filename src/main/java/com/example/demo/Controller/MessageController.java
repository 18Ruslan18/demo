package com.example.demo.Controller;

import com.example.demo.exeptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static javax.swing.UIManager.put;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter=4;
    private List<Map<String,String>> messages = new ArrayList<Map<String, String>>() {
        {
            add(new HashMap<String, String>() {{put("id","1"); put("text","First message");}});
            add(new HashMap<String, String>() {{put("id","2"); put("text","Second message");}});
            add(new HashMap<String, String>() {{put("id","3"); put("text","THIRD message");}});
        }
    };
    @GetMapping
    public List<Map<String,String>> list(){
        return messages;
    }
    @GetMapping("{id}")
    public Map<String,String> getOne(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream().filter(messages -> messages.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String,String> create (@RequestBody Map<String,String> mesasge){
        mesasge.put("id",String.valueOf(counter++));
        messages.add(mesasge);
        return mesasge;
    }
    @PutMapping("{id}")
    public Map<String,String> update(@PathVariable String id,@RequestBody Map<String,String> mesasge){
        Map<String,String> messageFromDb = getMessage(id);
        messageFromDb.putAll(mesasge);
        messageFromDb.put("id",id);
        return messageFromDb;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String,String> message = getMessage(id);
        messages.remove(message);
    }
}
//add
/*fetch('/message',{method:'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify({text:'Fourth message'})}).then(console.log)*/
//update
/*fetch('/message/4',{method:'PUT', headers: {'Content-Type':'application/json'}, body: JSON.stringify({text:'Fourth message(update)'})}).then(console.log)*/
//delete
//fetch('/message/4',{method:'DELETE'}).then(console.log)