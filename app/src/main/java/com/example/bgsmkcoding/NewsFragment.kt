package com.example.bgsmkcoding

import NewsData.NewsService
import NewsData.apiRequestCP
import NewsData.httpClient
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.dismissLoading
import util.showLoading
import util.tampilToast

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View,@Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApiGetProvinsi()
    }

    private fun callApiGetProvinsi(){
        showLoading(context!!, swipeProvinsi)

        val httpClient = httpClient()
        val apiRequest = apiRequestCP<NewsService>(httpClient)

        val call = apiRequest.getData()
        call.enqueue(object: Callback<List<DataProvItem>>{
            override fun onFailure(call: Call<List<DataProvItem>>, t: Throwable) {
                dismissLoading(swipeProvinsi)
            }

            override fun onResponse(
                call: Call<List<DataProvItem>>,
                response: Response<List<DataProvItem>>
            ) {
                dismissLoading(swipeProvinsi)

                when{
                    response.isSuccessful->
                        when{
                            response.body()?.size != 0->
                                tampilCovidProvince(response.body()!!)

                            else->{
                                tampilToast(context!!, "Berhasil")
                            }
                        }
                    else->{
                        tampilToast(context!!, "Gagal")
                    }
                }
            }
        })
    }

    private fun tampilCovidProvince(provinsi: List<DataProvItem>){
        listCovidProvinsi.layoutManager = LinearLayoutManager(context)
        listCovidProvinsi.adapter = NewsAdapter(context!!, provinsi){
            val provinsid = it
            tampilToast(context!!, provinsid.attributes.provinsi)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
