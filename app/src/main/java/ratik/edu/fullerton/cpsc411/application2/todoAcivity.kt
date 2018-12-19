package ratik.edu.fullerton.cpsc411.application2

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast

import java.util.ArrayList
import java.util.Collections

import ratik.edu.fullerton.cpsc411.application2.todoAcivity.*

class todoAcivity : AppCompatActivity() {

    lateinit var myDB: DbHelper
    lateinit var btnAdd: Button
    lateinit var etData: EditText
    lateinit var lv: ListView
    internal var update: Boolean = false
    lateinit var ID: String

    lateinit var dataList: ArrayList<String>
    lateinit var idList: ArrayList<String>

    lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_acivity)

        myDB = DbHelper(this)

        etData = findViewById<View>(R.id.etNewItem) as EditText
        btnAdd = findViewById<View>(R.id.btnAddItem) as Button
        lv = findViewById<View>(R.id.lvItems) as ListView


        populateList()

        btnAdd.setOnClickListener {
            val newText = etData.text.toString()
            if (newText.length > 0) {

                AddRecord(newText)


                etData.setText("")
            } else {
                Toast.makeText(this@todoAcivity, "Enter Data", Toast.LENGTH_LONG).show()
            }
        }

        lv.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //Object obj= lv.getItemAtPosition(position);
            ID = idList[position]
            val str = dataList[position]

            //Toast.makeText(todoAcivity.this,str,Toast.LENGTH_LONG).show();

            //etData.setText(str);
            //btnAdd.setText("Update");
            //update=true;
            val new_intent = Intent(this@todoAcivity, singleRecord::class.java)
            val mBundle = Bundle()
            mBundle.putString("data", str)
            mBundle.putString("id", ID)
            new_intent.putExtras(mBundle)
            startActivity(new_intent)
        }
    }

    override fun onResume() {
        super.onResume()
        populateList()
    }

    fun populateList() {
        dataList = ArrayList()
        idList = ArrayList()

        val data = myDB.data


        if (data.count == 0) {
            Toast.makeText(this@todoAcivity, "No record found", Toast.LENGTH_LONG).show()
        } else {
            while (data.moveToNext()) {
                dataList.add(data.getString(1))
                idList.add(data.getString(0))

            }
            Collections.reverse(dataList)
            Collections.reverse(idList)
            listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
            lv.adapter = listAdapter
        }
    }

    fun AddRecord(data: String) {
        val result = myDB.addData(data)
        if (result) {
            Toast.makeText(this@todoAcivity, "Task Added", Toast.LENGTH_LONG).show()
            populateList()
        } else {
            Toast.makeText(this@todoAcivity, "Error Occured", Toast.LENGTH_LONG).show()
        }
    }


}
