package spring.academy.tatery.homework.week3;

public class Car {
    private int id;
    private String mark;
    private String model;
    private String color;

    public Car(int id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

	@Override
	public String toString() {
		return "Car [id=" + id 
				+ ", mark=" + mark 
				+ ", model=" + model 
				+ ", color=" + color 
				+ "]";
	}
    
}
