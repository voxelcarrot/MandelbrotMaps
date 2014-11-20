package uk.ac.ed.inf.mandelbrotmaps.refactor;

import android.graphics.Matrix;

import java.util.List;

import uk.ac.ed.inf.mandelbrotmaps.refactor.overlay.IFractalOverlay;

public interface IFractalView {
    public void postUIThreadRedraw();

    public void postThreadSafeRedraw();

    public void setResizeListener(IViewResizeListener resizeListener);

    public void setFractalTransformMatrix(Matrix fractalTransformMatrix);

    public void createNewFractalBitmap(int[] pixels);

    public void setBitmapPixels(int[] pixels);

    public void cacheCurrentBitmap(int[] pixelBuffer);

    public void setTouchHandler(IFractalTouchHandler handler);

    public void setSceneOverlays(List<IFractalOverlay> overlays);

    public void setPresenterOverlays(List<IFractalOverlay> overlays);
}
