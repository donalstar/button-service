package com.donal.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Result object for return to the client
 */

@XmlRootElement(name = "result")
public class Result {

    List<PostData> posts;
    int pageId;
    int pageCount;

    public List<PostData> getPosts() {
        return posts;
    }

    public void setPosts(List<PostData> posts) {
        this.posts = posts;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
