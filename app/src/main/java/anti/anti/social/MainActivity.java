package anti.anti.social;

import anti.anti.social.unlock.util.PasswordUtil;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private Button btnStaSer;
	private Button btnStaPsdSetting;
	private Button btnAbout, btnLeave;
	private Context context;

	private void showToast(String str,Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



        init();
	}

	private void init() {
		context = MainActivity.this;

		btnStaSer = (Button) findViewById(R.id.button1);
		btnStaSer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent jmpLS = new Intent();
				jmpLS.setAction("anti.anti.social.service.LocalService");
				MainActivity.this.startService(jmpLS);
				showToast("Open the lock screen", MainActivity.this);
				PasswordUtil.setDefaultPsd(context);//这个是测试用，初始密码
				//This is a test, the initial password
			}
		});

		btnStaPsdSetting = (Button) findViewById(R.id.button2);
		btnStaPsdSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent jmpPS = new Intent(MainActivity.this, SetPsdActivity.class);
				startActivity(jmpPS);
				showToast("Gesture password here", MainActivity.this);
			}
		});

		btnAbout = (Button) findViewById(R.id.button3);
		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showToast("Here is Software", context);

				ScreenLockActivity.isShown = true;
				Intent jmpSLA = new Intent();
				jmpSLA.setClass(context, ScreenLockActivity.class);
				jmpSLA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(jmpSLA);


			}
		});

		btnLeave = (Button) findViewById(R.id.button4);
		btnLeave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast("Bye", context);
				finish();
				PasswordUtil.curPsd = "";//还是处理一下
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

}
