package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories = new ArrayList<>();

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

    public Item(String description, boolean finished, User user) {
        this.description = description;
        long droppedMillis = 1000 * (System.currentTimeMillis() / 1000);
        this.createDate = new Date(droppedMillis);
        this.finished = finished;
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCat(Category category) {
        this.categories.add(category);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", createDate=" + createDate
                + ", finished=" + finished
                + ", user=" + user
                + '}';
    }
}