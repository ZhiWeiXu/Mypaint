package com.json.domain;

public class PlottingCoordinates {
	private float x1;
	private float x2;
	private float y1;
	private float y2;
	private int Shape;
	private int Size;
	private int Color;

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}

	public int getShape() {
		return Shape;
	}

	public void setShape(int shape) {
		Shape = shape;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public int getColor() {
		return Color;
	}

	public void setColor(int color) {
		Color = color;
	}

	@Override
	public String toString() {
		return "PlottingCoordinates [x1=" + x1 + ", x2=" + x2 + ", y1=" + y1
				+ ", y2=" + y2 + ", Shape=" + Shape + ", Size=" + Size
				+ ", Color=" + Color + "]";
	}

}
