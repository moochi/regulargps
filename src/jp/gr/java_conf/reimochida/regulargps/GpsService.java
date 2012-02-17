package jp.gr.java_conf.reimochida.regulargps;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GpsService extends Service implements LocationListener {
	
	private LocationManager locationManager;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        
        //1.GPS情報が変化した場合に呼び出されるコールバック関数を設定

        /*
         * minDistance: onLocationChanged()が実行されるための移動距離間隔(単位：メートル)
         *              この距離だけ移動するとonLocationChanged()が実行される
         *              GPSの状態によって誤差があるのであくまで目安
         * 
         * minTime    : onLocationChanged()が実行されるための間隔秒数(単位：ミリ秒)
         *              この秒数だけ経過するとonLocationChanged()が実行される（多分）
         *              今回はonLocationChanged()の発動条件を移動距離とするので0とした
         * 
         * LocationManagerクラスの説明
         * http://developer.android.com/intl/ja/reference/android/location/LocationManager.html
         * 
         */
        int minTime     = ((1000 * 1) * 60) * 10; //10分
        int minDistance = 1000;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);        
    }

	@Override
	public void onLocationChanged(Location location) {
        final String gpsString =
        	"緯度:" + Double.toString(location.getLatitude()) + "\n" +
        	"経度:" + Double.toString(location.getLongitude()) + "\n" +
        	"高度:" + Double.toString(location.getAltitude()) + "\n" +
        	"精度:" + Double.toString(location.getAccuracy()) + "\n";
        Log.d("GPSSEVICE", gpsString);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(this);
	}

}
