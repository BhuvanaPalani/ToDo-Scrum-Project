package se.jensenyh.todo_list.todo;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @NotBlank(message = "Description is mandatory")
    @Size(min = 1, message = "Description is too short")
    @Size(max = 140, message = "Description is too long")
    private String description;
    private boolean isDone;
    @Column
    private int timeToComplete;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private String cognitoUserId;


    private String prio;

    //Creates a new todo without having to assign an id
    public Todo(String description, boolean isDone, String prio, String cognitoUserId, int timeToComplete) {
        this.description = description;
        this.isDone = isDone;
        this.prio = prio;
        this.cognitoUserId = cognitoUserId;
        this.timeToComplete = timeToComplete;
    }

    public void setIsDone(boolean isDone) {

        this.isDone = isDone;
    }


    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;

    }

    public void setTimeToComplete(int timeToComplete) {

        this.timeToComplete = timeToComplete;


    }

}