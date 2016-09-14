package DrinkWine;

import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;

@Script.Manifest(
        name = "Wine Drinker",
        description = "Drinks Wine",
        properties = "client=4;"
)

public class Main extends PollingScript<ClientContext> {

    private int wineID = 1993;
    private int HP;
    private int percentageHpToEatAt = 50;


    @Override
    public void start() {

    }

    @Override
    public void poll() {

        HP = (int)(((double)ctx.combat.health() / ctx.skills.realLevel(Constants.SKILLS_HITPOINTS)) * 100.0);

        final State state = getState();
        if (state == null) {
            return;
        }



        switch(state) {
            case DRINK:

                final Item food = ctx.inventory.select().id(wineID).poll();
                if(food.valid()){
                    food.click();
                }

                Condition.sleep(Random.nextInt(800, 1000));

                break;

            case WAIT:

                Condition.sleep(Random.nextInt(500, 1000));

                break;
        }
    }

    private State getState() {
        if (ctx.inventory.select().id(wineID).count(true) > 0 && HP != 0 && HP <= percentageHpToEatAt) {
            return State.DRINK;
        }
        return State.WAIT;
    }

    public void stop(){
        ctx.controller.stop();
    }

    private enum State {
        DRINK,WAIT
    }


}