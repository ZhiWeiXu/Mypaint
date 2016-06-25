package com.json.service;

import com.json.domain.PlottingCoordinates;

public class JsonService {

	public JsonService() {
		// TODO Auto-generated constructor stub
	}

	public PlottingCoordinates getPlottingCoordinates(float x1, float x2,
			float y1, float y2, int Shape, int Size, int Color) {

		PlottingCoordinates plottingCoordinates = new PlottingCoordinates();
		plottingCoordinates.setX1(x1);
		plottingCoordinates.setX2(x2);
		plottingCoordinates.setY1(y1);
		plottingCoordinates.setY2(y2);
		plottingCoordinates.setShape(Shape);
		plottingCoordinates.setSize(Size);
		plottingCoordinates.setColor(Color);
		return plottingCoordinates;

	}
}
