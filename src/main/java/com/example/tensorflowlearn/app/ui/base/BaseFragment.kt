package com.example.tensorflowlearn.app.ui.base

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.tensorflowlearn.R

/**
 * Created by AralBenli on 18.04.2023.
 */
abstract class BaseFragment<VB : ViewBinding> :
    Fragment() {
        lateinit var binding: VB

    abstract fun getViewBinding(): VB
    abstract fun initViews()
    private var savedInstanceView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceView == null) {
            onCreateViewBase()
            binding = getViewBinding()
            savedInstanceView = binding.root
            savedInstanceView
        } else {
            savedInstanceView
        }


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    open fun onCreateViewBase(){}
    open fun observer(){}

    fun showCustomToast(status : Status, message: String, fragment: Fragment) {

        val layout = fragment.layoutInflater.inflate(R.layout.custom_toast_layout,null)

        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        val imageView = layout.findViewById<ImageView>(R.id.titleIcon)

        when(status){
            Status.Success -> imageView.setImageResource(R.drawable.ic_done_24)
            Status.Failed -> imageView.setImageResource(R.drawable.ic_close_24)
        }

        val myToast = Toast(this.context)
        myToast.duration = Toast.LENGTH_SHORT
        myToast.setGravity(Gravity.BOTTOM, 0, 200)
        myToast.view = layout //setting the view of custom toast layout
        myToast.show()

    } enum class Status {
        Success,
        Failed ,
    }
}

