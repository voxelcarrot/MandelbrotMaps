package uk.ac.ed.inf.mandelbrotmaps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class FractalActivity extends Activity implements OnTouchListener, OnScaleGestureListener {
   private static final String TAG = "MMaps";
   private static final int INVALID_POINTER_ID = -1;
   
   private enum ControlMode{
	   PAN,
	   ZOOM
   }
   
   private ControlMode controlMode;

   private MandelbrotFractalView fractalView;
   private MandelbrotJuliaLocation mjLocation;
   
   private int dragLastX;
   private int dragLastY;
   
   private ScaleGestureDetector gestureDetector;
   
   private boolean draggingFractal = false;
   
   private int dragID = INVALID_POINTER_ID;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      Log.d(TAG, "onCreate");
      
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

      fractalView = new MandelbrotFractalView(this);
      //fractalView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
      setContentView(fractalView);
      fractalView.requestFocus();
      
      mjLocation = new MandelbrotJuliaLocation();
      fractalView.loadLocation(mjLocation);
      
      gestureDetector = new ScaleGestureDetector(this, this);
   }

   
   @Override
   protected void onResume() {
      super.onResume();
      Log.d(TAG, "onResume");
   }

   
   @Override
   protected void onPause() {
      super.onPause();
      Log.d(TAG, "onPause");
   }
   
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.navigationmenu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.ZoomOut:
    	  fractalView.zoomChange((int)(fractalView.getWidth()/2), (int)(fractalView.getHeight()/2), 1);
    	  return true;
      case R.id.ZoomIn:
    	  fractalView.zoomChange((int)(fractalView.getWidth()/2), (int)(fractalView.getHeight()/2), -1);
    	  return true;
      case R.id.PanUp:
    	  fractalView.shiftPixels(0, -100);
    	  fractalView.moveFractal(0, -100);
    	  return true;
      case R.id.PanDown:
    	  fractalView.shiftPixels(0, 100);
    	  fractalView.moveFractal(0, 100);
    	  return true;
      case R.id.PanLeft:
    	  fractalView.moveFractal(100, 0);
    	  return true;
      case R.id.PanRight:
    	  fractalView.moveFractal(-100, 0);
    	  return true;
      }
      return false;
   }


public boolean onTouch(View v, MotionEvent evt) {
	gestureDetector.onTouchEvent(evt);
	
	switch (evt.getAction() & MotionEvent.ACTION_MASK)
	{
		case MotionEvent.ACTION_DOWN:
			dragLastX = (int) evt.getX();
			dragLastY = (int) evt.getY();
			
			dragID = evt.getPointerId(0);	
			Log.d(TAG, "Initial dragID: " + dragID);
			return true;
			
		case MotionEvent.ACTION_MOVE:
			if(!draggingFractal)
			{
				fractalView.startDragging();
				draggingFractal = true;
				Log.d(TAG, "Started dragging");
			}
			
			if(!gestureDetector.isInProgress() && dragID != INVALID_POINTER_ID)
			{
				int pointerIndex = evt.findPointerIndex(dragID);
				
				int dragDiffPixelsX = (int) (evt.getX(pointerIndex) - dragLastX);
				int dragDiffPixelsY = (int) (evt.getY(pointerIndex) - dragLastY);
		
				// Move the canvas
				fractalView.dragFractal(dragDiffPixelsX, dragDiffPixelsY);
		
				// Update last mouse position
				dragLastX = (int) evt.getX(pointerIndex);
				dragLastY = (int) evt.getY(pointerIndex);
				return true;
			}
			
		case MotionEvent.ACTION_POINTER_UP:
			// Extract the index of the pointer that came up
	        final int pointerIndex = (evt.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        final int pointerId = evt.getPointerId(pointerIndex);
	        
	        if (pointerId == dragID) {
	            Log.d(TAG, "Choosing new active pointer");
	            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	            dragLastX = (int) evt.getX(newPointerIndex);
				dragLastY = (int) evt.getY(newPointerIndex);
	            dragID = evt.getPointerId(newPointerIndex);
	        }
	        break;
	        
		case MotionEvent.ACTION_UP:
			draggingFractal = false;
			fractalView.stopDragging();
			break;
	}
	return true;
}


public boolean onScale(ScaleGestureDetector detector) {
	Log.d(TAG, "Scaling");
	return true;
}


public boolean onScaleBegin(ScaleGestureDetector detector) {
	// TODO Auto-generated method stub
	return true;
}


public void onScaleEnd(ScaleGestureDetector detector) {
	// TODO Auto-generated method stub
	
}
}
