package com.example.mypaint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mypaint.view.DrawPanelView;
import com.example.mypaint.view.XCArcMenuView;
import com.example.mypaint.view.XCArcMenuView.OnMenuItemClickListener;
import com.json.service.SocketService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	private static final int GALLERY_REQUEST_CODE = 0;
	int window_width,window_height;
	TextView button1, button2, button3, button4, button5, button6,
	texttool1,texttool2,texttool3,texttool4,texttool5,texttool6,texttool,texttool_1,seek_text;
	Button but_file1, but_file2;
	EditText ed;
	TextView backcolor[];
	ArrayAdapter<String> arr, arr1;
	List<Map<String, Object>> data;
	SimpleAdapter sim_color;
	GridView paintsize;
	DrawPanelView drawpanelview;
	XCArcMenuView xcarcmenuview;
	AlertDialog.Builder builder;
	Uri uri;
	View contentView;
	PopupWindow popupwindow;
	TextView color_back;
	SocketService socketService = new SocketService();
	
	DisplayMetrics metric = new DisplayMetrics();
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		exitdialog() ;
		getWindowManager().getDefaultDisplay().getMetrics(metric);
         window_width = metric.widthPixels;     // 屏幕宽度（像素）
         window_height = metric.heightPixels;   // 屏幕高度（像素）
        Log.d("WH", window_width+"-------"+window_height);
		socketService.start();

		drawpanelview = (DrawPanelView) findViewById(R.id.mview);
		xcarcmenuview = (XCArcMenuView) findViewById(R.id.arcmenu);
		builder = new AlertDialog.Builder(this);
		arrString();
	}

	private void paintColor() {

		backcolor = new TextView[] {
				(TextView) contentView.findViewById(R.id.color1),
				(TextView) contentView.findViewById(R.id.color2),
				(TextView) contentView.findViewById(R.id.color3),
				(TextView) contentView.findViewById(R.id.color4),
				(TextView) contentView.findViewById(R.id.color5),
				(TextView) contentView.findViewById(R.id.color6),
				(TextView) contentView.findViewById(R.id.color7),
				(TextView) contentView.findViewById(R.id.color8),
				(TextView) contentView.findViewById(R.id.color9),
				(TextView) contentView.findViewById(R.id.color10),
				(TextView) contentView.findViewById(R.id.color11),
				(TextView) contentView.findViewById(R.id.color12),
				(TextView) contentView.findViewById(R.id.color13),
				(TextView) contentView.findViewById(R.id.color14),
				(TextView) contentView.findViewById(R.id.color15),
				(TextView) contentView.findViewById(R.id.color16),
				(TextView) contentView.findViewById(R.id.color17),
				(TextView) contentView.findViewById(R.id.color18),
				(TextView) contentView.findViewById(R.id.color19),
				(TextView) contentView.findViewById(R.id.color20),

		};
		// 循环添加选择颜色事件
		for (int i = 0; i < backcolor.length; i++) {
			backcolor[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int j = 0; j < backcolor.length; j++) {
						if (v.getId() == backcolor[j].getId()) {
							texttool.setTextColor(backcolor[j].getCurrentTextColor());
							drawpanelview.Setpaintcolor(backcolor[j]
									.getCurrentTextColor());
							Log.d("颜色", "" + backcolor[j].getCurrentTextColor());
							popupwindow.dismiss();
							drawpanelview.Setcolor_of(j + 1);
							
						}
					}

				}
			});

		}
	}

	public void arrString() {// 初始化组件
		Typeface face = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
		button1 = (TextView) findViewById(R.id.button1);
		button2 = (TextView) findViewById(R.id.button2);
		button3 = (TextView) findViewById(R.id.button3);
		button4 = (TextView) findViewById(R.id.button4);
		button5 = (TextView) findViewById(R.id.button5);
		button6 = (TextView) findViewById(R.id.button6);
		texttool=(TextView) findViewById(R.id.id_button);
		texttool_1=(TextView) findViewById(R.id.id_button1);
		texttool1= (TextView) findViewById(R.id.xiang);
		texttool2= (TextView) findViewById(R.id.xiangpi);
		texttool3= (TextView) findViewById(R.id.zhixiang);
		texttool4= (TextView) findViewById(R.id.juxing);
		texttool5= (TextView) findViewById(R.id.yuan);
		texttool6= (TextView) findViewById(R.id.sanjiao);
		
		button1.setTypeface(face);
		button2.setTypeface(face);
		button3.setTypeface(face);
		button4.setTypeface(face);
		button5.setTypeface(face);
		button6.setTypeface(face);
		texttool.setTypeface(face);
		texttool_1.setTypeface(face);
		texttool1.setTypeface(face);
		texttool2.setTypeface(face);
		texttool3.setTypeface(face);
		texttool4.setTypeface(face);
		texttool5.setTypeface(face);
		texttool6.setTypeface(face);
		texttool_1.setTextColor(Color.WHITE);
		data = new ArrayList<Map<String, Object>>();
		sim_color = new SimpleAdapter(getApplicationContext(), Getcolordata(),
				R.layout.paint_size, new String[] { "size" },
				new int[] { R.id.backcolortext });
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		xcarcmenuview.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public void onClick(View view, int pos) {
				
				switch (pos) {
				case 1:
					drawpanelview.Setdrawtype("画笔");
					texttool_1.setText(texttool1.getText());
					texttool.setText(texttool1.getText());
					break;
				case 2:
					drawpanelview.Setdrawtype("橡皮擦");
					texttool_1.setText(texttool2.getText());
					texttool.setText(texttool2.getText());
					break;
				case 3:
					drawpanelview.Setdrawtype("直线");
					texttool_1.setText(texttool3.getText());
					texttool.setText(texttool3.getText());
					break;
				case 4:
					drawpanelview.Setdrawtype("矩形");
					texttool_1.setText(texttool4.getText());
					texttool.setText(texttool4.getText());
					break;
				case 5:
					drawpanelview.Setdrawtype("圆");
					texttool_1.setText(texttool5.getText());
					texttool.setText(texttool5.getText());
					break;
				case 6:
					
					drawpanelview.Setdrawtype("三角");
					Toast.makeText(getApplicationContext(), "请任意点击屏幕中三个点", Toast.LENGTH_SHORT).show();
					texttool_1.setText(texttool6.getText());
					texttool.setText(texttool6.getText());
					break;
				}

			}
		});
	}

	public List<Map<String, Object>> Getcolordata() {
		String c[] = { "2px", "4px", "6px", "8px", "10px", "12px", "14px",
				"16px", "18px", "20px" };
		for (int i = 0; i < c.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("size", c[i]);
			data.add(map);
		}

		return data;

	}

	private void setPopupwindow() {
		// 定义popupwindow内容、布局
		popupwindow = new PopupWindow(contentView, window_width/4, window_height/2, true);
		popupwindow.setFocusable(true);
		popupwindow.setTouchable(true);
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
	}


	private void exitdialog() {// 保存提示框
		ed = new EditText(getApplicationContext());
		ed.setTextColor(Color.BLACK);
		Dialog dialog = new AlertDialog.Builder(MainActivity.this)
				.setTitle("输入保存的名称:").setView(ed)
				.setPositiveButton("保存", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						drawpanelview.saveBitmap(ed.getText().toString(), DrawPanelView.cachebBitmap);
					}
				}).setNegativeButton("取消", null).create();
		dialog.show();

	}

	public void IntentFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, 1);
	}
    public void openFile(){
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    	intent.setType("image/*");
    	startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TODO Auto-generated method stub
		if(requestCode == GALLERY_REQUEST_CODE){
			if(data==null){//数据为空相当于没选择图片
				return;
			}
			Uri uri;
			uri = data.getData();
			try {
				InputStream inputStream = getContentResolver().openInputStream(uri);
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
				
				drawpanelview.setBitmap(bitmap);
				DrawPanelView.bitmap_of=1;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			contentView = getLayoutInflater().inflate(R.layout.file_menu, null);
			but_file1 = (Button) contentView.findViewById(R.id.file_but_1);
			but_file2 = (Button) contentView.findViewById(R.id.file_but_2);
			setPopupwindow();
			popupwindow.showAtLocation(v,Gravity.CENTER, 10, 10);
			but_file1.setOnClickListener(this);
			but_file2.setOnClickListener(this);
			break;
		case R.id.button2:
			contentView = getLayoutInflater().inflate(R.layout.color, null);
			paintColor();
			setPopupwindow();
			popupwindow.showAtLocation(v,Gravity.CENTER, 10, 10);

			break;
		case R.id.button3:
			contentView = getLayoutInflater().inflate(R.layout.seekbar, null);
			SeekBar seekBar=(SeekBar) contentView.findViewById(R.id.seekbar);
			 seek_text=(TextView) contentView.findViewById(R.id.seek_text);
			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
		
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					drawpanelview.Setpaintsize(progress);
					seek_text.setText("size: "+progress);
					seekBar.setProgress(progress);
				
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
			});
			setPopupwindow();
			popupwindow.showAsDropDown(v, -50, 0);
			break;
		case R.id.button4:
			break;
		case R.id.button5:
			drawpanelview.undo();
			break;
		case R.id.button6:
			drawpanelview.clearAll();

			break;
		case R.id.file_but_1:
			 openFile();
			popupwindow.dismiss();
			break;
		case R.id.file_but_2:
			exitdialog();
			
			popupwindow.dismiss();
			break;

		

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (arg0.getId() == paintsize.getId()) {// 设置画笔大小
			drawpanelview.Setpaintsize((arg2 + 1) * 2);
			popupwindow.dismiss();
		}
	}

}