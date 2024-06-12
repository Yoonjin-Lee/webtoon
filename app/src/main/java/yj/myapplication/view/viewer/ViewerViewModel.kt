package yj.myapplication.view.viewer

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor() : ViewModel() {
    fun finish(context: Context){
        (context as Activity).finish()
    }

    private val _undoSharedFlow = MutableSharedFlow<Unit>()
    val undoSharedFlow = _undoSharedFlow.asSharedFlow()

    private val _redoSharedFlow = MutableSharedFlow<Unit>()
    val redoSharedFlow = _redoSharedFlow.asSharedFlow()

    fun undo() {
        viewModelScope.launch {
            _undoSharedFlow.emit(Unit)
        }
    }

    fun redo() {
        viewModelScope.launch {
            _redoSharedFlow.emit(Unit)
        }
    }
}