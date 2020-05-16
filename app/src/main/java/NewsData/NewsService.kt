package NewsData

import com.example.bgsmkcoding.DataProvItem
import retrofit2.Call
import retrofit2.http.GET


interface NewsService {
    @GET("indonesia/provinsi")
    fun getData(): Call<List<DataProvItem>>
}