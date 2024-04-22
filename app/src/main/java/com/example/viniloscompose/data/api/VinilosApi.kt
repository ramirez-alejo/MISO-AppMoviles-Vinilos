package com.example.viniloscompose.data.api

import android.content.Context
import com.android.volley.Response
import com.example.viniloscompose.model.album.Album
import com.example.viniloscompose.utils.VolleyBroker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class VinilosApi (
    context: Context
) : IVinilosApi {
    var volleyBroker = VolleyBroker(context)

    override suspend fun getAlbums() : List<Album>? = withContext(Dispatchers.IO) {
        var result: List<Album>? = null

        volleyBroker.instance.add(VolleyBroker.getRequest("albums",
            Response.Listener<JSONObject> { response ->
                // deserialize the response
                val jsonArray = response.getJSONArray("albums")
                result = List(jsonArray.length()) { i ->
                    Album.fromJson(jsonArray.getJSONObject(i))
                }
            },
            Response.ErrorListener { error ->
                // handle error
            }))

        return@withContext result
    }

    override suspend fun getAlbumById(id: Int) : Album? = withContext(Dispatchers.IO) {
        var result: Album? = null

        volleyBroker.instance.add(VolleyBroker.getRequest("albums/$id",
            Response.Listener<JSONObject> { response ->
                // deserialize the response
                result = Album.fromJson(response)
            },
            Response.ErrorListener { error ->
                // handle error
            }))

        return@withContext result
    }
}