import java.awt.Color;

public class Lab08Main {

    private static final String[] COLOR_NAMES = {"แดง", "ส้ม", "เหลือง", "เขียว", "ฟ้า", "คราม", "ม่วง"};

    private static final Color[] RAINBOW = new Color[]{
            new Color(255, 0, 0),
            new Color(255, 127, 0),
            new Color(255, 255, 0),
            new Color(0, 200, 0),
            new Color(0, 150, 255),
            new Color(75, 0, 130),
            new Color(148, 0, 211)
    };

    public static void main(String[] args) {

        Turtle.bgcolor("white");
        Turtle.setCanvasSize(900, 900);
        Turtle.zoom(-250, -250, 250, 250);

        Turtle กลม = new Turtle();
        Thread threadกลม = new Thread(() -> {
            กลม.speed(0);
            กลม.up();
            กลม.shape("circle");
            กลม.shapeSize(20, 20);
            Color pink = new Color(255, 105, 180);
            กลม.fillColor(pink);
            กลม.outlineColor(pink);
            กลม.setPosition(0, 0);
            กลม.stamp();

            กลม.shape("turtle");
            กลม.shapeSize(33, 33);
            กลม.outlineColor("black");
            กลม.fillColor(new Color(0, 255, 0, 180));
            กลม.up();

            orbitOpposite(กลม, 90, 0, 25);
        }, "กลม");

        Turtle หัวใจ = new Turtle();
        Thread threadหัวใจ = new Thread(() -> {
            หัวใจ.speed(0);
            drawHeart(หัวใจ, 0, 0, 2.2, Color.RED);

            หัวใจ.shape("turtle");
            หัวใจ.shapeSize(33, 33);
            หัวใจ.outlineColor("black");
            หัวใจ.fillColor(new Color(0, 200, 255, 180));
            หัวใจ.up();

            orbitOpposite(หัวใจ, 90, 180, 25);
        }, "หัวใจ");

        threadกลม.start();
        threadหัวใจ.start();

        final int rings = 10;

        final int baseStep = 1;
        final int baseSize = 6;

        final long baseDelay = 14;
        final long delayPerRing = 8;
        final long minDelay = 10;
        final long maxDelay = 120;

        for (int ring = 0; ring < rings; ring++) {

            final double radius = 120 + ring * 12;

            final int angleStep = baseStep + 2 * ring;
            final int dotSize = baseSize + 2 * ring;

            long d = baseDelay + delayPerRing * ring;
            if (d < minDelay) d = minDelay;
            if (d > maxDelay) d = maxDelay;
            final long ringDelay = d;

            for (int colorIndex = 0; colorIndex < 7; colorIndex++) {

                final int cIndex = colorIndex;
                final int offset = cIndex * (360 / 7);

                String turtleName = "เต่า" + COLOR_NAMES[cIndex] + "-วง" + ring;

                new Thread(() -> {

                    Turtle turtleColor = new Turtle();
                    turtleColor.speed(0);
                    turtleColor.up();
                    turtleColor.shape("circle");
                    turtleColor.shapeSize(dotSize, dotSize);
                    turtleColor.fillColor(RAINBOW[cIndex]);
                    turtleColor.outlineColor(RAINBOW[cIndex]);

                    int angle = 0;

                    while (true) {
                        int a = (angle + offset) % 360;

                        double rad = Math.toRadians(a);
                        double x = radius * Math.cos(rad);
                        double y = radius * Math.sin(rad);

                        turtleColor.setPosition(x, y);
                        turtleColor.stamp();

                        angle = (angle + angleStep) % 360;
                        sleep(ringDelay);
                    }

                }, turtleName).start();
            }
        }
    }

    private static void orbitOpposite(Turtle t, double radius, int offsetDeg, long delayMs) {
        t.up();
        while (true) {
            int baseAngle = (int) ((System.currentTimeMillis() / delayMs) % 360);
            int angle = (baseAngle + offsetDeg) % 360;

            double rad = Math.toRadians(angle);
            double x = radius * Math.cos(rad);
            double y = radius * Math.sin(rad);

            t.setDirection(angle + 90);
            t.setPosition(x, y);
            sleep(delayMs);
        }
    }

    private static void drawHeart(Turtle t, double cx, double cy, double scale, Color c) {
        t.up();
        t.shape("circle");
        t.shapeSize(6, 6);
        t.fillColor(c);
        t.outlineColor(c);

        for (int deg = 0; deg < 360; deg++) {
            double tt = Math.toRadians(deg);
            double x = 16 * Math.pow(Math.sin(tt), 3);
            double y = 13 * Math.cos(tt)
                    - 5 * Math.cos(2 * tt)
                    - 2 * Math.cos(3 * tt)
                    - Math.cos(4 * tt);

            double px = cx + x * scale;
            double py = cy + y * scale;

            t.setPosition(px, py);
            t.stamp();
        }

        t.shapeSize(5, 5);
        for (int deg = 0; deg < 360; deg += 2) {
            double tt = Math.toRadians(deg);
            double x = 16 * Math.pow(Math.sin(tt), 3);
            double y = 13 * Math.cos(tt)
                    - 5 * Math.cos(2 * tt)
                    - 2 * Math.cos(3 * tt)
                    - Math.cos(4 * tt);

            double px = cx + x * scale * 0.92;
            double py = cy + y * scale * 0.92;

            t.setPosition(px, py);
            t.stamp();
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
