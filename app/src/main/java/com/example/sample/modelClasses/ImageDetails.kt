package com.example.sample.modelClasses

class ImageDetails {
    var imageId = 0
    var imageUrl: String? = null
    var description : String? = null
    var title : String? = null
    var favStatus: String? = null

    constructor() {}
    constructor(imageId: Int, imageUrl: String?, favStatus: String?) {
        this.imageId = imageId
        this.imageUrl = imageUrl
                this.description = description;
        this.title = title;
        this.favStatus = favStatus
    }

    @JvmName("getDescription1")
    fun getDescription(): String? {
        return description
    }

    @JvmName("setDescription1")
    fun setDescription(): String? {
        return description
    }

    @JvmName("getTitle1")
    fun getTitle(): String? {
        return title
    }

    @JvmName("setTitle1")
    fun setTitle(): String? {
        return title
    }

    @JvmName("getFavStatus1")
    fun getFavStatus(): String? {
        return favStatus
    }

    @JvmName("setFavStatus1")
    fun setFavStatus(favStatus: String?) {
        this.favStatus = favStatus
    }

}

