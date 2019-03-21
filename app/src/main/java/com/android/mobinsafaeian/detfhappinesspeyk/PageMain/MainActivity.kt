package com.android.mobinsafaeian.detfhappinesspeyk.PageMain


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.BaseActivity
import com.android.mobinsafaeian.detfhappinesspeyk.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , MainViewInterface{

    private lateinit var fragment: MainFragment
    private lateinit var presenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializations
        init()

        //Load Fragment in frameLayout
        loadFragment(fragment)


    }

    /**
     * initializations
     */
    private fun init() {
        fragment = MainFragment()
        presenter = MainPresenter(this)

        //get IP from server
        presenter.getIPFromServer()
    }

    /**Load Fragment in main_fragment_container to have one activity and multiple fragments in here
     * @param fragment is the fragment that will be loaded
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container , fragment)
            .commit()
    }

    /**
     * whenever data received from server , this method will be called by presenter to update the ui
     * @param ip is the user ip that retrieved from server
     */
    override fun updateUi(ip: String) {
        ip_progress_bar.visibility = View.GONE
        ip_text.visibility = View.VISIBLE
        ip_text.text = ip
    }

    /**
     * whenever retrieving data faced with a problem , presenter will call this method to show the error message
     * @param message is the error message that should be shown
     */
    override fun showError(message: String) {
       SimpleDialog("Error" , message)
    }

    /**
     * when app is in onPause mode , we should call the unbind() method to dispose disposable variable
     */
    override fun onPause() {
        super.onPause()
        presenter.unbind()
    }

    // show a simple exit dialog when user clicks on backPressedButton
    override fun onBackPressed() {
        ExitDialog("Exit" , "Do you want to exit?" , "yes" , "no")
    }
}
