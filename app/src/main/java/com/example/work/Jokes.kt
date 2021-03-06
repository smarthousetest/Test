package com.example.work
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.jokes_fragment.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.StringBuilder
import java.net.URL

class Jokes : Fragment() {
    lateinit var nUrl:String
    private val URL = "https://api.icndb.com/jokes/random/"
    lateinit var newUrl:String
    var okHttpClient: OkHttpClient = OkHttpClient()

    companion object {
        fun newInstance() = Jokes()
    }

    private lateinit var viewModel: JokesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.jokes_fragment,container,false)
val count = rootView.findViewById<EditText>(R.id.count_jokes)
        rootView.findViewById<TextView>(R.id.text).movementMethod = ScrollingMovementMethod()

        rootView.findViewById<Button>(R.id.button3)?.setOnClickListener() {
            val count1 = count.text.toString().toIntOrNull()
            nUrl= count1.toString()
            if (count1 == null){
                Toast.makeText(context,"Введите еще раз",Toast.LENGTH_LONG).show()
            }else {
                when {
                    nUrl.toInt() > 574 -> {
                        Toast.makeText(context, "Число больше 574", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        newUrl = "$URL$nUrl"
                        loadRandomFact()
                    }
                }
            }
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JokesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("KEY",text.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        text.text=savedInstanceState?.getString("KEY")
    }

    private fun loadRandomFact() {

        val request: Request = Request.Builder().url(newUrl).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val json = response.body()?.string()
                val array= JSONObject(json).getJSONArray("value")
                val data = StringBuilder()
                for (i in 0 until array.length()){
                    val jsonObject = array.getJSONObject(i)
                    val joke = jsonObject.optString("joke")
                    data.append( "${i+1}.").append("$joke\n")
                }
                activity?.runOnUiThread {
                    val text = view?.findViewById<TextView>(R.id.text)
                    if (text != null) {
                        text.text = data.toString()

                    }
                }
            }
        })
    }

}

