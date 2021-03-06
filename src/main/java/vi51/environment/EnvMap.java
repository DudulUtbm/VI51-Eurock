package vi51.environment;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.arakhne.afc.math.continous.object2d.Rectangle2f;
import org.jbox2d.dynamics.World;

import vi51.util.ConstantContainer;
import vi51.util.Semantic;

/**
 * the world map
 * 
 * @author rdulieu
 *
 */

public class EnvMap {
	
	//TODO create a list of RTree that represent the world -> Cohen Sutherland ?
	private RTree objectList;
	private Map<UUID,AgentBody> bodies = new TreeMap<>();
	
	
	//Parameters of the world
	private final float width; 
	private final float height;
	
	public RTree getObjectList() {
		return objectList;
	}

	public void setObjectList(RTree objectList) {
		this.objectList = objectList;
	}

	/**
	 * Initialisation
	 * @param objects
	 */

	public EnvMap(float width,float height){
		this.width = width;
		this.height = height;
		this.objectList = new RTree(null,new Rectangle2f(0,0,width,height)); 

	}

	/**
	 * Add a collection of object
	 * @param objects
	 */
	public void addObjects(Collection<EnvironmentObject> objects) {
		for( EnvironmentObject o : objects){
			objectList.getRoot().add(o);
		}
	}
	
	/**
	 * add a single object
	 * @param object
	 */
	public void addObjects(EnvironmentObject object) {
		
			objectList.getRoot().add(object);
		
	}
	
	/** Replies the number of bodies in the map
	 * 
	 * @return the number of bodies
	 */
	public int getBodyCount(){
		return this.bodies.size();
	}
	/** Remove a body from the agentBodies
	 * @param UUID
	 */
	public void removeBody(UUID id){
		this.bodies.remove(id);
	}
	/** Replies the agent bodies
	 * 
	 * @return the agent bodies
	 */
	public Collection<AgentBody> getAgentBodies(){
		return Collections.unmodifiableMap(this.bodies).values();
	}
	
	/** Replies the agent body
	 * @param id
	 * @return the agent bodies
	 */
	public AgentBody getAgentBody (UUID id) {
		return this.bodies.get(id);
	}

	public EnvironmentObject getObjectAt(float i, float j, float perceptionDistance) {
	  throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
	
	/** Create a body of the given type.
	 *
	 * @param bodyType the type of the body.
	 * @param agentId the identifier of the agent that will be linked to the body.
	 * @param perceptionDistance the distance of perception.
	 * @return the body.
	 * @throws Exception if it is impossible to retrieve the body constructor or to create the instance. 
	 */
	public <T extends AgentBody> T createBody(Class<T> bodyType,float x,float y, UUID agentId,World w, float perceptionDistance,Semantic type) throws Exception {

		UUID id = agentId;
		if (id == null) {
			id = UUID.randomUUID();
		}

		Constructor<T> cons = bodyType.getDeclaredConstructor(float.class, float.class, float.class, UUID.class,World.class,EnvMap.class, float.class,Semantic.class);
		T body = cons.newInstance(x, y,ConstantContainer.BASIC_RADIUS, id,w, this, perceptionDistance,type);
		this.bodies.put(id, body);
		addObjects(body);

		return body;
	}

//	public boolean canMoveInside(int x, int y) {
//		
//		return (x>=ConstantContainer.MAP_LIMIT_MIN_X && y>=ConstantContainer.MAP_LIMIT_MIN_Y && x<width-ConstantContainer.MAP_LIMIT_MAX_X && y<height-ConstantContainer.MAP_LIMIT_MAX_Y);
//	}

	public float getHeight() {
		
		return this.height;
	}
	public float getWidth() {
		
		return this.width;
	}

	

}
