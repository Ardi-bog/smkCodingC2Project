package com.example.bgsmkcoding

import TotalData.TotalService
import TotalData.apiRequestTo
import TotalData.httpClient
import WorldData.apiRequest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_total.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.dismissLoading
import util.showLoading
import util.tampilToast

/**
 * A simple [Fragment] subclass.
 */
class TotalFragment : Fragment() {

    private fun callApiGetCovidGlobal(){
        showLoading(context!!, swipeTotal)

        val httpClient = httpClient()
        val apiRequest = apiRequestTo<TotalService>(httpClient)

        val call = apiRequest.getTotal()
        call.enqueue(object : Callback<List<TotalDataItem>> {
            override fun onFailure(call: Call<List<TotalDataItem>>, t: Throwable) {
                dismissLoading(swipeTotal)
            }

            override fun onResponse(
                call: Call<List<TotalDataItem>>,
                response: Response<List<TotalDataItem>>
            ) {
                dismissLoading(swipeTotal)

                when{
                    response.isSuccessful->
                        when{
                            response.body()?.size != 0 ->
                                tampilTotal(response.body()!!)
                            else->{
                                tampilToast(context!!, "Berhasil")
                            }
                        }
                    else->
                        tampilToast(context!!, ".")
                }
            }
        })
    }
    private fun  tampilTotal(total: List<TotalDataItem>){
        listIndonesia.layoutManager = LinearLayoutManager(context)
        listIndonesia.adapter =
            TotalAdapter(
                context!!,
                total
            ){
                val totalData = it
                tampilToast(context!!,totalData.name)
            }
    }

    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onViewCreated(view: View,@Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApiGetCovidGlobal()
    }


}
