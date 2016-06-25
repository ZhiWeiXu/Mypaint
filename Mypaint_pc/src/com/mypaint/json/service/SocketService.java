package com.mypaint.json.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import com.mypaint.json.domain.PlottingCoordinates;
import com.mypaint.json.fastjsontools.GsonTools;

public class SocketService extends Frame {
	private InputStream inputStream = null;
	private GsonTools gsonTools;
	// private Wacom wacom;
	private PlottingCoordinates plottingCoordinates = new PlottingCoordinates();
	private static boolean flag = true;
	Socket socket;
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	int x_1 = 0, y_1 = 0, x_2 = 0, y_2 = 0, x_3 = 0, y_3 = 0;

	private int Shape=0;
	private int color;
	private int size;
	private InetAddress ip;
	Graphics2D g2D;
	String url;
	ImageZxing imagezx=new ImageZxing();
	Toolkit tool = this.getToolkit();
    Image image;
	// private FastJsonTools fastJsonTools;
	public SocketService() {
		// TODO Auto-generated constructor stub
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				SocketService socketService = new SocketService();
				socketService.setFlag(false);
				System.exit(0);
			}
		});
		// wacom = new Wacom();
		gsonTools = new GsonTools();
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			socket = new Socket(ip.getHostAddress(), 8010);
			System.out.println(ip.getHostAddress());
			url=ip.getHostAddress();
			if (socket != null)
				read(inputStream);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catrch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void read(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int hasRead = 0;
		while (flag) {
			inputStream = socket.getInputStream();
			if(inputStream!=null&&inputStream.available()!=0){
			try {
				if ((hasRead = inputStream.read(buffer)) != -1) {
					String info = new String(buffer, 0, hasRead);
					plottingCoordinates = gsonTools.getCoordinate(info, PlottingCoordinates.class);
					if (plottingCoordinates != null) {
						Shape = plottingCoordinates.getShape();
						x1 = (int) plottingCoordinates.getX1();
						x2 = (int) plottingCoordinates.getX2();
						y1 = (int) plottingCoordinates.getY1();
						y2 = (int) plottingCoordinates.getY2();
						color = plottingCoordinates.getColor();
						size = plottingCoordinates.getSize();
						System.out.println("起点X：" + x1 + "Y:" + y1 + "------终点X：" + x2 + "Y:" + y2);
						repaint();
					}

					// wacom.DisplayDrawing(plottingCoordinates);
					plottingCoordinates = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
		try {
			inputStream.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setFlag(boolean flag) {
		SocketService.flag = flag;
	}

	public static void main(String[] args) {
		new SocketService();
	}

	public void SetColor(int colorof) {
		switch (color) {
		case 0:
			g2D.setColor(new Color(255, 0, 0));
			break;
		case 1:

			g2D.setColor(new Color(255, 255, 255));
			break;
		case 2:
			g2D.setColor(new Color(255, 211, 155));

			break;
		case 3:
			g2D.setColor(new Color(255, 240, 0));

			break;
		case 4:
			g2D.setColor(new Color(238, 180, 34));
			break;
		case 5:
			g2D.setColor(new Color(238, 59, 59));
			break;
		case 6:
			g2D.setColor(new Color(224, 255, 255));
			break;
		case 7:
			g2D.setColor(new Color(191, 239, 255));
			break;
		case 8:
			g2D.setColor(new Color(188, 210, 238));
			break;
		case 9:
			g2D.setColor(new Color(159, 121, 238));
			break;
		case 10:
			g2D.setColor(new Color(58, 95, 205));
			break;
		case 11:
			g2D.setColor(new Color(84, 255, 159));
			break;
		case 12:
			g2D.setColor(new Color(50, 205, 550));
			break;
		case 13:
			g2D.setColor(new Color(33, 136, 104));
			break;
		case 14:
			g2D.setColor(new Color(105, 139, 34));
			break;
		case 15:
			g2D.setColor(new Color(47, 79, 79));
			break;
		case 16:
			g2D.setColor(new Color(139, 58, 58));
			break;
		case 17:
			g2D.setColor(new Color(75, 0, 130));
			break;
		case 18:
			g2D.setColor(new Color(47, 79, 0));
			break;
		case 19:
			g2D.setColor(new Color(0, 0, 128));
			break;
		case 20:
			g2D.setColor(new Color(0, 0, 0));
			break;
		}
	}

	@Override
	public void paint(Graphics g) {
		 
		super.paint(g);
		
	     image = tool.getImage(imagezx.InputZxing(url));
        System.err.println(image);
		g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(size));
		System.out.println("" + color);
		SetColor(color);
		if (Shape==0) {
			g2D.drawImage(image, 500, 100, 300, 300, null);
		}
		switch (Shape) {
		case 1:
			g2D.drawLine(x1, y1, x2, y2);
			break;
		case 2:
			g2D.setColor(Color.WHITE);
			g2D.drawLine(x1, y1, x2, y2);
			break;
		case 3:
			g2D.drawLine(x1, y1, x2, y2);

			break;
		case 4:
			g2D.drawRect(x1, y1, x2 - x1, y2 - y1);

			break;
		case 5:
			switch (x2 % 3) {
			case 1:
				x_1 = x1;
				y_1 = y1;
				g2D.drawLine(x_1, y_1, x_1, y_1);
				break;
			case 2:
				x_2 = x1;
				y_2 = y1;
				g2D.drawLine(x_2, y_2, x_1, y_1);
				break;
			case 0:
				x_3 = x1;
				y_3 = y1;
				g2D.drawLine(x_3, y_3, x_2, y_2);
				g2D.drawLine(x_1, y_1, x_3, y_3);
				break;
			}

			break;
		case 6:
			float d = (float) ((x2) / (Math.sqrt(2)));
			g2D.drawOval(x1 - x2, y1 - (int) (d), 2 * x2, 2 * x2);
			break;
		case 7:

			break;
		case 8:
			g2D.clearRect(0, 0, 1920, 1080);
			break;
		}
		
	}

	@Override
	public void update(Graphics g) {
		// TODO 自动生成的方法存根
		paint(g);
	}

}
