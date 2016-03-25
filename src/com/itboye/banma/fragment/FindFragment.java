package com.itboye.banma.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.itboye.banma.R;
import com.itboye.banma.adapter.GridViewAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductItem;

public class FindFragment extends Fragment implements StrUIDataListener{
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private View productGridView;
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;
	private GridViewAdapter gridViewAdapter;
	private TextView title;
	private  PullToRefreshGridView mPullRefreshGridView;
	private int mItemCount = 15;
	private 	ProgressBar dialog;//显示正在加载;
	private PullToRefreshGridView pull_refresh_grid;
	private Boolean 	YesOrNo;
	private int pageNow=1;
	private ImageView iv_back;
	private ArrayList<ProductItem> productlist ;
	private int page=0;//list数目
	private int state=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		productGridView = inflater.inflate(R.layout.fragment_find, container, false);
		initView();
		initDatas();
		initIndicator();
		return productGridView;
	}


	private void initIndicator() {
		// TODO Auto-generated method stub
		ILoadingLayout startLabels = mPullRefreshGridView
				.getLoadingLayoutProxy(true, false);
		//startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("释放刷新");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = mPullRefreshGridView.getLoadingLayoutProxy(
				false, true);
	//	endLabels.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在刷新加载");// 刷新时
		endLabels.setReleaseLabel("释放刷新");// 下来达到一定距离时，显示的提示
	}


	private void initDatas()
	{
			try {
				YesOrNo = appContext.getProductList(getActivity(), pageNow,
						Constant.PAGE_SIZE,null, networkHelper);
				if (!YesOrNo) { // 如果没联网
					Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {

				e.printStackTrace();
			}


	}

	private void initView() {
		// TODO Auto-generated method stub
		title=(TextView)productGridView.findViewById(R.id.title);
		title.setText("发现");
		iv_back=(ImageView)productGridView.findViewById(R.id.iv_back);
		iv_back.setVisibility(View.GONE);
		dialog=(ProgressBar) productGridView.findViewById(R.id.progressBar);
		mPullRefreshGridView = (PullToRefreshGridView) productGridView.findViewById(R.id.pull_refresh_grid);
	}
	
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		productlist = new ArrayList<ProductItem>();
		productlist.clear();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);
			System.out.println("发现PPPPPPPPPPP"+data.toString());
			int code = jsondata.getInt("code");
			if (code == 0) {
				System.out.println(data.toString());
				String producData = jsondata.getString("data");
				jsondata = new JSONObject(producData);
				page=(jsondata.getInt("count"))/20+1;
				String producList = jsondata.getString("list");
				productlist = gson.fromJson(producList,
						new TypeToken<List<ProductItem>>() {
						}.getType());
				if (productlist != null) {
					showGridView();
					gridViewAdapter.notifyDataSetChanged();
					// Call onRefreshComplete when the list has been refreshed.
				}else {
					dialog.setVisibility(View.GONE);
				}
			} else {
				dialog.setVisibility(View.GONE);
//				Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPullRefreshGridView.onRefreshComplete();
	}

	private void showGridView() {
		// TODO Auto-generated method stub
		dialog.setVisibility(View.GONE);
		mPullRefreshGridView.setVisibility(View.VISIBLE);
	//	mPullRefreshGridView.setVisibility(View.VISIBLE);
		gridViewAdapter = new GridViewAdapter(getActivity(), productlist);
		mPullRefreshGridView.setAdapter(gridViewAdapter);
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>()
				{
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView)
					{
						Log.e("TAG", "onPullDownToRefresh"); // Do work to
						String label = DateUtils.formatDateTime(
								getActivity().getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						updateDataRed();
					}

					private void updateDataAdd() {
						// TODO Auto-generated method stub
						if(page>pageNow){
							pageNow+=1;
						}
						
						try {
							appContext.getProductList(getActivity(), pageNow,
									Constant.PAGE_SIZE, null,networkHelper);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					private void updateDataRed() {
						// TODO Auto-generated method stub				
						pageNow-=1;
						if (pageNow<1) {
							pageNow=1;
						}
						try {
							appContext.getProductList(getActivity(), pageNow,
									Constant.PAGE_SIZE, null, networkHelper);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView)
					{
						Log.e("TAG", "onPullUpToRefresh"); // Do work to refresh
															// the list here.
					
						updateDataAdd();
					}
				});
	}
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		if (state<=1&&AppContext.isTokenSuccess()==false) {
//			dialog.setVisibility(View.VISIBLE);
//			state+=1;
//			initDatas();
//		}
//	}

}
