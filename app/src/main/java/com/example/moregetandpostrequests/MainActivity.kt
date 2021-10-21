package com.example.moregetandpostrequests
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var ed1:EditText
    private lateinit var ed2:EditText
    private lateinit var ed3:EditText

    private lateinit var btnAdd:Button
    private lateinit var btnGet:Button

    private lateinit var rv:RecyclerView

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
    val names = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed1 = findViewById(R.id.ed1)
        ed2 = findViewById(R.id.ed2)
        ed3 = findViewById(R.id.ed3)

        btnAdd = findViewById(R.id.btnAdd)
        btnGet = findViewById(R.id.btnGet)

        rv = findViewById(R.id.rv)

        btnAdd.setOnClickListener {
            val newUser = DataItem(ed1.text.toString())
            val newUser2 = DataItem(ed2.text.toString())
            val newUser3 = DataItem(ed3.text.toString())
            addUser(newUser)
            addUser(newUser2)
            addUser(newUser3)


        }
        btnGet.setOnClickListener {
            if (apiInterface != null) {
                apiInterface.getUser()?.enqueue(object : Callback<Array<DataItem>?> {
                    override fun onResponse(
                        call: Call<Array<DataItem>?>,
                        response: Response<Array<DataItem>?>
                    ) {
                        val users=response.body()!!
                        for (user in users) {
                            names.add(user.name)
                        }
                        rv.adapter = MyAdap(this@MainActivity,names)
                        rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                    override fun onFailure(call: Call<Array<DataItem>?>, th: Throwable) {
                        Toast.makeText(applicationContext, "${th.message}", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }


    }

    fun addUser(newUser: DataItem) {
        if (apiInterface != null) {
            apiInterface.addUser(newUser).enqueue(object : Callback<DataItem?> {
                override fun onResponse(call: Call<DataItem?>, response: Response<DataItem?>) {
                    ed1.text.clear()
                    ed2.text.clear()
                    ed3.text.clear()

                    Toast.makeText(applicationContext, "users added", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<DataItem?>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()

                }
            })
        }

    }



}