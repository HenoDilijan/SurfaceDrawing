package club.aborigen.module3drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private DrawThread drawThread;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        try {
            drawThread.join();
        } catch (InterruptedException ignored) {}
    }

    public class DrawThread extends Thread {
        private int colors []= { Color.GREEN, Color.YELLOW, Color.RED };
        private volatile boolean running = true;

        public DrawThread() {}

        public void requestStop() {
            running = false;
            interrupt();
        }

        @Override
        public void run() {
            int index = 0;
            while (running) {
                try {
                    Canvas canvas = getHolder().lockCanvas();
                    canvas.drawColor(colors[index % colors.length]);
                    getHolder().unlockCanvasAndPost(canvas);
                    index++;
                    Thread.sleep(2000);
                } catch (Exception ignored) {}
            }
        }
    }
}
