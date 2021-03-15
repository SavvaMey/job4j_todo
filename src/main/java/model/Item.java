package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Timestamp createDate;
    private boolean finished;

    public Item() {
    }

    public Item(int id) {
        this.id = id;
    }

    public Item(int id, String description, Timestamp created, boolean finished) {
        this.id = id;
        this.description = description;
        this.createDate = created;
        this.finished = finished;
    }

    public Item(String description, Timestamp created, boolean finished) {
        this.description = description;
        this.createDate = created;
        this.finished = finished;
    }

    public Item(String description, boolean finished) {
        this.description = description;
        long droppedMillis = 1000 * (System.currentTimeMillis() / 1000);
        this.createDate = new Timestamp(droppedMillis);
        this.finished = finished;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return createDate;
    }

    public void setCreated(Timestamp created) {
        this.createDate = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", createDate=" + createDate
                + ", finished=" + finished
                + '}';
    }
}