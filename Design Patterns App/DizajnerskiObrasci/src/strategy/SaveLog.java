package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.ListModel;

import mvc.DrawingFrame;

public class SaveLog implements Saving {

    private DrawingFrame frame;

    public SaveLog(DrawingFrame frame) {
        this.frame = frame;
    }

    @Override
    public void save(String address) {
        File file = new File(address + ".txt");
        
      
        try {
				file.createNewFile();
		} catch (IOException e1) {
				
			JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}

      
        ListModel<String> listModel = frame.getList().getModel();
            
        FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) ;
            
			for (int i = 0; i < listModel.getSize(); i++) {
                	
				bufferedWriter.write((String)listModel.getElementAt(i));
                bufferedWriter.newLine();
			}
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
        
           
    }
}
