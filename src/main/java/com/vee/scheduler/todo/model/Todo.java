package com.vee.scheduler.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "todo")
public class Todo {

    @Id
    private String id;
    private boolean complete;
    private String description;

    public Todo(){
        this.id = UUID.randomUUID().toString();
    }
}
