package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "item")
public final class Item {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /** описание */
    @Setter
    @Column(name = "description")
    private String description;

    /** время создания **/
    @Column(name = "create_time", updatable = false, nullable = false)
    private Timestamp createTime;

    /** время измененя **/
    @Column(name = "edit_time")
    private Timestamp editTime;

    /** выполнить до **/
    @Setter
    @Column(name = "done_time")
    private Timestamp doneTime;

    /** фактическое время выполнения **/
    @Column(name = "fact_done_time")
    private Timestamp factDoneTime;

    /** выполнено **/
    @Setter
    @Column(name = "is_done")
    private Boolean isDone;

    @PrePersist
    protected void prePersist() {
        createTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void preUpdate() {
        editTime = new Timestamp(System.currentTimeMillis());
        factDoneTime = isDone ? new Timestamp(System.currentTimeMillis()) : null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item items = (Item) o;
        return Objects.equals(id, items.id)
                && Objects.equals(description, items.description)
                && Objects.equals(createTime, items.createTime)
                && Objects.equals(doneTime, items.doneTime)
                && Objects.equals(isDone, items.isDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createTime, doneTime, isDone);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", editTime=" + editTime +
                ", doneTime=" + doneTime +
                ", factDoneTime=" + factDoneTime +
                ", isDone=" + isDone +
                '}';
    }

}
