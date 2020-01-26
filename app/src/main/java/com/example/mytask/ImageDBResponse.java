package com.example.mytask;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageDBResponse {
    @SerializedName("totalHits")
    private Integer totalHits;

    @SerializedName("hits")
    @Expose
    private List<Image> hits = null;

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public List<Image> getHits() {
        return hits;
    }

    public void setHits(List<Image> hits) {
        this.hits = hits;
    }
}