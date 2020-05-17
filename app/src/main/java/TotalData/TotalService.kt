package TotalData



import com.example.bgsmkcoding.TotalDataItem
import retrofit2.Call
import retrofit2.http.GET


interface TotalService {
    @GET("indonesia")
    fun getTotal(): Call<List<TotalDataItem>>
}