package ru.startandroid.develop.financemanagment.test

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.edit_transaction_income.*
import kotlinx.android.synthetic.main.fragment_income.*
import kotlinx.android.synthetic.main.fragment_income.agentSpinner
import kotlinx.android.synthetic.main.fragment_income.categorySpinner
import kotlinx.android.synthetic.main.fragment_income.date
import kotlinx.android.synthetic.main.fragment_income.fromAccountSpinner
import kotlinx.android.synthetic.main.fragment_income.projectSpinner
import kotlinx.android.synthetic.main.fragment_income.sum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.startandroid.develop.financemanagment.R
import ru.startandroid.develop.financemanagment.activity.MainActivity
import ru.startandroid.develop.financemanagment.models.*
import ru.startandroid.develop.financemanagment.retrofit.RetrofitBuilder
import ru.startandroid.develop.util.OnItemSelectListener
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*

class EditIncomeTransaction : AppCompatActivity() {


    private val calendar: Calendar by lazy { Calendar.getInstance() }

    private lateinit var agentAdapter: ArrayAdapter<String>
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private lateinit var fromAccountAdapter: ArrayAdapter<String>
    private lateinit var projectNameAdapter: ArrayAdapter<String>
    private val fromAccounts = mutableListOf<Account>()
    private val fromCategory = mutableListOf<OperationResponse>()
    private val fromAgent = mutableListOf<CounterPartyResponce>()
    private val fromProject = mutableListOf<Project>()
    private var idTransaction: Int? = null
    private lateinit var dateTransaction: String
    private var sumTransaction: Int = 0
    private var operationId: Int? = null
    private var projectId: Int? = null
    private var counterPartyId: Int? = null
    private var scoreId: Int? = null
    private var description = "Тестовые данные"

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_transaction_income)

//        val extraDate = intent.getStringExtra("date")
//        val extraAccount = intent.getStringExtra("account")
//        val extraSum = intent.getStringExtra("sum")?.toInt()
//        val extraCategory = intent.getStringExtra("category")
//        val extraCounterParty = intent.getStringExtra("counterParty")
//        val extraProject = intent.getStringExtra("project")
        idTransaction = intent.getStringExtra("id")?.toInt()


        val textDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss").format(System.currentTimeMillis())
        editDate.setText(textDate)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd'T'HH:mm:ss.sss" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                val formatDate = sdf.format(calendar.time)
                editDate.setText(formatDate)
                dateTransaction = formatDate

            }

        editDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }



//        Toast.makeText(requireContext(), dateTransaction, Toast.LENGTH_SHORT).show()
        editFromAccountSpinner.onItemClickListener = OnItemSelectListener { position ->
            scoreId = fromAccounts[position].id
        }

        editSum.doAfterTextChanged {amount ->
            if(amount?.isDigitsOnly() == true) {
                sumTransaction = amount.toString().toInt()
            }
        }

        editCategorySpinner.onItemClickListener = OnItemSelectListener { position ->
            operationId = fromCategory[position].id
        }
        editAgentSpinner.onItemClickListener = OnItemSelectListener { position ->
            counterPartyId = fromAgent[position].id
        }
        editProjectSpinner.onItemClickListener = OnItemSelectListener { position ->
            projectId = fromProject[position].id
        }

        fromAccountAdapter = ArrayAdapter<String>(
            applicationContext, R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )
        categoryAdapter = ArrayAdapter<String>(
            applicationContext, R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )
        agentAdapter =
            ArrayAdapter<String>(
                applicationContext,
                R.layout.dropdown_menu_popup_item,
                arrayListOf()
            )
        projectNameAdapter = ArrayAdapter<String>(
            applicationContext, R.layout.dropdown_menu_popup_item,
            arrayListOf()
        )


        editFromAccountSpinner.setAdapter(fromAccountAdapter)
        editCategorySpinner.setAdapter(categoryAdapter)
        editAgentSpinner.setAdapter(agentAdapter)
        editProjectSpinner.setAdapter(projectNameAdapter)

        fetchFrom()
        fetchCategories()
        fetchAgent()
        fetchProjectNames()

        edit_btn.setOnClickListener {
            MainScope().launch(Dispatchers.Main) {
                editTransaction(
                    id = idTransaction!!,
                    date = dateTransaction,
                    sum = sumTransaction ?: 0,
                    operationId = operationId!!,
                    projectId = projectId!!,
                    scoreId = scoreId!!,
                    counterPartyId = counterPartyId!!,
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
                        fromCategory.map { it.name }
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


    private fun editTransaction(
        id: Int,
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
            EditTransactionModel(
                id = id,
                transactionDate = date,
                sum = sum,
                operationId = operationId,
                projectId = projectId,
                scoreId = scoreId,
                counterPartyId = counterPartyId,
                description = description
            )
        retrofitInstance.editTransaction(transactionInfo, RetrofitBuilder.getToken())
            .enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Транзакция успешно отредактирована",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
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