package ru.startandroid.develop.financemanagment.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_transfer.*
import kotlinx.android.synthetic.main.fragment_transfer.date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.activity.MainActivity
import ru.startandroid.develop.financemanagment.models.Account
import ru.startandroid.develop.financemanagment.models.TransferModel
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder
import ru.startandroid.develop.util.OnItemSelectListener
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFragment : Fragment() {

    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private lateinit var fromAccountAdapter: ArrayAdapter<String>
    private lateinit var toAccountAdapter: ArrayAdapter<String>
    private val fromAccounts = mutableListOf<Account>()
    private val fromAccountsTwo = mutableListOf<Account>()
    private lateinit var dateTransfer: String
    private var sumTransaction: Int = 0
    private var scoreId: Int? = null
    private var score2Id: Int? = null
    private var description = "Тестовые данные"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_transfer, container, false)

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss").format(System.currentTimeMillis())
        date.setText(textDate)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd'T'HH:mm:ss.sss" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val formatDate = sdf.format(calendar.time)
                date.setText(formatDate)
            }

        date.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fromAccountsSpinner.onItemClickListener = OnItemSelectListener { position ->
            scoreId = fromAccounts[position].id
        }
        toAccountSpinner.onItemClickListener = OnItemSelectListener { position ->
            score2Id = fromAccounts[position].id
        }
        dateTransfer = date.text.toString()

        try {
            if (sumTransfer == null) {
                sumTransfer.doAfterTextChanged {
                    sumTransaction = it.toString().toInt()
                }
            }

        } catch (e: NumberFormatException) {
            Log.d("error", e.message.toString())
            println("error")
        }



        fromAccountAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )
        toAccountAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )

        fromAccountsSpinner.setAdapter(fromAccountAdapter)
        toAccountSpinner.setAdapter(toAccountAdapter)

        fetchFrom()
        fetchToAccount()

        buttonTransfer.setOnClickListener {
            MainScope().launch(Dispatchers.Main) {
                createTransfer(
                    date = dateTransfer,
                    sum = sumTransaction ?: 0,
                    scoreId = scoreId!!,
                    score2Id = score2Id!!,
                    description = description
                )
            }
        }
    }


    private fun fetchFrom() {
        RetrofitBuilder.getInstance().getScores()
            .enqueue(object : Callback<List<Account>> {
                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                    println("Ошибка братан")
                    Log.d("ne polu4ilos brat", "fail $t")
                }

                override fun onResponse(
                    call: Call<List<Account>>,
                    response: Response<List<Account>>
                ) {
                    fromAccounts.clear()
                    fromAccounts.addAll(response.body() ?: emptyList())
                    val operation: MutableList<String> = fromAccounts.map {
                        it.name + "(" + it.paymentType + ")"
                    }?.toMutableList()

                    fromAccountAdapter.clear()
                    fromAccountAdapter.addAll(operation)
                    Handler().post {
                        fromAccountAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

    private fun fetchToAccount() {
        RetrofitBuilder.getInstance().getScores()
            .enqueue(object : Callback<List<Account>> {
                override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                    println("Ошибка братан")
                    Log.d("ne polu4ilos brat", "fail $t")
                }

                override fun onResponse(
                    call: Call<List<Account>>,
                    response: Response<List<Account>>
                ) {
                    fromAccountsTwo.clear()
                    fromAccountsTwo.addAll(response.body() ?: emptyList())
                    val operation: MutableList<String> = fromAccounts.map {
                        it.name + "(" + it.paymentType + ")"
                    }?.toMutableList()

                    toAccountAdapter.clear()
                    toAccountAdapter.addAll(operation)
                    Handler().post {
                        toAccountAdapter.notifyDataSetChanged()
                    }
                }
            })
    }


    private fun createTransfer(
        date: String,
        sum: Int,
        scoreId: Int,
        score2Id: Int,
        description: String
    ) {
        val retrofitInstance = RetrofitBuilder.getInstance()
        val transferInfo = TransferModel(
            actionDate = date,
            sum = sum,
            scoreId = scoreId,
            score2Id = score2Id,
            description = description
        )
        retrofitInstance.createTransfer(transferInfo, RetrofitBuilder.getToken())
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //Toast.makeText(requireContext(), "Ошибка Создания Транзакции", Toast.LENGTH_SHORT).show()
                    Log.d("error", t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Перевод успешно совершен",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            })
    }
}
