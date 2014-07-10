package com.imcore.x_bionic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imcore.x_bionic.R;
import com.imcore.x_bionic.ui.ExpertStoryHomeActivity;

public class FragmentHomeFour extends Fragment{
	ImageView ivImage4;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.layout_fragment_home_four, null);
		ivImage4=(ImageView) view.findViewById(R.id.iv_home_activity_four);
		ivImage4.setImageResource(R.drawable.xactivtypage4);
		ivImage4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(getActivity(), ExpertStoryHomeActivity.class);
				startActivity(intent);
				
			}
		});
		return view;
	}
	
}
