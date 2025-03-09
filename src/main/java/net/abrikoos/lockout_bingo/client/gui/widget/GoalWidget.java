package net.abrikoos.lockout_bingo.client.gui.widget;

import net.abrikoos.lockout_bingo.client.ClientGameStateV2;
import net.abrikoos.lockout_bingo.server.goals.GoalListItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.abrikoos.lockout_bingo.networkv2.team.Colors;

public class GoalWidget extends ClickableWidget {

    public String uuid = "00000000-0000-0000-0000-000000000000";
    public final GoalListItem goal;

    public GoalWidget(int x, int y, int widthHeight, GoalListItem goal) {
        super(x, y, widthHeight, widthHeight, Text.of(goal.name));
        this.goal = goal;
        this.setTooltip(Tooltip.of(Text.of(goal.name)));
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        try {
            int color = Colors.get(ClientGameStateV2.teamReg.getTeamDataByPlayerUUID(uuid).teamColor);
            context.fill( this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, color - 0x47000000);
            goal.draw(context, delta, this.getX(), this.getY(), this.width, this.height);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public static Builder builder(GoalListItem goal, int widthHeight) {
        return new Builder(goal, widthHeight);
    }

    public void updateUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return false;
    }

    public static class Builder {
        private int x;
        private int y;
        private int widthHeight;
        private GoalListItem goal;

        public Builder(GoalListItem goal, int widthHeight) {
            this.widthHeight = widthHeight;
            this.goal = goal;
        }

        public Builder dimensions(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public GoalWidget build() {
            return new GoalWidget(x, y, widthHeight, goal);
        }






    }
}
