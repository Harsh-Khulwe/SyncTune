package io.synctune.app.networking

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription
import android.util.Log

open class SdpObserverAdapter(
    private val tag: String = "SDP",
    private val onCreateSuccess: ((SessionDescription) -> Unit)? = null,
    private val onSetSuccess: (() -> Unit)? = null
) : SdpObserver {

    override fun onCreateSuccess(desc: SessionDescription?) {
        Log.d(tag, "onCreateSuccess: $desc")
        desc?.let { onCreateSuccess?.invoke(it) }
    }

    override fun onSetSuccess() {
        Log.d(tag, "onSetSuccess")
        onSetSuccess?.invoke()
    }

    override fun onCreateFailure(error: String?) {
        Log.e(tag, "onCreateFailure: $error")
    }

    override fun onSetFailure(error: String?) {
        Log.e(tag, "onSetFailure: $error")
    }
}
