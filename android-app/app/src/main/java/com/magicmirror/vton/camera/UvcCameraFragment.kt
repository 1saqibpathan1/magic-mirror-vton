package com.magicmirror.vton.camera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.CameraFragment
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.ausbc.callback.ICaptureCallBack
import com.jiangdg.ausbc.widget.AspectRatioTextureView
import com.jiangdg.ausbc.widget.IAspectRatio
import java.io.File

class UvcCameraFragment : CameraFragment() {

    var controller: UvcCameraController? = null
        set(value) {
            field = value
            value?.attachFragment(this)
        }

    private lateinit var previewView: AspectRatioTextureView

    override fun getRootView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        previewView = AspectRatioTextureView(requireContext())
        return previewView
    }

    override fun getCameraView(): IAspectRatio = previewView

    override fun getCameraViewContainer(): ViewGroup? = null

    override fun onCameraState(
        self: MultiCameraClient.ICamera,
        code: ICameraStateCallBack.State,
        msg: String?
    ) {
        controller?.onCameraStateChanged(
            code == ICameraStateCallBack.State.OPENED
        )
    }

    fun captureCacheFile(name: String): File = File(requireContext().cacheDir, name)

    fun capturePhoto(outFile: File, onResult: (File?) -> Unit) {
        captureImage(object : ICaptureCallBack {
            override fun onBegin() = Unit

            override fun onError(error: String?) {
                onResult(null)
            }

            override fun onComplete(path: String?) {
                onResult(path?.let(::File) ?: outFile.takeIf { it.exists() })
            }
        }, outFile.absolutePath)
    }
}
