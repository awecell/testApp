package com.example.logoquiz_screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.logoquiz.R;

public class Home_screen extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        ((Button)findViewById(R.id.playButton)).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playButton:
			startActivity(new Intent(this,Level_screen.class));
			break;

		default:
			break;
		}
	}
    
    

}
