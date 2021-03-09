package ru.startandroid.develop.financemanagment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_accounts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.adapters.AccountAdapter
import ru.startandroid.develop.financemanagment.models.Account
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder
import ru.startandroid.develop.util.TopSpacingItemDecoration

/**
 * A simple [Fragment] subclass.
 * Use the [AccountsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    var adapterAccount = AccountAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_accounts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topSpacingDecoration = TopSpacingItemDecoration(30)


        initAccount()
        recyclerviewAccounts.adapter = adapterAccount
    }


    private fun initAccount() {
        RetrofitBuilder.getInstance().getScores().enqueue(object : Callback<List<Account>> {
            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                print("FAIL")

            }

            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                println("Успешно ")
                Log.d("s", "suc ${response.code()}")
                if(response.body() == null){
                    progressBar.visibility
                }
                else{
                    progressBar.isVisible = false
                }
                adapterAccount.setData(response.body() ?: arrayListOf())
            }

        })
    }


}

//private fun initProjects() {
//    RetrofitBuilder.apiService().getProject().enqueue(object : Callback<List<Account>> {
//        override fun onFailure(call: Call<List<Project>>, t: Throwable) {
//            print("FAIL")
//        }
//
//        override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
//            println("Успешно братан")
//            Log.d("s", "suc ${response.code()}")
//
//            recyclerView.adapter = HomeAdapter(response.body() as ArrayList<Project>)
//        }
//    })
//}

