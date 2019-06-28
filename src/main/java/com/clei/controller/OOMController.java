package com.clei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("oom")
public class OOMController {
    @RequestMapping("boom")
    public String oom(){
        Set<Room> rooms = new HashSet<>();
        while(true){
            rooms.add(new Room());
        }
    }

    private class Room{
        private List<Entity> entities = new ArrayList<>();
        public Room(){
            for (int i = 0; i < 1000; i++) {
                entities.add(new Entity());
            }
        }
    }

    private class Entity{
        private Integer age = new Integer(18);
        private String name = new String("hasaki");
    }
}
