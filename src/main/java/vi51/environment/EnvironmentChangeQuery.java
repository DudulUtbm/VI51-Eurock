package vi51.environment;

import java.util.UUID;

import org.arakhne.afc.math.continous.object2d.Vector2f;

public final class EnvironmentChangeQuery {
	
	private final UUID emitter;
	private final Vector2f velocity; //new velocity to apply to the body
	private final String influenceType;
	
	public EnvironmentChangeQuery(UUID emitter, Vector2f velocity,String influenceType) {
		this.emitter = emitter;
		this.velocity = velocity;
		this.influenceType = influenceType;
	}

	/** Replies the emitter.
	 */
	public UUID getEmitter() {
		return this.emitter;
	}
	
	/** Replies the change.
	 */
	public Vector2f getVelocity() {
		return this.velocity;
	}

	public String getInfluenceType() {
		return influenceType;
	}

}
