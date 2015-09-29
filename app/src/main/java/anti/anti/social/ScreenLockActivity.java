package anti.anti.social;

import anti.anti.social.listener.OnFlingCompleteListener;
import anti.anti.social.lockutil.LockLayer;
import anti.anti.social.unlock.util.PasswordUtil;
import anti.anti.social.view.FlingRelativeLayout;

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
	
	private final int NEED_DELAY = 1;
	private long delay = 100l;
	
	private Button btnConfirm, btnClear;
	private TextView tvTopInfo, tvPsdReveal;
	private NumberPicker passwdNum;

	private FlingRelativeLayout flingRelativeLayout;
	private int randomPassNum;

	private void showToast(String str, Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isShown = true;
		instance = ScreenLockActivity.this;
		randomPassNum = new Random().nextInt(10);
		Log.e(TAG, "THIS IS THE PASS: " + randomPassNum );

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
		flingRelativeLayout = (FlingRelativeLayout) lockView.findViewById(R.id.flingRelativeLayout);
		btnConfirm = (Button) lockView.findViewById(R.id.btnConfirm);
		btnClear = (Button) lockView.findViewById(R.id.btnClear);
		tvTopInfo = (TextView) lockView.findViewById(R.id.tvTopInfo);
		tvPsdReveal = (TextView) lockView.findViewById(R.id.tvPsdReveal);
		passwdNum = (NumberPicker) lockView.findViewById(R.id.passwdNum);
		passwdNum.setMaxValue(9);
		passwdNum.setMinValue(0);



		tvPsdReveal.setText("");
		
		
		
		if (PasswordUtil.hasPsd(instance)) {
			//有密码的时候
			btnConfirm.setVisibility(View.VISIBLE);
			btnClear.setVisibility(View.VISIBLE);
			passwdNum.setVisibility(View.VISIBLE);

			tvTopInfo.setText("Please Insert a Number between 0 and 9");
			
			flingRelativeLayout.setOnFlingCompleteListener(new OnFlingCompleteListener() {
				
				@Override
				public void onFlingComplete(int curDirection) {
					//每次手势结束时候触发
					if (!PasswordUtil.curPsd.equals("")) {
						tvPsdReveal.setText(""+PasswordUtil.curPsd);
					}
				}
			});
						
			btnConfirm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(passwdNum.getValue() == randomPassNum){
						showToast("Back, correct password", instance);
						PasswordUtil.curPsd="";
						tvPsdReveal.setText("");
						lockLayer.unlock();
						finish();
					}
					if (PasswordUtil.validatePsd(instance)) {
						//密码输入正确
						showToast("Enter the correct password, welcome back ~", instance);
						PasswordUtil.curPsd="";//每次都是自动帮你去清空整个密码
						tvPsdReveal.setText("");

						lockLayer.unlock();
						finish();
					} else {
						//密码错误
						//清空密码
						PasswordUtil.curPsd="";
						tvPsdReveal.setText("");
						showToast("Password input error, please re-enter", instance);
					}
				}
			});
			
			btnClear.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					PasswordUtil.curPsd = "";
					tvPsdReveal.setText("");
					showToast("Enter the password has been cleared", instance);
				}
			});
			
		} else {
			//没有密码的时候
			btnConfirm.setVisibility(View.INVISIBLE);
			btnClear.setVisibility(View.INVISIBLE);
			passwdNum.setVisibility(View.INVISIBLE);
			tvTopInfo.setText("Swipe to unlock any direction~");
			flingRelativeLayout.setOnFlingCompleteListener(new OnFlingCompleteListener() {
				
				@Override
				public void onFlingComplete(int curDirection) {
					Log.e(TAG, "curDirection: "+curDirection);
					//一旦监听到有滑动手势的时候，最好判断一下，不为0，就可以finish()
					if (curDirection != 0) {
						PasswordUtil.curPsd="";//清空，因为如果不清空的话，每次滑动其实是把我们的滑动代码存储了起来
						mHandler.obtainMessage(NEED_DELAY).sendToTarget();
//						ScreenLockActivity.this.finish();//比起直接结束，效果更好
					}
				}
			});
			
		}
		
	}
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case NEED_DELAY:
				mHandler.postDelayed(finishCurAct, delay);
				break;

			default:
				break;
			}
		};
	};
	
	private Runnable finishCurAct = new Runnable() {
		
		@Override
		public void run() {
			lockLayer.unlock();
			
			ScreenLockActivity.this.finish();
			showToast("Unlocking Success~", ScreenLockActivity.this);
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
