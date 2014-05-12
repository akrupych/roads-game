package andriy.krupych.roads;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	private static final String TAG = GameView.class.getSimpleName();
	private static final float ERROR_RADIUS = 1;
	
	private List<PointF> mPoints = new ArrayList<PointF>();
	private Paint paint = new Paint();

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.WHITE);
		Log.d(TAG, "ctor");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, String.format("onTouchEvent %d %f %f",
				event.getAction(), event.getX(), event.getY()));
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			mPoints = new ArrayList<PointF>();
		savePoint(event.getX(), event.getY());
		return true;
	}
	
	private void savePoint(float x, float y) {
		if (!mPoints.isEmpty()) {
			PointF last = mPoints.get(mPoints.size() - 1);
			if (Math.abs(x - last.x) <= ERROR_RADIUS ||
					Math.abs(y - last.y) <= ERROR_RADIUS)
				return;		
		}
		mPoints.add(new PointF(x, y));
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		canvas.drawColor(Color.BLACK);
//		for (int i = 0; i < mPoints.size(); i++) {
//			PointF current = mPoints.get(i);
//			canvas.drawPoint(current.x, current.y, paint);
//		}
		if (mPoints.size() < 2) return;
		for (int i = 1; i < mPoints.size(); i++) {
			PointF current = mPoints.get(i);
			PointF previous = mPoints.get(i - 1);
			canvas.drawLine(previous.x, previous.y, current.x, current.y, paint);
		}
	}

}
