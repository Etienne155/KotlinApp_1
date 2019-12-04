package com.etien.mykotlinapplication2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val urlAPOD = "https://api.nasa.gov/planetary/apod?api_key=UFMUzL1CFPs2aarVh9nQAqwi9ASq2UUi5eRCGowh"

        var textCopyright = findViewById<TextView>(R.id.textCopyright)
        val buttonHello = findViewById<Button>(R.id.buttonHello)
        val imageAPOD = findViewById<ImageView>(R.id.imageAPOD)

        buttonHello?.setOnClickListener{

            doAsync {

                val data = URL(urlAPOD).readText()
                val json = JSONObject(data)

                val apodData = APODData(
                    json.get("copyright").toString(),
                    json.get("date").toString(),
                    json.get("explanation").toString(),
                    json.get("media_type").toString(),
                    json.get("title").toString(),
                    json.get("url").toString()
                )

                uiThread {
                    textCopyright.setText("Copyright: " + apodData.copyright)

                    Picasso
                        .get()
                        .load(apodData.url)
                        .into(imageAPOD)

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
