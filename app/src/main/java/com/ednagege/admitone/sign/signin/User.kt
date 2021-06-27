package com.ednagege.admitone.sign.signin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var email:String ?="",
    var name:String ?="",
    var password:String ?="",
    var balance:String ?="",
    var url:String ?="",
    var username:String ?=""
) : Parcelable