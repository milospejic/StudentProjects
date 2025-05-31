package strategy;

public class SavingManager implements Saving{
	
	private Saving saving;
	
	public SavingManager(Saving saving) {
		this.saving=saving;
	}
	
	
	@Override
	public void save(String address) {
		saving.save(address);
		
	}

}
