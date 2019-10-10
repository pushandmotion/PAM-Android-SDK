package com.pushandmotion.sdk.notification

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.pushandmotion.sdk.R


class PushNotiActivity : AppCompatActivity() {
    companion object {
        const val PUSHNOFICATION_INTENT_KEY = "push_intent_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_push_noti)


        //No activity stack in memory, we start new activity
//        val newIntent = Intent(this, NavigationActivity::class.java)
//        val pushMsg = intent.getStringExtra(PUSHNOFICATION_INTENT_KEY)
//        intent.removeExtra(PUSHNOFICATION_INTENT_KEY)
//        newIntent.putExtra(PUSHNOFICATION_INTENT_KEY, pushMsg)
//        startActivity(newIntent)
////        }
//
//        finish()
    }

}
