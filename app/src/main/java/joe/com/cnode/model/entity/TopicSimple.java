package joe.com.cnode.model.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

/**
 * Created by JOE on 2016/8/5.
 */
public class TopicSimple {

    private String id;

    private String title;

    private Author author;

    @SerializedName("last_reply_at")
    private DateTime lastReplyAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public DateTime getLastReplyAt() {
        return lastReplyAt;
    }

    public void setLastReplyAt(DateTime lastReplyAt) {
        this.lastReplyAt = lastReplyAt;
    }
}
