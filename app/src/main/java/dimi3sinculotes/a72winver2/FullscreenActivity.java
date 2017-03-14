package dimi3sinculotes.a72winver2;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        TextView t2 = (TextView)findViewById(R.id.textView3);
        temp = t2;

        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);

        randomize(b1, b2);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!getTimeUp()) {
                    butt1(1);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!getTimeUp()) {
                    butt1(2);
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(getTimeUp()) {
                    restart();
                }
            }
        });

        TextView t1 = (TextView) findViewById(R.id.textView);
        t1.setText("0");


        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



    // MINE -----------------------------------------------------------------------------

    private boolean primeraVez = true;
    private TextView temp;

    private int screenSize(char c) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (c == 'x') {
            return size.x - 40;
        } else if (c == 'y') {
            return size.y - 40;
        } else {
            return 0;
        }
    }

    public void butt1(int mode) {
        if(primeraVez){
            MyThread m1 = new MyThread(this);
            m1.start();
            primeraVez = false;
        }

        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        int x, y;
        if (mode == 1) {
            x = Integer.valueOf(b1.getText().toString());
            y = Integer.valueOf(b2.getText().toString());
        } else {
            y = Integer.valueOf(b1.getText().toString());
            x = Integer.valueOf(b2.getText().toString());
        }
        if (x >= y) {
            TextView t1 = (TextView) findViewById(R.id.textView);
            t1.setText(String.valueOf(Integer.valueOf(t1.getText().toString()) + 1));
        }
        randomize(b1, b2);
    }

    public void randomize(Button b1, Button b2) {
        b1.setX((float) (randomNum('x')));
        b1.setY((float) (randomNum('y')));
        float b2x = (float) (randomNum('x'));
        float b2y = (float) (randomNum('y'));

        while (((b2x > b1.getX() - 70) && (b2x < b1.getX() + 70)) || ((b2y > b1.getY() - 70) && (b2y < b1.getY() + 70))) {
            b2x = (float) (randomNum('x'));
            b2y = (float) (randomNum('y'));
        }

        b2.setX(b2x);
        b2.setY(b2y);

        b1.setText(String.valueOf(1 + (int) (Math.random() * 11)));
        b2.setText(String.valueOf(1 + (int) (Math.random() * 11)));
    }

    public int randomNum(char c){
        int i;
            if(c == 'x'){
                i = (int) (Math.random() * (screenSize(c) - 160));
            }else{
                i = (int) (180 + (Math.random() * (screenSize(c) - 400)));
            }
        return i;
    }
    private void time() {
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);

        String s1 = temp.getText().toString();
        int newTime = Integer.parseInt(s1) - 1;
        temp.setText(String.valueOf(newTime));

        if (getTimeUp()){
            gameOver(b1, b2);
        }
    }

    public boolean getTimeUp(){
        String time  = temp.getText().toString();
        int timei = Integer.parseInt(time);
        return (timei <= 0);
    }

    public void gameOver(Button b1, Button b2){
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        Button b3 = (Button)findViewById(R.id.button3);
        b3.setVisibility(View.VISIBLE);
    }
    private void restart(){
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);

        temp.setText("72");
        TextView t2 = (TextView)findViewById(R.id.textView);
        t2.setText("0");

        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.GONE);

        MyThread mt = new MyThread(this);
        mt.start();
    }

    class MyThread extends Thread {
        FullscreenActivity mainAct;

        public MyThread(FullscreenActivity ma){
            mainAct = ma;
        }
        @Override
        public void run() {
            while (!noEnd()) {
                try {
                    Thread.sleep(1000); //Time in between loops in ms.
                } catch (InterruptedException ex) {
                    System.out.println("Error thread");
                }
                // Things to do every loop
                mainAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        time();
                    }
                });
            }
        }
        private boolean noEnd() {
            return getTimeUp();
        }
    }
}
