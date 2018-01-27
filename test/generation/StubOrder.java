package generation;

public class StubOrder implements Order {
	int percentDone;
	private int skill;
	private boolean perfect;
	private Builder builder;
	private MazeConfiguration mazeConfiguration;
	
	public StubOrder (int skill, boolean perfect, Builder builder) {
		this.skill = skill;
		this.perfect = perfect;
		this.builder = builder;
	}
	
	@Override
	public void updateProgress(int percentage) {
		this.percentDone = percentage;
	}
	@Override
	public int getSkillLevel() {
		return skill;
	}
	@Override
	public boolean isPerfect() {
		return perfect;
	}
	@Override
	public Builder getBuilder() {
		return builder;
	}
	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeConfiguration = mazeConfig;
	}
	public MazeConfiguration getConfiguration(){
		return mazeConfiguration;
	}
}