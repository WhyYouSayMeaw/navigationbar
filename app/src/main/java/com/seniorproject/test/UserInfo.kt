package com.seniorproject.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserInfo : AppCompatActivity() {
    var gender : String = ""
    var agerange : String = ""
    var adddress : String = ""
    var likephoto : String = ""
    var car : String = ""
    var cluster : String = ""
    var username:String? = ""
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_user_info)

        val g1 = findViewById<RadioButton>(R.id.gender1)
        val g2 = findViewById<RadioButton>(R.id.gender2)
        //set value in radiogroup
        val gendergroup = findViewById<RadioGroup>(R.id.gendergroup)
        gendergroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.gender1 ){
                //gendergroup =
                gender = "male"
                //Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
                Log.d("gender",gender.toString())
            }
            else{
                gender = "female"
                //Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
                Log.d("gender",gender.toString())
            }
        }

        val agegroup = findViewById<RadioGroup>(R.id.agegroup)
        agegroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.agegroup1 ){
                agerange = "group1"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("age",agerange.toString())
            }
            else if(checkedId == R.id.agegroup2){
                agerange = "group2"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("age",agerange.toString())
            }
            else if(checkedId == R.id.agegroup3){
                agerange = "group3"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("age",agerange.toString())
            }
            else if(checkedId == R.id.agegroup4){
                agerange = "group4"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("age",agerange.toString())
            }
            else {
                agerange = "group5"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("age",agerange.toString())
            }
        }

        val ad = findViewById<RadioGroup>(R.id.addressgroup)
        ad.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.address1 ){
                adddress = "BKK"
                //Toast.makeText(this,agerange,Toast.LENGTH_SHORT).show()
                Log.d("address",adddress.toString())
            }
            else if(checkedId == R.id.address2){
                adddress = "NKP"
                //Toast.makeText(this,adddress,Toast.LENGTH_SHORT).show()
                Log.d("adddress",adddress.toString())
            }
            else if(checkedId == R.id.address3){
                adddress = "SMP"
                //Toast.makeText(this,adddress,Toast.LENGTH_SHORT).show()
                Log.d("adddress",adddress.toString())
            }
            else if(checkedId == R.id.address4){
                adddress = "NTB"
                //Toast.makeText(this,adddress,Toast.LENGTH_SHORT).show()
                Log.d("adddress",adddress.toString())
            }
            else if(checkedId == R.id.address5) {
                adddress = "PTN"
                //Toast.makeText(this,adddress,Toast.LENGTH_SHORT).show()
                Log.d("adddress",adddress.toString())
            }
            else {
                adddress = "SMK"
                //Toast.makeText(this,adddress,Toast.LENGTH_SHORT).show()
                Log.d("adddress",adddress.toString())
            }
        }

        val photo = findViewById<RadioGroup>(R.id.likephoto)
        photo.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.photo1 ){
                likephoto = "yes"
                //Toast.makeText(this,likephoto,Toast.LENGTH_SHORT).show()
                Log.d("likephoto",likephoto.toString())
            }
            else{
                likephoto = "no"
                //Toast.makeText(this,likephoto,Toast.LENGTH_SHORT).show()
                Log.d("likephoto",likephoto.toString())
            }
        }

        val havecar = findViewById<RadioGroup>(R.id.car)
        havecar.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.car1 ){
                car = "yes"
                //Toast.makeText(this,car,Toast.LENGTH_SHORT).show()
                Log.d("car",car.toString())
            }
            else{
                car = "no"
                //Toast.makeText(this,car,Toast.LENGTH_SHORT).show()
                Log.d("car",car.toString())
            }
        }

        val btn = findViewById<Button>(R.id.infobtn)
        btn.setOnClickListener {
            //case 1
            if((gender == "male" || gender == "female")
                    && agerange == "group1"
                    && (adddress == "BKK" || adddress == "NKP" || adddress == "SMP" || adddress == "NTB" || adddress == "PTN" || adddress == "SMK")
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster0"
            }
            // case 2
            else if((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    &&  adddress == "NKP"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster0"
            }
            //case 3
            else if((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    && adddress == "NTB"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster0"
            }
            /*//case 4
            else if ((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    && adddress == "BKK" && likephoto == "no" && car == "yes"){
                cluster = "cluster1"
            }
            //case 5
            else if((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    && adddress == "SMP"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster1"
            }
            //case 6
            else if((gender == "male" || gender == "female")
                    && agerange == "group3"
                    && likephoto == "yes"
                    && (car == "yes" || car == "no")){
                cluster = "cluster1"
            }
            //case 7
            else if( gender == "female" && agerange == "group2" && adddress == "BKK"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster1"
            }
            //case 8
            else if(  gender == "female" && agerange == "group2" && adddress == "NKP" &&  car == "no"
                    && (likephoto == "yes" || likephoto == "no")){
                cluster = "cluster1"
            }
            //case 9
            else if(gender == "male" && agerange == "group2"
                    && (adddress == "BKK" || adddress == "NKP" || adddress == "SMP" || adddress == "NTB" || adddress == "PTN" || adddress == "SMK")
                    &&  (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster1"
            }*/
            //case 10
            else if ((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    && adddress == "PTN"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster2"
            }
            //case 11
            else if((gender == "male" || gender == "female") && agerange == "group2" && adddress == "BKK"
                    && (likephoto == "yes" || likephoto == "no") && car == "yes"){
                cluster = "cluster2"
            }
            //case 12
            else if((gender == "male" || gender == "female")
                    && (agerange == "group1" || agerange == "group2" || agerange == "group3" || agerange == "group4" || agerange == "group5")
                    && adddress == "BKK"
                    && (likephoto == "yes" || likephoto == "no")
                    && (car == "yes" || car == "no")){
                cluster = "cluster3"
            }
            // others
            else cluster = "cluster1"

            //Toast.makeText(this,cluster.toString(),Toast.LENGTH_SHORT).show()
            Log.d("cluster",cluster)

            //InputData()
            mAuth = FirebaseAuth.getInstance()
            val currentUser = mAuth.currentUser
            username = currentUser?.displayName.toString()
            val user_name : String = username.toString()
            val Gender : String = gender
            val AgeRange : String = agerange
            val Address : String = adddress
            val Photo : String = likephoto
            val Car : String = car
            val Cluster : String = cluster

            val database = FirebaseDatabase.getInstance().getReference("users")
            database.child("Info").child(database.push().key.toString()).setValue(Users_Info(user_name,Gender,AgeRange,Address,Photo,Car,Cluster))
                    .addOnSuccessListener {
                        val intent = Intent(this, Navigationbar::class.java)
                        startActivity(intent)
                        finish()
                    }

        }

    }
}