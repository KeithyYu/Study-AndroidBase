package com.wanggk.parcelable

import android.os.Parcel
import android.os.Parcelable

class Student() : Parcelable {
    var name: String = ""
    var age: Int = 0
    lateinit var score: Score

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        age = parcel.readInt()
        score = parcel.readParcelable(Score::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeParcelable(score, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }

}

class Score() : Parcelable {
    var math: Int = 0
    var english: Int = 0

    constructor(parcel: Parcel) : this() {
        math = parcel.readInt()
        english = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(math)
        parcel.writeInt(english)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Score> {
        override fun createFromParcel(parcel: Parcel): Score {
            return Score(parcel)
        }

        override fun newArray(size: Int): Array<Score?> {
            return arrayOfNulls(size)
        }
    }
}
