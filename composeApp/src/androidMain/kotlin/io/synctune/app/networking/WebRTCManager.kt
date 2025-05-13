package io.synctune.app.networking

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.webrtc.*

class WebRTCManager(
    private val context: Context,
    private val callId: String,
    private val userRole: String // "caller" or "callee"
) {

    private val peerConnectionFactory = WebRTCFactory.getConnectionFactory(context)
    private val firestore = Firebase.firestore
    private var peerConnection: PeerConnection? = null

    fun initConnection(iceServers: List<PeerConnection.IceServer>) {
        val rtcConfig = PeerConnection.RTCConfiguration(iceServers)
        peerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, object : PeerConnection.Observer {
            override fun onSignalingChange(state: PeerConnection.SignalingState?) {}
            override fun onIceConnectionChange(state: PeerConnection.IceConnectionState?) {}
            override fun onIceConnectionReceivingChange(receiving: Boolean) {}
            override fun onIceGatheringChange(state: PeerConnection.IceGatheringState?) {}

            override fun onIceCandidate(candidate: IceCandidate?) {
                candidate?.let { sendIceCandidateToFirebase(it) }
            }

            override fun onIceCandidatesRemoved(candidates: Array<out IceCandidate>?) {}
            override fun onAddStream(stream: MediaStream?) {}
            override fun onRemoveStream(stream: MediaStream?) {}
            override fun onDataChannel(dc: DataChannel?) {}
            override fun onRenegotiationNeeded() {}
        })
    }

    fun createOffer() {
        val constraints = MediaConstraints()
        peerConnection?.createOffer(object : SdpObserverAdapter() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                desc?.let {
                    peerConnection?.setLocalDescription(SdpObserverAdapter(), it)
                    sendSessionDescriptionToFirebase(it, "offer")
                }
            }
        }, constraints)
    }

    fun createAnswer(offer: SessionDescription) {
        peerConnection?.setRemoteDescription(SdpObserverAdapter(), offer)

        val constraints = MediaConstraints()
        peerConnection?.createAnswer(object : SdpObserverAdapter() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                desc?.let {
                    peerConnection?.setLocalDescription(SdpObserverAdapter(), it)
                    sendSessionDescriptionToFirebase(it, "answer")
                }
            }
        }, constraints)
    }

    fun setRemoteDescription(desc: SessionDescription) {
        peerConnection?.setRemoteDescription(SdpObserverAdapter(), desc)
    }

    private fun sendSessionDescriptionToFirebase(desc: SessionDescription, type: String) {
        val sdpMap = mapOf(
            "type" to desc.type.canonicalForm(),
            "sdp" to desc.description
        )

        firestore.collection("calls")
            .document(callId)
            .update(type, sdpMap)
    }

    private fun sendIceCandidateToFirebase(candidate: IceCandidate) {
        val iceData = mapOf(
            "sdpMid" to candidate.sdpMid,
            "sdpMLineIndex" to candidate.sdpMLineIndex,
            "candidate" to candidate.sdp
        )

        firestore.collection("calls")
            .document(callId)
            .collection("${userRole}Candidates")
            .add(iceData)
    }

    fun listenForRemoteIceCandidates() {
        val remoteRole = if (userRole == "caller") "callee" else "caller"
        firestore.collection("calls")
            .document(callId)
            .collection("${remoteRole}Candidates")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.documentChanges?.forEach { change ->
                    val data = change.document.data
                    val candidate = IceCandidate(
                        data["sdpMid"] as String?,
                        (data["sdpMLineIndex"] as Long).toInt(),
                        data["candidate"] as String
                    )
                    peerConnection?.addIceCandidate(candidate)
                }
            }
    }

    fun listenForSessionDescription(type: String) {
        firestore.collection("calls")
            .document(callId)
            .addSnapshotListener { snapshot, _ ->
                val data = snapshot?.get(type) as? Map<*, *> ?: return@addSnapshotListener
                val sdp = data["sdp"] as? String ?: return@addSnapshotListener
                val sdpType = data["type"] as? String ?: return@addSnapshotListener

                val sessionDescription = SessionDescription(
                    SessionDescription.Type.fromCanonicalForm(sdpType),
                    sdp
                )

                if (type == "offer" && userRole == "callee") {
                    createAnswer(sessionDescription)
                } else if (type == "answer" && userRole == "caller") {
                    setRemoteDescription(sessionDescription)
                }
            }
    }

    fun sendTrackToPeer(streamUrl: String, startTimestamp: Long) {
        val syncData = mapOf(
            "url" to streamUrl,
            "startTime" to startTimestamp
        )

        firestore.collection("calls")
            .document(callId)
            .collection("sync")
            .document("currentTrack")
            .set(syncData)
    }

    fun listenForTrackSync(onTrackReceived: (url: String, startTime: Long) -> Unit) {
        firestore.collection("calls")
            .document(callId)
            .collection("sync")
            .document("currentTrack")
            .addSnapshotListener { docSnapshot, _ ->
                val url = docSnapshot?.getString("url") ?: return@addSnapshotListener
                val startTime = docSnapshot.getLong("startTime") ?: return@addSnapshotListener

                onTrackReceived(url, startTime)
            }
    }

}
