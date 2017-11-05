package com.saha.slnarch.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saha.slnarch.core.element.by.ByType;

public class ElementInfo {

  @SerializedName("key")
  @Expose
  private String key;
  @SerializedName("type")
  @Expose
  private ByType type;


  public ByType getType() {
    return type;
  }

  public void setType(ByType type) {
    this.type = type;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
