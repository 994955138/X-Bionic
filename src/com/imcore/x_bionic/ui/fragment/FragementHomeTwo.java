package com.imcore.x_bionic.ui.fragment;

import com.imcore.x_bionic.R;
import com.imcore.x_bionic.ui.IntroductionHomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragementHomeTwo extends Fragment{

	ImageView ivImage2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.layout_fragment_home_two, null);
		ivImage2=(ImageView) view.findViewById(R.id.iv_home_activity_two);

		ivImage2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(getActivity(), IntroductionHomeActivity.class);
				startActivity(intent);
				
			}
		});
		return view;
	}
	
}
