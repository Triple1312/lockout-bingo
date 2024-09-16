package net.abrikoos.lockout_bingo.gui.widget;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LockoutSlider<T> extends SliderWidget {
    List<T> values;
    double min;
    double max;
    Consumer<T> consumer;

    private LockoutSlider(int x, int y, int width, int height, Text text, double value, List<T> values, Consumer<T> consumer, double min, double max) {
        super(x, y, width, height, text, value);
        this.values = values;
        this.min = min;
        this.max = max;
        this.consumer = consumer;
    }


    @Override
    protected void updateMessage() {
        if (this.values.isEmpty()) {
            this.setMessage(Text.of(String.valueOf(this.value)));
        }
        else {
            double value = this.value;
            if (value == 1.0D) {
                value = 0.99D;
            }
            this.setMessage(Text.of(this.values.get((int) (value * (values.size()))).toString()));
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.setValueFromMouse(mouseX);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.setValueFromMouse(mouseX);
        super.onDrag(mouseX, mouseY, deltaX, deltaY);
    }



    protected void setValue(double value) {
        double d = this.value;
        this.value = MathHelper.clamp(value, 0.0, 1.0);
        if (d != this.value) {
            this.applyValue();
        }

        this.updateMessage();
    }

    private void setValueFromMouse(double mouseX) {
        if (this.values.isEmpty()) {
            this.setValue((mouseX - (double)(this.getX() + 4)) / (double)(this.width - 8));
        }
        else {
            value = (mouseX - (double) (this.getX() + 4)) / (double) (this.width - 8);
            double area_before = (max - min) * value;
            for (int i = 0; i < values.size(); i++) { // todo not tested
                double area = (max - min) * (i + 1) / values.size();
                if (area_before < area) {
                    value = i / (values.size() - 1);
                    break;
                }
            }
            this.value = value;
            this.applyValue();
        }
    }

    public T currentvalue() {
        if (values.isEmpty()) {
            return (T) (Object) (this.value + min);
        }
        else {
            double value = this.value;
            if (value == 1.0D) {
                value = 0.99D;
            }
            try {
                return this.values.get((int) (value * (values.size())));
            }
            catch (Exception e) {
                return this.values.get(this.values.size() - 1);
            }

        }
    }

    @Override
    protected void applyValue() {
        consumer.accept(currentvalue());

    }

    public static <T> LockoutSlider.Builder builder(Text text, Consumer<T> consumer) {
        return new LockoutSlider.Builder<>(text, consumer);
    }

    public static class Builder<T> {
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private Tooltip tooltip;
        List<T> values = new ArrayList<>();
        private T default_value;
        Consumer<T> consumer;
        Text text;
        double max = 1.0D;
        double min = 0.0D;

        public Builder(Text text, Consumer<T> consumer) {
            this.text = text;
            this.consumer = consumer;
        }

        public Builder defaultValue(T val) {
            this.default_value = val;
            return this;
        }

        public Builder values(List<T> values) {
            this.values = values;
            return this;
        }

        public Builder minmax(double min, double max) {
            this.min = min;
            this.max = max;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public LockoutSlider<T> build() {
            double val = 0.0; // todo check if between min max
            try {
                if (!values.isEmpty()) {
                    val = (double) values.indexOf(default_value) / (values.size() - 1);
                }
                else {
                    val = (double) default_value;
                }
            }
            catch (Exception e) {
//                e.printStackTrace();
            }
            return new LockoutSlider<T>(x, y, width, height, text, val, values, consumer, min, max);
        }

    }



}
