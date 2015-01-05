package com.example.herephone;

import java.util.ArrayList;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;


public class backgroundService extends Service
{
	
	public Context context;
	private BroadcastReceiver receiver;
	@Override 
	public void onCreate()
	{
		context = this;
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		
		receiver = new BroadcastReceiver()
		{
			@Override 
			public void onReceive(Context context, Intent intent)
			{
				if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
				{
					executeVoice();
				}
				else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
				{
					
				}
				
			}
			
		};
		
		registerReceiver(receiver, filter);
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		
		
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		
		return null;
	}
	
	@Override
	public void onDestroy()
	{
		unregisterReceiver(receiver);
		
	}
	
	private void executeVoice()
	{
		Recog recog = new Recog();
		
		SpeechRecognizer sp;
		sp = SpeechRecognizer.createSpeechRecognizer(this);
		sp.setRecognitionListener(recog);
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		sp.startListening(intent);
		
		
	}
	
	private class Recog implements RecognitionListener
	{
		@Override
		public void onBeginningOfSpeech(){}
		
		@Override
		public void onBufferReceived(byte[] buffer){}
		
		@Override
		public void onEndOfSpeech(){}
		
		@Override
		public void onError(int error){}
		
		@Override
		public void onEvent(int eventType, Bundle params){}
		
		@Override
		public void onPartialResults(Bundle partialResults){}
		
		@Override
		public void onReadyForSpeech(Bundle params){}
		
		@Override
		public void onResults(Bundle results)
		{
			int b = 0;
			ArrayList<String> res = new ArrayList<String>();
			res = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			for(int i=0; i<res.size(); i++)
			{
				if( res.get(i).contains("ear") || res.get(i).contains("ere") || res.get(i).contains("our") )
				{
					b = 1;
				}
				if( res.get(i).contains("one") || res.get(i).contains("phone"))
				{
					b = b + 1;
					
				}
				if(b > 1)
				{
					Vibrator vr;
					
					vr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vr.vibrate(2000);
					MediaPlayer mp = new MediaPlayer();
					mp = MediaPlayer.create(context, R.raw.dog1);
							mp.start();
			
				}
				
			}
			
			
		}
		
		@Override
		public void onRmsChanged(float rmsdB){}
		

	}
	
	
}