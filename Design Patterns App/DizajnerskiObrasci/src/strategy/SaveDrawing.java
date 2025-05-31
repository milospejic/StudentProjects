package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class SaveDrawing implements Saving{
	
	DrawingModel model;
	
	public SaveDrawing(DrawingModel model) {
		this.model=model;
	}

	@Override
	public void save(String address) {
		File file = new File(address);
		try {
			file.createNewFile();
			System.out.println(address);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
        
        
        try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(model.getShapes());
			objectOutputStream.flush();
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found!", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}
