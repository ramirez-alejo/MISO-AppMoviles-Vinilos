package com.example.viniloscompose.model.album

data class Album (
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    var description: String,
    var genre: String,
    var recordLabel: String
) : java.io.Serializable
{
    companion object {
        fun fromJson(jsonObject: org.json.JSONObject): Album {
            return Album(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                cover = jsonObject.getString("cover"),
                releaseDate = jsonObject.getString("releaseDate"),
                description = jsonObject.getString("description"),
                genre = jsonObject.getString("genre"),
                recordLabel = jsonObject.getString("recordLabel")
            )
        }
    }
}