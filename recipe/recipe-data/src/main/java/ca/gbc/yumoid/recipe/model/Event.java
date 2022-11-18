package ca.gbc.yumoid.recipe.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    private String eventDesc;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private User userEvent;

    public Event() {
    }

    public Event(String eventName, LocalDate eventDate, String eventDesc, User userEvent) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDesc = eventDesc;
        this.userEvent = userEvent;
    }

    public Event(Long id, String eventName, LocalDate eventDate, String eventDesc, User userEvent) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDesc = eventDesc;
        this.userEvent = userEvent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDetails) {
        this.eventDesc = eventDetails;
    }

    public User getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(User userEvent) {
        this.userEvent = userEvent;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", eventDetails='" + eventDesc + '\'' +
                ", user=" + userEvent +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }
}
