package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.mobinsafaeian.detfhappinesspeyk.R

class MainFragment : Fragment() {

    //inflate the xml file to view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    //main method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}