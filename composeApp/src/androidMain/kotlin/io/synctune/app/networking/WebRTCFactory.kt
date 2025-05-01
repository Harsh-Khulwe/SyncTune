package io.synctune.app.networking

import android.content.Context
import org.webrtc.PeerConnectionFactory

object WebRTCFactory {
    private var connectionFactory: PeerConnectionFactory? = null

    fun getConnectionFactory(context: Context): PeerConnectionFactory{
        if(connectionFactory == null) {
            PeerConnectionFactory.initialize(
                PeerConnectionFactory.InitializationOptions.builder(context).createInitializationOptions()
            )
            connectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory()
        }

        return connectionFactory!!
    }
}