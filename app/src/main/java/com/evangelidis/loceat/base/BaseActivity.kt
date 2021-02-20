package com.evangelidis.loceat.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.evangelidis.loceat.extensions.hide
import com.evangelidis.loceat.extensions.show
import com.evangelidis.loceat.R
import com.evangelidis.loceat.databinding.ActivityBaseBinding

abstract class BaseActivity<in V : BaseContract.View, out P : BaseContract.Presenter<V>> : AppCompatActivity(), BaseContract.View {

    private val baseBinding: ActivityBaseBinding by lazy { ActivityBaseBinding.inflate(layoutInflater) }
    protected val presenter: P by lazy { createPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        delegate.setContentView(baseBinding.root)
        presenter.onAttach(this as V)
    }

    override fun setContentView(view: View?) {
        baseBinding.baseContent.apply {
            removeAllViews()
            addView(view)
        }
    }

    override fun showLoader(text: String) {
        baseBinding.baseSpinner.loadingMessage.text = text
        baseBinding.baseSpinner.root.show()
    }

    override fun hideLoader() {
        baseBinding.baseSpinner.root.hide()
    }

    abstract fun createPresenter(): P
}