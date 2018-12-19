package ratik.edu.fullerton.cpsc411.application2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class singleRecord : AppCompatActivity() {

    lateinit var myDB: DbHelper
    lateinit var etData: EditText
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    internal var id: String? = null
    internal var data: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_record)

        data = intent.extras!!.getString("data")
        id = intent.extras!!.getString("id")

        myDB = DbHelper(this)

        etData = findViewById<View>(R.id.editTextData) as EditText
        btnDelete = findViewById<View>(R.id.btn_done) as Button
        btnUpdate = findViewById<View>(R.id.btn_update) as Button
        etData.setText(data)

        btnUpdate.setOnClickListener {
            val newText = etData.text.toString()
            if (newText.length > 0) {

                dataUpdate(newText, id)

            } else {
                Toast.makeText(this@singleRecord, "Enter Data", Toast.LENGTH_LONG).show()
            }
        }

        btnDelete.setOnClickListener {
            val result = myDB.deleteData(id)

            if (result) {
                Toast.makeText(this@singleRecord, "Task Deleted", Toast.LENGTH_LONG).show()
                val new_intent = Intent(this@singleRecord, todoAcivity::class.java)
                startActivity(new_intent)
            } else {
                Toast.makeText(this@singleRecord, "Couldn't Delete", Toast.LENGTH_LONG).show()
            }
        }


    }

    fun dataUpdate(data: String, id: String?) {
        val result = myDB.updateData(data, id)
        if (result) {
            Toast.makeText(this@singleRecord, "Task Updated", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this@singleRecord, "Couldn't Update", Toast.LENGTH_LONG).show()
        }
    }
}
