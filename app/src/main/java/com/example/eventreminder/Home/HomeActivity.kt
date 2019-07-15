package com.example.eventreminder.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.eventreminder.Adapter.RecyclerAdapter
import com.example.eventreminder.Model.Events.Data
import com.example.eventreminder.R
import com.facebook.AccessToken
import com.facebook.GraphRequest
import org.json.JSONException
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.logo
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout
import android.widget.EditText
import com.example.eventreminder.Constants


class HomeActivity : AppCompatActivity() {


    private lateinit var viewModel: HomeViewModelActivity
    private lateinit var eventsArrayAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        loadUserProfile(AccessToken.getCurrentAccessToken())

        viewModel = ViewModelProviders.of(this).get(HomeViewModelActivity::class.java)
        viewModel.response.observe(this@HomeActivity, Observer {
            if (it == null) {
                Toast.makeText(this@HomeActivity, Constants.NO_DATA_FOUND, Toast.LENGTH_SHORT).show()
            } else {

                if (checkForOverlapping(it)) {
                    sendEmailOnOverllaping(it)
                }


                /* Getting Weather Response
                  *
                   * @TODO : weather Data Conditions should be retrieved when sending City from events Data ,But Data retrieved
                   * @TODO: from Events Graph facebook , was null in some objects so i used just Forecasting weather for 5 days ..
                   * */
                viewModel.WeatherResponse.observe(this@HomeActivity, Observer { response ->
                    eventsArrayAdapter =
                        RecyclerAdapter(this@HomeActivity, it as ArrayList<Data>, response)
                    recycler.adapter = eventsArrayAdapter

                })


            }
        })

    }

    private fun checkForOverlapping(it: List<Data>): Boolean {
        /* Check for Overlapping  */
        for (i in 0..it.size) {
            for (j in i + 1 until it.size) {
                println("i = $i; j = $j")
                if (it[i].startTime == it[j].startTime) {
                    return true
                }
            }

        }
        return false
    }

    private fun showAlerDialog(
        input: EditText
    ): String {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)
        var postponedMeeting = "example"

        // set message of alert dialog
        dialogBuilder.setMessage(Constants.DIALOG_MESSAGE)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton(Constants.DONE) {

                    dialog, id ->
                finish()
                postponedMeeting = input.text.toString()


            }
            // negative button text and action
            .setNegativeButton(Constants.CANCEL) { dialog, id ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setView(input)
        // set title for alert dialog box
        alert.setTitle(Constants.ALERT_TITLE)
        // show alert dialog
        alert.show()
        return postponedMeeting

    }

    private fun sendEmailOnOverllaping(it: List<Data>) {
        /* Make Edit Text for Dialog  */
        val input = EditText(this@HomeActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        Toast.makeText(this@HomeActivity, "Overlapping", Toast.LENGTH_SHORT).show()
        /* Show Dialog to take meeting place  */
        var email = showAlerDialog(input)

        /* send This place By Email  */
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("${it[0].name}@example.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.SUBJECT_EMAIL)
        intent.putExtra(Intent.EXTRA_TEXT, Constants.POSTPONE_MEETING + "$email")
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                this@HomeActivity,
                Constants.NO_EMAIL_CLIENTS,
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }

    private fun loadUserProfile(newAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            newAccessToken
        ) { `object`, response ->
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                val email = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url = "https://graph.facebook.com/$id/picture?type=normal"
                Log.i("facebook", "$first_name,$last_name,$email,$id,$newAccessToken")
                val requestOptions = RequestOptions()
                requestOptions.dontAnimate()
                Glide.with(this@HomeActivity).load(image_url).into(logo)


                viewModel.getResult(id, newAccessToken.token)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()

    }


}





