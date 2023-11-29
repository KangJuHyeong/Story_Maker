package com.example.teststorymaker

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val _id: String,
    val title: String,
    val representative_image: String,
    val contents: List<ContentItem>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(ContentItem.CREATOR)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(title)
        parcel.writeString(representative_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoryResponse> {
        override fun createFromParcel(parcel: Parcel): StoryResponse {
            return StoryResponse(parcel)
        }

        override fun newArray(size: Int): Array<StoryResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class ContentItem(
    val image: String,
    val detail: String,
    val music: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(detail)
        parcel.writeString(music)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContentItem> {
        override fun createFromParcel(parcel: Parcel): ContentItem {
            return ContentItem(parcel)
        }

        override fun newArray(size: Int): Array<ContentItem?> {
            return arrayOfNulls(size)
        }
    }
}

data class AllStoryDataResponse(
    val id: String,
    val title: String,
    //img URL
    val image: String

)

data class SubmitInform(
    @SerializedName("name")
    val name: String,
    @SerializedName("sex")
    val sex : String,
    @SerializedName("age")
    val age: String,
    @SerializedName("personality")
    val personality : String,
    @SerializedName("name2")
    val name2 : String,
    @SerializedName("subject")
    val subject : String
)
data class InformResponse(
    @SerializedName("message")
    val result: String
)

data class TokenResponse(
    @SerializedName("message")
    val result: String
)