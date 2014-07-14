package com.imcore.x_bionic.ui.fragment;

import java.util.ArrayList;

import com.imcore.x_bionic.R;
import com.imcore.x_bionic.model.SubCategoryDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class FragmentSubCategory extends Fragment{
	

	public FragmentSubCategory (ArrayList<SubCategoryDetail> list){
		
		
	}
	
	GridView gvSubCategory;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view=inflater.inflate(R.layout.fragment_layout_subcategory, null);
		gvSubCategory=(GridView) view.findViewById(R.id.gv_subcategory);
		gvSubCategory.setAdapter(new GVAdapter());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	class GVAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}
	
	
}
