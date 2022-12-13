//Angela Marshall
//300297936
//RANK: SILVER - WOOHOOOOO!!!
//March 4/2022
import java.util.*;
import java.io.*;
import java.math.*;
/**
 * Grab Snaffles and try to throw them through the opponent's goal!
 * Move towards a Snaffle and use your team id to determine where you need to throw it.
 **/
// To debug: System.err.println("Debug messages...");
// Edit this line to indicate the action for each wizard (0 ≤ thrust ≤ 150, 0 ≤ power ≤ 500)
// i.e.: "MOVE x y thrust" or "THROW x y power"                
class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left
        // game loop
        while (true) {
            int myScore = in.nextInt();
            int myMagic = in.nextInt();
            int opponentScore = in.nextInt();
            int opponentMagic = in.nextInt();
            int entities = in.nextInt(); // number of entities still in game
            ArrayList<Wizard> myWizard = new ArrayList<>();
            ArrayList<Snaffle> snaffles = new ArrayList<>();
            ArrayList<Snaffle> snaffleDistance = new ArrayList<>();
            for (int i = 0; i < entities; i++) {
                int entityId = in.nextInt(); // entity identifier
                String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
                int x = in.nextInt(); // position
                int y = in.nextInt(); // position
                int vx = in.nextInt(); // velocity
                int vy = in.nextInt(); // velocity
                int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise
                int distance = 0;
                boolean target = false;
                if (entityType.equals("WIZARD")){
                    myWizard.add(new Wizard(entityId, x, y, vx, vy, state));
                } else if (entityType.equals("SNAFFLE")){
                    snaffles.add(new Snaffle(entityId, x, y, vx, vy, state, distance, target));
                }
            }
            for (int i = 0; i < 2; i++) {
                int wizardX = myWizard.get(i).getX();
                int wizardY = myWizard.get(i).getY();
                int distance = 0;
                int maximum = Integer.MAX_VALUE;
                int closestSnaffle = 0;
                for (int j = 0; j < snaffles.size(); j++){
                    int snaffleX = snaffles.get(j).getX();
                    int snaffleY = snaffles.get(j).getY();
                    distance = distanceFormula(wizardX, snaffleX, wizardY, snaffleY);
                    if (distance < maximum){
                        maximum = distance;
                        closestSnaffle = j;
                    }
                }
                
                int center = 0;
                if (myTeamId == 0){
                    center = 16000;
                } else {
                    center = 0;
                }

                if (myWizard.get(i).getState() == 1){
                    System.out.println("THROW " + center + " 3750 500");
                } else if (myMagic >= 15){
                    System.out.println("ACCIO " + snaffles.get(closestSnaffle).getEntityId());
                } else{
                    System.out.println("MOVE " + snaffles.get(closestSnaffle).getX() + " " + snaffles.get(closestSnaffle).getY() + " 150");
                } 
            }
        }
    }  

    public static int distanceFormula(int x1, int x2, int y1, int y2) {
        return (int)(Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
    }
}
abstract class Entity implements Comparable<Snaffle>{
    protected int entityId;
    protected String entityType;
    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected int state;
    public Entity(int entityId, int x, int y, int vx, int vy, int state){
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = state;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getState(){
        return this.state;
    }
    public void setState(int state){
        this.state = state;
    }
    public int getEntityId(){
       return entityId;
   }
}
class Wizard extends Entity{
   public Wizard(int entityId, int x, int y, int vx, int vy, int state){
        super(entityId, x, y, vx, vy, state);
   }

@Override
public int compareTo(Snaffle o) {
	return 0;
}
}
class Snaffle extends Entity{
    protected int distance;
    protected boolean target;
    public Snaffle(int entityId, int x, int y, int vx, int vy, int state, int distance, boolean target){
        super(entityId, x, y, vx, vy, state);
        this.distance = distance;
    }
    public boolean getTarget(){
       return this.target;
   }
	@Override
	public int compareTo(Snaffle o) {
		if (this.distance > o.distance){
            return 1;
        } else if (this.distance < o.distance){
            return -1;
        } else{
            return 0;
        }
	}
}