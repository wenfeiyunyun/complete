package hello;

/**
 * Created by Richard on 6/06/2016.
 */
public class Tag {
    public int id;
    public String tagName;
    public String wechat_id;

    public Tag(int id, String tagName, String wechat_id) {
        this.id = id;
        this.tagName = tagName;
        this.wechat_id = wechat_id;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
