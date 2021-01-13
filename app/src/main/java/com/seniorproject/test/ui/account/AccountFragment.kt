package com.seniorproject.test.ui.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider
import com.mikhaellopez.circularimageview.CircularImageView
import com.seniorproject.test.R
import com.seniorproject.test.SignIn

class AccountFragment : Fragment() {
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        accountViewModel =
//                ViewModelProvider(this).get(AccountViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_account, container, false)
//        val textView: TextView = root.findViewById(R.id.text_account)
//        accountViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        val name: TextView = root.findViewById(R.id.name_txt)
        val mail: TextView = root.findViewById(R.id.email_txt)
        name.text = currentUser?.displayName
        mail.text = currentUser?.email
        val circularImageView = root.findViewById<CircularImageView>(R.id.circularImageView)
        Glide.with(this).load(currentUser?.photoUrl).circleCrop().into(circularImageView)
        circularImageView.apply {
            // Set Color
            circleColor = Color.WHITE
            // or with gradient
            circleColorStart = Color.WHITE
            circleColorEnd = Color.WHITE
            circleColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Set Border
            borderWidth = 10f
            borderColor = Color.WHITE
            // or with gradient
            borderColorStart = Color.WHITE
            borderColorEnd = Color.WHITE
            borderColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Add Shadow with default param
            shadowEnable = true
            // or with custom param
            shadowRadius = 7f
            shadowColor = Color.WHITE
            shadowGravity = CircularImageView.ShadowGravity.CENTER
        }
        val button: Button = root.findViewById(R.id.sign_out_btn)
        button.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(getActivity(), SignIn::class.java)
            startActivity(intent)
        }

        return root
    }
}