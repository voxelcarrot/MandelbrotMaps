package uk.ac.ed.inf.mandelbrotmaps.refactor;

import android.graphics.Matrix;

import uk.ac.ed.inf.mandelbrotmaps.refactor.strategies.IFractalPresenterDelegate;

public interface IFractalPresenter extends IFractalPresenterDelegate {
    public int[] getPixelBuffer();

    public void translatePixelBuffer(int dx, int dy);

    public void recomputeGraph(int pixelBlockSize);

    public void notifyRecomputeComplete(int pixelBlockSize);

    public int getMaxIterations();

    public double getPixelSize();

    public void setFractalDetail(double detail);

    public void setView(IFractalView view, Matrix matrix, IViewResizeListener listener);

    public double[] getGraphPositionFromClickedPosition(float touchX, float touchY);

    public double[] getPointFromGraphPosition(double pointX, double pointY);

    // Graph area affecting

    public void translateGraphArea(int dx, int dy);

    public void zoomGraphArea(int x, int y, double scale);

    public void setGraphArea(double[] graphArea);
}
