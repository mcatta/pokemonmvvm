package com.giuseppesorce.architecture.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.giuseppesorce.architecture.CommonState
import com.giuseppesorce.architecture.ErrorState
import com.giuseppesorce.architecture.LoadingState

abstract class BaseActivityViewBinding<State : Any, Event : Any>() : AppCompatActivity() {


    abstract fun provideBaseViewModel(): BaseViewModel<State, Event>?
    abstract fun handleState(state: State)
    abstract fun handleEvent(event: Event)
    abstract fun setupUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getDataBindingiView())
        provideBaseViewModel()?.stateHolder()
            ?.observe(this, Observer { state -> handleState(state) })
        provideBaseViewModel()?.commonStateHolder()
            ?.observe(this, Observer { state -> handleCommonState(state) })
        provideBaseViewModel()?.eventHolder()
            ?.observe(this, Observer { event -> handleEvent(event) })
        setupUI()
        observerData()
        initActivity()
    }

    abstract fun getDataBindingiView(): View

    open fun initActivity() {

    }

    open fun observerData() {
    }

    private fun handleCommonState(commonState: CommonState) {
        handleLoading(commonState.loadingState)
        handleError(commonState.errorState)
    }

    open fun handleLoading(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Loading -> showLoadingState()
            is LoadingState.Idle -> showIdleState()
        }
    }

    open fun showLoadingState() {

    }

    open fun hideLoadingState() {
    }

    open fun showIdleState() {
        hideLoadingState()
//        errorDialog?.dismiss()
//        errorDialog = null
    }

    open fun handleError(errorState: ErrorState) {
        when (errorState) {
            is ErrorState.UnknownError -> displayUnknownError()
            is ErrorState.Error -> displayUnknownError()
        }
    }

    open fun displayUnknownError() {

    }


}