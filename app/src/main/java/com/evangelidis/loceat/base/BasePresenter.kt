package com.evangelidis.loceat.base

open class BasePresenter<T : BaseContract.View> : BaseContract.Presenter<T> {

    var view: T? = null

    override fun onAttach(view: T) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
    }

    fun isViewAttached(): Boolean = view != null

}