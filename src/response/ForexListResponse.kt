package response

import chutien.it.ForexItem
import com.google.gson.annotations.SerializedName

data class ForexListResponse(
    @SerializedName("success") val success: Boolean? = null,
    @SerializedName("timestamp") val timestamp: Int? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("rates") val rates: MutableList<ForexItem>? = null
)