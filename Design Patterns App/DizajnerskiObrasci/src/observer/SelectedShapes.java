package observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectedShapes implements Observable{
	private int numberOfSelected;

	private List<Observer> observers;
	
	
	public void setNumberOfSelected(int numberOfSelected) {
		this.numberOfSelected = numberOfSelected;
		notifyObservers();
	}
	
	
	public SelectedShapes() {
		observers = new ArrayList<>();
	}

	@Override	
	public void addObserver(Observer observer) {
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while (it.hasNext())
			it.next().update(numberOfSelected);
		
	}
	

}
