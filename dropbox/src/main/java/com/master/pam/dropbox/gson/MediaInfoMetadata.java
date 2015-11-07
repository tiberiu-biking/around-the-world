package com.master.pam.dropbox.gson;

import java.io.Serializable;

public class MediaInfoMetadata implements Serializable {

    private static final long serialVersionUID = 4169212178873030308L;

    private String name;

    private String path;

    private String icon_name;

    private Boolean mightHaveThumbnail;

    private Long num_bytes;

    private String human_size;

    private String last_modified;

    private String client_mtime;


    private String rev;

    private PhotoInfo photo_info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    public Boolean getMightHaveThumbnail() {
        return mightHaveThumbnail;
    }

    public void setMightHaveThumbnail(Boolean mightHaveThumbnail) {
        this.mightHaveThumbnail = mightHaveThumbnail;
    }

    public Long getNum_bytes() {
        return num_bytes;
    }

    public void setNum_bytes(Long num_bytes) {
        this.num_bytes = num_bytes;
    }

    public String getHuman_size() {
        return human_size;
    }

    public void setHuman_size(String human_size) {
        this.human_size = human_size;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getClient_mtime() {
        return client_mtime;
    }

    public void setClient_mtime(String client_mtime) {
        this.client_mtime = client_mtime;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public PhotoInfo getPhoto_info() {
        return photo_info;
    }

    public void setPhoto_info(PhotoInfo photo_info) {
        this.photo_info = photo_info;
    }

}
