package kr.ac.gnu.bingbingcon;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;

public class BeaconServiceUtility {

	private Context context;
	private PendingIntent pintent;
	private AlarmManager alarm;
	private Intent iService;

	public BeaconServiceUtility(Context context) {
		super();
		this.context = context;
		iService = new Intent(context, BeaconDetactorService.class);
		alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		pintent = PendingIntent.getService(context, 0, iService, 0);
	}

	public void onStart() {
		/** 
		 * Binds an Android Activity or Service to the IBeaconService. 
		 * The Activity or Service must implement the IBeaconConsuemr interface 
		 * so that it can get a callback when the service is ready to use. 
		 * **/
		startBackgroundScan();

	}

	public void onStop(IBeaconManager iBeaconManager, IBeaconConsumer consumer) {

		iBeaconManager.unBind(consumer);

	}

	private void stopBackgroundScan() {
		alarm.cancel(pintent);
		context.stopService(iService);
	}

	private void startBackgroundScan() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 2);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 360000, pintent); // 6*60 * 1000
		context.startService(iService);
	}

}
