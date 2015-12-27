package com.example.locationmangertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{
	static final int MIN_TIME = 5000; //��m��s����:5000 �@��(=5��)
	static final float MIN_DIST = 5; //��m��s����: 5 ����
	LocationManager locmgr; //�w��޲z��
	TextView txv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txv = (TextView)findViewById(R.id.textVeiw1);
		
		
		//���o�t�ΪA�Ȫ� LocationManager����
		locmgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, this);
//		locmgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
		
		
		if(locmgr.isProviderEnabled(LocationManager.GPS_PROVIDER) || locmgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			locationServiceInitial();
		}else{
			Toast.makeText(MainActivity.this, "�ж}�ҩw��A��", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
		}
		
	}
	
	private void locationServiceInitial() {
//		String bestProvider = LocationManager.GPS_PROVIDER;
//		locmgr = (LocationManager)getSystemService(LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
		
		while(true)
		{
			Location location = locmgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			//if(locmgr == null){
			if(location == null){
				Toast.makeText(MainActivity.this, "locationServiceInitial", Toast.LENGTH_LONG).show();						
				}else{
					Toast.makeText(MainActivity.this, "locmgr != null", Toast.LENGTH_LONG).show();
					getLocation(location);
					break;
				}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//		bestProvider = locmgr.getBestProvider(criteria, true);
	

	private void getLocation(Location location) {
		if(location != null){
			Double longitude = location.getLongitude();
			Double latitude = location.getLatitude();
			
			String values = String.valueOf(longitude) + " ; " + String.valueOf(latitude);
			txv.setText(values);
		}else{
			Toast.makeText(MainActivity.this, "�L�k�w��y��" , Toast.LENGTH_LONG).show();
		}
	
		
	}

	@Override
	protected void onResume() {
		super.onResume();
//		locmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, this);
//		Toast.makeText(MainActivity.this, "onResume", Toast.LENGTH_LONG).show();
		
//		//���o�̨Ϊ��w�촣�Ѫ�.  true:��X�w�ҥΪ����Ѫ�
//		String best = locmgr.getBestProvider(new Criteria(), true);
//		
//		//�p�G���w�촣�Ѫ̥i��
//		if(best != null){
//			txv.setText("���o�w���Ƥ�...");
//			//���U��m�ƥ��ť��
//			locmgr.requestLocationUpdates(best, MIN_TIME, MIN_DIST, this);
//		}else{
//			txv.setText("�нT�{�w�}�ҩw��\��");
//		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//�������U��s�ƥ�
		locmgr.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		 Log.i("onLocationChanged", "come in");
         if (location != null)
         {
             Log.w("Location","Current altitude = "+ location.getAltitude()); 
             Log.w("Location","Current latitude = "+ location.getLatitude());
         }
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
}
