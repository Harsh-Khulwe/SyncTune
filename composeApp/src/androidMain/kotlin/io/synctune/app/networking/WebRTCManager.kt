package io.synctune.app.networking

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer

class WebRTCManager(private val context: Context) {
    private val peerConnectionFactory = WebRTCFactory.getConnectionFactory(context)

    fun createPeerConnection(iceServers: List<IceServer>): PeerConnection? {
        val rtcConfig = PeerConnection.RTCConfiguration(iceServers)
        return peerConnectionFactory.createPeerConnection(rtcConfig, object : PeerConnection.Observer {
            override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
                TODO("Not yet implemented")
            }

            override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
                TODO("Not yet implemented")
            }

            override fun onIceConnectionReceivingChange(p0: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
                TODO("Not yet implemented")
            }

            override fun onIceCandidate(iceCandidate: IceCandidate?) {
                iceCandidate?.let {
                    sendIceCandidateToFirebase(it)
                }
            }

            override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
                TODO("Not yet implemented")
            }

            override fun onAddStream(p0: MediaStream?) {
                TODO("Not yet implemented")
            }

            override fun onRemoveStream(p0: MediaStream?) {
                TODO("Not yet implemented")
            }

            override fun onDataChannel(p0: DataChannel?) {
                TODO("Not yet implemented")
            }

            override fun onRenegotiationNeeded() {
                TODO("Not yet implemented")
            }

            fun sendIceCandidateToFirebase(candidate: IceCandidate) {
                val iceData = mapOf(
                    "sdpMid" to candidate.sdpMid,
                    "sdpMLineIndex" to candidate.sdpMLineIndex,
                    "candidate" to candidate.sdp
                )

                val callId = "your-call-id" // common ID for both peers
                val userRole = "caller" // or "callee", depending on who you are

                Firebase.firestore.collection("calls")
                    .document(callId)
                    .collection("${userRole}Candidates")
                    .add(iceData)
            }
        })
    }
}