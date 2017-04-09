package pl.com.imralav.magisternative.camera;


import android.hardware.Camera;

public class CameraHelper {
    public static int getFrontCameraId() {
        return getCameraIdFacing(Camera.CameraInfo.CAMERA_FACING_FRONT, "Front camera not found");
    }

    public static int getBackCameraId() {
        return getCameraIdFacing(Camera.CameraInfo.CAMERA_FACING_BACK, "Back camera not found");
    }

    private static int getCameraIdFacing(int cameraFacingBack, String message) {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == cameraFacingBack) {
                return camIdx;
            }
        }
        throw new RuntimeException(message);
    }
}
