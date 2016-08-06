package joe.com.cnode.model.entity;

import com.google.gson.annotations.SerializedName;

import joe.com.cnode.util.FormatUtils;

/**
 * Created by JOE on 2016/8/5.
 */
public class Author {

    //SerializedName定义属性序列化后的名称
    @SerializedName("loginname")
    private String loginName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAvatarUrl() {//修复头像地址的历史遗留问题
        return FormatUtils.getCompatAvatarUrl(avatarUrl);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
