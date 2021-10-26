package com.example.sample.modelClasses

class FavItem {
    var fav_id = 0

    //    public String getFav_description() {
    var fav_image: String? = null

    //    private String fav_description;
    constructor() {}
    constructor(fav_id: Int, fav_image: String?) {
        this.fav_id = fav_id
        this.fav_image = fav_image
        //        this.fav_description = fav_description;
    }

}