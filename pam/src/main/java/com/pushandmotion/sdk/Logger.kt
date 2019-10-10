package com.pushandmotion.sdk


class Logger{

    companion object{
        var enableLog = true

        fun log(msg:String){
            if( !enableLog ) {return}
            print("🦄 PAM : $msg")
        }
    }

}
