package com.evangelidis.loceat.base

interface BaseContract {

    interface View{
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter<in T : View> {
        fun onAttach(view: T)
        fun onDetach()
    }
}