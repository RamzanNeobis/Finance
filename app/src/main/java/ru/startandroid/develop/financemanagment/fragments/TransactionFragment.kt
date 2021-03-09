package ru.startandroid.develop.financemanagment.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.adapters.TransactionAdapter
import ru.startandroid.develop.financemanagment.activity.AddActivity
import ru.startandroid.develop.financemanagment.models.Data
import ru.startandroid.develop.financemanagment.models.TransactionStory
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder
import ru.startandroid.develop.financemanagment.retrofit.SessionManager

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionFragment : Fragment(), CoroutineScope by MainScope() {

    private var transactionAdapter = TransactionAdapter()
    private var token = SessionManager
    private var transctions = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = transactionAdapter
        MainScope().launch(Dispatchers.Main){
            initTransaction()
        }





    }

    private fun initTransaction() {

        RetrofitBuilder.getInstance().getTransaction()
            .enqueue(object : Callback<TransactionStory> {

                override fun onFailure(call: Call<TransactionStory>, t: Throwable) {
                    print("FAIL")
                }

                override fun onResponse(
                    call: Call<TransactionStory>,
                    response: Response<TransactionStory>
                ) {
                    if(response.body() == null){
                        progressBar.visibility
                    }
                    else{
                        progressBar.isVisible = false
                    }
                    println("Успешно ")
                    Log.d("s", "suc ${response.code()}")
                    transactionAdapter.setData(response.body()?.data ?: arrayListOf())
                }
            })
    }


}





