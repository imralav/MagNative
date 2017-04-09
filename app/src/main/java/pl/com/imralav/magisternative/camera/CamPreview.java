package pl.com.imralav.magisternative.camera;


import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CamPreview {
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera camera = null;
    private boolean inPreview = false;
    private boolean cameraConfigured = false;
    private Context context;
    private int cameraId;

    public static CamPreview frontCamPreviewOn(SurfaceView view) {
        int frontCameraId = CameraHelper.getFrontCameraId();
        return new CamPreview(view, frontCameraId);
    }

    public static CamPreview backCamPreviewOn(SurfaceView view) {
        int backCameraId = CameraHelper.getBackCameraId();
        return new CamPreview(view, backCameraId);
    }

    public CamPreview withContext(Context context) {
        this.context = context;
        return this;
    }

    private CamPreview(SurfaceView view, int cameraId) {
        this.cameraId = cameraId;
        this.preview = view;
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void open() {
        camera = Camera.open(cameraId);
    }

    public void stop() {
        if(inPreview) {
            camera.stopPreview();
        }
        camera.release();
        camera = null;
        inPreview = false;
    }

    public void start() {
        if(cameraConfigured && camera != null) {
            camera.startPreview();
            inPreview = true;
        }
    }

    private void initPreview(int width, int height) {
        if (camera != null && previewHolder.getSurface() != null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (Throwable t) {
                Toast
                        .makeText(context, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = getBestPreviewSize(width, height,
                        parameters);

                if (size != null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured = true;
                }
            }
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return (result);
    }

    private void startPreview() {
        if (cameraConfigured && camera != null) {
            camera.startPreview();
            inPreview = true;
        }
    }

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaceChanged()
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };
}
