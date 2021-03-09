package ru.startandroid.develop.financemanagment.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.agentSpinner
import kotlinx.android.synthetic.main.fragment_income.categorySpinner
import kotlinx.android.synthetic.main.fragment_income.date
import kotlinx.android.synthetic.main.fragment_income.fromAccountSpinner
import kotlinx.android.synthetic.main.fragment_income.projectSpinner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.activity.MainActivity
import ru.startandroid.develop.financemanagment.R

import ru.startandroid.develop.financemanagment.models.*
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder
import ru.startandroid.develop.financemanagment.models.TransactionModel
import ru.startandroid.develop.util.OnItemSelectListener
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeFragment : Fragment() {


    private val calendar: Calendar by lazy { Calendar.getInstance() }

    private lateinit var agentAdapter: ArrayAdapter<String>
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private lateinit var fromAccountAdapter: ArrayAdapter<String>
    private lateinit var projectNameAdapter: ArrayAdapter<String>
    private val fromAccounts = mutableListOf<Account>()
    private val fromCategory = mutableListOf<OperationResponse>()
    private val fromAgent = mutableListOf<CounterPartyResponce>()
    private val fromProject = mutableListOf<Project>()
    private lateinit var dateTransaction: String
    private var sumTransaction: Int = 0
    private var operationId: Int? = null
    private var projectId: Int? = null
    private var counterPartyId: Int? = null
    private var scoreId: Int? = null
    private var description = "Тестовые данные"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_income, container, false)



    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
//        fromAccountSpinner.onItemClickListener = OnItemSelectListener {position ->
//            Toast.makeText(requireContext()," id = ${fromAccount.get(position)}",Toast.LENGTH_SHORT).show()
//        }

        dateTransaction = date.text.toString()
//        Toast.makeText(requireContext(), dateTransaction, Toast.LENGTH_SHORT).show()
        fromAccountSpinner.onItemClickListener = OnItemSelectListener { position ->
            scoreId = fromAccounts[position].id
        }

        try {
            if (sum == null) {
                sum.doAfterTextChanged {
                    sumTransaction = it.toString().toInt()
                }
            }
        } catch (e: NumberFormatException) {
            Log.d("error", e.message.toString())
            println("error")
        }




        categorySpinner.onItemClickListener = OnItemSelectListener { position ->
            operationId = fromCategory[position].id
        }
        agentSpinner.onItemClickListener = OnItemSelectListener { position ->
            counterPartyId = fromAgent[position].id
        }
        projectSpinner.onItemClickListener = OnItemSelectListener { position ->
            projectId = fromProject[position].id
        }


        fromAccountAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )
        categoryAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )
        agentAdapter =
            ArrayAdapter<String>(
                requireContext(),
                R.layout.dropdown_menu_popup_item,
                arrayListOf()
            )
        projectNameAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )

        fromAccountSpinner.setAdapter(fromAccountAdapter)
        categorySpinner.setAdapter(categoryAdapter)
        agentSpinner.setAdapter(agentAdapter)
        projectSpinner.setAdapter(projectNameAdapter)

        fetchFrom()
        fetchCategories()
        fetchAgent()
        fetchProjectNames()


        save_btn.setOnClickListener {
            MainScope().launch(Dispatchers.Main) {
                createTransaction(
                    date = dateTransaction,
                    sum = sumTransaction ?: 0,
                    operationId = operationId!!,
                    projectId = projectId!!,
                    counterPartyId = counterPartyId!!,
                    scoreId = scoreId!!,
                    description = description
                )
            }
        }


    }

    private fun fetchCategories() {
        RetrofitBuilder.getInstance().getOperations()
            .enqueue(object : Callback<List<OperationResponse>> {
                override fun onFailure(call: Call<List<OperationResponse>>, t: Throwable) {
                    println("Ошибка братан")
                    Log.d("suka", "fail $t")
                }

                override fun onResponse(
                    call: Call<List<OperationResponse>>,
                    response: Response<List<OperationResponse>>
                ) {

                    fromCategory.clear()
                    fromCategory.addAll(response.body() ?: emptyList())
                    val categories: List<String> =
                        fromCategory.map { it.name  }
                    categoryAdapter.clear()
                    categoryAdapter.addAll(categories)
                    Handler().post {
                        categoryAdapter.notifyDataSetChanged()
                    }
                }
            })
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
                        it.name
                    }?.toMutableList()

                    fromAccountAdapter.clear()
                    fromAccountAdapter.addAll(operation)
                    Handler().post {
                        fromAccountAdapter.notifyDataSetChanged()
                    }
                }
            })
    }


    private fun fetchAgent() {
        RetrofitBuilder.getInstance().getCounterParty()
            .enqueue(object : Callback<List<CounterPartyResponce>> {
                override fun onFailure(call: Call<List<CounterPartyResponce>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<List<CounterPartyResponce>>,
                    response: Response<List<CounterPartyResponce>>
                ) {
                    fromAgent.clear()
                    fromAgent.addAll(response.body() ?: emptyList())
                    val temp = fromAgent.map { it.name }.toMutableList()
                    agentAdapter.clear()
                    agentAdapter.addAll(temp)
                    Handler().post {
                        agentAdapter.notifyDataSetChanged()
                    }
                }
            })
    }


    private fun fetchProjectNames() {
        RetrofitBuilder.getInstance().getProject().enqueue(object : Callback<List<Project>> {
            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<Project>>,
                response: Response<List<Project>>
            ) {
                fromProject.clear()
                fromProject.addAll(response.body() ?: emptyList())
                val temp = fromProject.map { it.nameProject }.toList()
                projectNameAdapter.clear()
                projectNameAdapter.addAll(temp)
                Handler().post {
                    projectNameAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun createTransaction(
        date: String,
        sum: Int,
        operationId: Int,
        projectId: Int,
        counterPartyId: Int,
        scoreId: Int,
        description: String
    ) {
        val retrofitInstance = RetrofitBuilder.getInstance()
        val transactionInfo =
            TransactionModel(
                transactionDate = date,
                sum = sum,
                operationId = operationId,
                projectId = projectId,
                counterPartyId = counterPartyId,
                scoreId = scoreId,
                description = description
            )
        retrofitInstance.createTransaction(transactionInfo, RetrofitBuilder.getToken())
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Транзакция успешно создана",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //Toast.makeText(requireContext(), "Ошибка Создания Транзакции", Toast.LENGTH_SHORT).show()
                    Log.d("error", t.message.toString())

                }


            })
    }

}
