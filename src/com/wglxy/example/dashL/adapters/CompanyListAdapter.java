package com.wglxy.example.dashL.adapters;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wglxy.example.dashL.R;

public class CompanyListAdapter extends ViewInflaterBaseAdapter<String> {
	
	ArrayList<String> data = new ArrayList<String>();
	
	public CompanyListAdapter(ViewInflater inflater, ArrayList<String> data) {
		super(inflater, data);
		super.setInflater(inflater);
		this.data = data;
	}
	
	// inflater
	public static class Inflater implements ViewInflaterBaseAdapter.ViewInflater {

		int feature;
		
		public Inflater(){}
		
		public Inflater(int feature){
			this.feature = feature;
		}
		
		private class ViewHolder {
			ImageView img_biz_pic;
			TextView txt_biz_name;
			TextView txt_biz_loc;
			RatingBar rb_ratings;

		}

		@Override
		public View inflate(ViewInflaterBaseAdapter adapter, int pos,
				View ConvertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View rowView = ConvertView;

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.list_item_search, parent,
						false);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.img_biz_pic = (ImageView)rowView.findViewById(R.id.img_biz_pic);
				viewHolder.txt_biz_name = (TextView)rowView.findViewById(R.id.txt_biz_name);
				viewHolder.txt_biz_loc = (TextView)rowView.findViewById(R.id.txt_biz_loc);
				viewHolder.rb_ratings = (RatingBar)rowView.findViewById(R.id.rb_ratings);
				
				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			
			//TODO get data
			String biz_name;
			String biz_loc; 

			// TODO set data
			
			switch(this.feature){
			case 1:	//electronics
				biz_name = "Home Tide Electronics";
				biz_loc = "ABC Place, Westlands";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.electronics));
				break;
				
			case 2: //mechanics
				biz_name = "Quick Fix Garage";
				biz_loc = "Posta, Ngong Rd";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.car_repair));
				break;
				
			case 3:	//electricians
				biz_name = "Home Tide Electricians";
				biz_loc = "opp Hotel Arizona, River Rd";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.options));
				break;
				
			case 4:	//plumbers
				biz_name = "Reliable Plumbers";
				biz_loc = "ABC Place";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.package_utilities));
				break;
				
			case 5:	//cable tv
				biz_name = "Smart TV";
				biz_loc = "Luthuli Street";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.television_alt));
				break;
				
			case 6:	//house repairs
				biz_name = "Nyumba Poa Repairs";
				biz_loc = "Makina, Kibera, Nbi";
				viewHolder.img_biz_pic.setImageDrawable(
						parent.getContext().getResources().getDrawable(R.drawable.home_icon));
				break;
				
				default:
					biz_name = "Fundi Sasa";
					biz_loc = "Bishop Magua Center, Ngong Rd";
					viewHolder.img_biz_pic.setImageDrawable(
							parent.getContext().getResources().getDrawable(R.drawable.home_icon));
					break;
			}
			
			
			viewHolder.txt_biz_name.setText(biz_name);
			viewHolder.txt_biz_loc.setText(biz_loc);
//			viewHolder.rb_ratings.setRating();

			return rowView;
		}

	}
	
}
