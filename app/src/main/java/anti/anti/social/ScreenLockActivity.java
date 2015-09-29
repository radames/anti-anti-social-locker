package anti.anti.social;

import anti.anti.social.lockutil.LockLayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ScreenLockActivity extends Activity {
    private static final String TAG = "ScreenLockActivity";
    public static boolean isShown = false;
    private static Context instance = null;

    private LockLayer lockLayer;
    private View lockView;

    private final int SUCCESS = 2;
    private final int FAILED = 1;

    private final long delay = 1500l;

    private Button btnConfirm;
    private TextView tvTopInfo, tvPsdReveal;
    private NumberPicker passwdNum;

    private int randomPassNum;

    private void showToast(String str, Context context) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShown = true;
        instance = ScreenLockActivity.this;
        randomPassNum = new Random().nextInt(11);
        Log.e(TAG, "THIS IS THE PASS: " + randomPassNum);

//		setContentView(R.layout.activity_screen_lock);
        init();
    }

    private void init() {
        Log.e(TAG, "ScreenLockActivity-> onCreate -> init");

        //init layout view
        lockView = View.inflate(this, R.layout.activity_screen_lock, null);

        //init lock layer
        lockLayer = LockLayer.getInstance(this);
        lockLayer.setLockView(lockView);
        lockLayer.lock();

        //Init Views
        btnConfirm = (Button) lockView.findViewById(R.id.btnConfirm);
        tvTopInfo = (TextView) lockView.findViewById(R.id.tvTopInfo);
        tvPsdReveal = (TextView) lockView.findViewById(R.id.tvPsdReveal);
        passwdNum = (NumberPicker) lockView.findViewById(R.id.passwdNum);
        passwdNum.setMaxValue(10);
        passwdNum.setMinValue(0);


        tvPsdReveal.setText("");


        //有密码的时候
        btnConfirm.setVisibility(View.VISIBLE);
        passwdNum.setVisibility(View.VISIBLE);

        tvTopInfo.setText("To Unlock Please Insert a Number between 0 and 10");

        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (passwdNum.getValue() == randomPassNum) {

                    mHandler.obtainMessage(SUCCESS).sendToTarget();

                } else {

                    mHandler.obtainMessage(FAILED).sendToTarget();

                }
            }
        });


    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case FAILED:
                    tvPsdReveal.setText("WRONG RANDOM NUMBER, TRY AGAIN");
                    mHandler.postDelayed(failedPass, delay);
                    break;

                case SUCCESS:
                    tvPsdReveal.setText("CORRECT RANDOM NUMBER, UNLOCKING");
                    mHandler.postDelayed(successPass, delay);
                    break;

                default:
                    break;
            }
        }

        ;
    };

    private Runnable successPass = new Runnable() {

        @Override
        public void run() {
            lockLayer.unlock();
            ScreenLockActivity.this.finish();
            showToast("Unlocking Success~", ScreenLockActivity.this);
            finish();
        }
    };

    private Runnable failedPass = new Runnable() {

        @Override
        public void run() {
            tvPsdReveal.setText("");
            //showToast("Unlocking Success~", ScreenLockActivity.this);
        }
    };

    @Override
    protected void onDestroy() {
        isShown = false;
        instance = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.screen_lock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static Context getInstance() {
        return instance;
    }
}
