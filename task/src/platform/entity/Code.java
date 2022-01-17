package platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(length = 10000)
    private String code;

    private LocalDateTime date;

    private Long time;

    private Integer views;

    @JsonIgnore
    private String uuid;

    public Code() {
        this.date = LocalDateTime.now();
        this.uuid = java.util.UUID.randomUUID().toString();
    }

    public void initDateAndTime() {
        if (time != null) {
            this.time = this.time < 1 ? null : time;
        }

        if (views != null) {
            this.views = this.views < 1 ? null : views;
        }
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    @JsonIgnore
    public LocalDateTime getDateTime() {
        return date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTime() {
        this.time = this.time == null ? 0 : time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setViews() {
        this.views = this.views == null ? 0 : views;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
